package io.javalab.launcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * Handles isolated compilation (simple javac or Maven), build status inspection,
 * directory preview, and execution with guaranteed working directory isolation.
 */
public final class ProjectRunner {

    public enum BuildStatus {
        UP_TO_DATE("Güncel", "[OK]"),
        STALE("Güncelleme Gerekli", "[ESKI]"),
        NOT_BUILT("Derlenmedi", "[--]"),
        FAILED("Derleme Hatası", "[HATA]");

        private final String label;
        private final String badge;

        BuildStatus(String label, String badge) {
            this.label = label;
            this.badge = badge;
        }

        public String getLabel() {
            return label;
        }

        public String getBadge() {
            return badge;
        }
    }

    public record CompileResult(boolean success, String classpath, String output, long durationMs) {
    }

    public record DirectoryItem(String name, boolean directory, String detail) {
    }

    private final Path repoRoot;
    private final String javaBin;
    private final String javacBin;
    private final String mvnBin;

    public ProjectRunner(Path repoRoot) {
        this.repoRoot = repoRoot.toAbsolutePath().normalize();
        this.javaBin = resolveJdkTool("java");
        this.javacBin = resolveJdkTool("javac");
        this.mvnBin = resolveMavenTool();
    }

    public Path getRepoRoot() {
        return repoRoot;
    }

    public BuildStatus inspectBuildStatus(TargetEntry target, boolean lastFailed) {
        if (lastFailed) {
            return BuildStatus.FAILED;
        }
        Path workDir = target.resolveWorkingDir(repoRoot);
        String classRelPath = target.getMainClass().replace('.', File.separatorChar) + ".class";

        if (target.isMaven()) {
            Path classFile = workDir.resolve(Path.of("target", "classes", classRelPath));
            if (!Files.isRegularFile(classFile)) {
                return BuildStatus.NOT_BUILT;
            }
            long classMtime = lastModifiedMillis(classFile);
            long pomMtime = lastModifiedMillis(workDir.resolve("pom.xml"));
            long newestSrc = newestJavaMillis(resolveSourceRoot(target));
            return (classMtime >= Math.max(pomMtime, newestSrc)) ? BuildStatus.UP_TO_DATE : BuildStatus.STALE;
        } else {
            Path outDir = simpleOutDir(workDir, target.getMainClass());
            Path classFile = outDir.resolve(classRelPath);
            if (!Files.isRegularFile(classFile)) {
                return BuildStatus.NOT_BUILT;
            }
            long classMtime = lastModifiedMillis(classFile);
            Path sourceRoot = resolveSourceRoot(target);
            long newestSrc = newestJavaMillis(sourceRoot);
            return (classMtime >= newestSrc) ? BuildStatus.UP_TO_DATE : BuildStatus.STALE;
        }
    }

    public CompileResult compile(TargetEntry target) {
        long start = System.currentTimeMillis();
        try {
            if (target.isMaven()) {
                return compileMaven(target, start);
            }
            return compileSimple(target, start);
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            return new CompileResult(false, "", "Derleme istisnasi: " + e.getMessage(), elapsed);
        }
    }

    private CompileResult compileSimple(TargetEntry target, long start) throws IOException, InterruptedException {
        Path workDir = target.resolveWorkingDir(repoRoot);
        Path sourceRoot = resolveSourceRoot(target);
        Path mainJavaFile = locateMainJavaFile(sourceRoot, target.getMainClass());
        if (mainJavaFile == null || !Files.isRegularFile(mainJavaFile)) {
            long elapsed = System.currentTimeMillis() - start;
            return new CompileResult(false, "",
                    "Ana kaynak dosyasi bulunamadi: " + target.getMainClass() + " (kaynak kok: " + sourceRoot + ")",
                    elapsed);
        }

        Path outDir = simpleOutDir(workDir, target.getMainClass());
        List<Path> helpers = collectHelperJavaFiles(sourceRoot, mainJavaFile);

        // Attempt 1: Compile mainJavaFile + non-main helper files in the same source root.
        // Sibling files containing their own main() are excluded from direct arguments so
        // an unrelated broken main file never blocks this target, while -sourcepath still
        // resolves any class actually referenced by mainJavaFile.
        cleanAndCreateDir(outDir);
        List<String> cmd1 = new ArrayList<>();
        cmd1.add(javacBin);
        cmd1.add("-encoding");
        cmd1.add("UTF-8");
        cmd1.add("-sourcepath");
        cmd1.add(sourceRoot.toString());
        cmd1.add("-d");
        cmd1.add(outDir.toString());
        cmd1.add(mainJavaFile.toString());
        for (Path helper : helpers) {
            cmd1.add(helper.toString());
        }

        ProcessOutput res1 = runCaptured(cmd1, workDir);
        if (res1.exitCode() == 0) {
            long elapsed = System.currentTimeMillis() - start;
            String msg = res1.output().isBlank()
                    ? String.format("Basariyla derlendi (%d yardimci dosya, %d ms)", helpers.size(), elapsed)
                    : res1.output();
            return new CompileResult(true, outDir.toString(), msg, elapsed);
        }

        // Attempt 2 (strict isolation fallback): if an unrelated helper file in the folder
        // has a compile error, compile ONLY mainJavaFile with -sourcepath so javac compiles
        // only the exact helpers this target depends on.
        if (!helpers.isEmpty()) {
            cleanAndCreateDir(outDir);
            List<String> cmd2 = List.of(
                    javacBin,
                    "-encoding", "UTF-8",
                    "-sourcepath", sourceRoot.toString(),
                    "-d", outDir.toString(),
                    mainJavaFile.toString());
            ProcessOutput res2 = runCaptured(cmd2, workDir);
            if (res2.exitCode() == 0) {
                long elapsed = System.currentTimeMillis() - start;
                String msg = String.format(
                        "Izole modda derlendi (klasordeki ilgisiz hatali yardimci dosya atlandi, %d ms)", elapsed);
                return new CompileResult(true, outDir.toString(), msg, elapsed);
            }
            deleteRecursive(outDir);
            long elapsed = System.currentTimeMillis() - start;
            return new CompileResult(false, "", res2.output().isBlank() ? res1.output() : res2.output(), elapsed);
        }

        deleteRecursive(outDir);
        long elapsed = System.currentTimeMillis() - start;
        return new CompileResult(false, "", res1.output(), elapsed);
    }

    private CompileResult compileMaven(TargetEntry target, long start) throws IOException, InterruptedException {
        Path workDir = target.resolveWorkingDir(repoRoot);
        if (mvnBin == null) {
            long elapsed = System.currentTimeMillis() - start;
            return new CompileResult(false, "",
                    "Maven (mvn) bulunamadi. Lutfen Maven kurun ve PATH'e ekleyin.", elapsed);
        }

        List<String> compileCmd = List.of(mvnBin, "-B", "compile");
        ProcessOutput compileOut = runCaptured(compileCmd, workDir);
        if (compileOut.exitCode() != 0) {
            long elapsed = System.currentTimeMillis() - start;
            return new CompileResult(false, "", compileOut.output(), elapsed);
        }

        Path cpFile = workDir.resolve(Path.of("target", "runtime.cp"));
        Path pomFile = workDir.resolve("pom.xml");
        boolean needCp = !Files.isRegularFile(cpFile)
                || lastModifiedMillis(pomFile) > lastModifiedMillis(cpFile);

        if (needCp) {
            List<String> cpCmd = List.of(
                    mvnBin, "-B", "-q",
                    "dependency:build-classpath",
                    "-Dmdep.outputFile=target/runtime.cp");
            ProcessOutput cpOut = runCaptured(cpCmd, workDir);
            if (cpOut.exitCode() != 0) {
                long elapsed = System.currentTimeMillis() - start;
                return new CompileResult(false, "",
                        "Maven classpath olusturulamadi:\n" + cpOut.output(), elapsed);
            }
        }

        String classesDir = workDir.resolve(Path.of("target", "classes")).toString();
        String extraCp = Files.isRegularFile(cpFile)
                ? Files.readString(cpFile, StandardCharsets.UTF_8).trim()
                : "";
        String fullCp = extraCp.isEmpty() ? classesDir : classesDir + File.pathSeparator + extraCp;

        long elapsed = System.currentTimeMillis() - start;
        return new CompileResult(true, fullCp,
                String.format("Maven derlemesi basarili (%d ms)", elapsed), elapsed);
    }

    /**
     * Runs the target in the foreground with inherited stdio and working directory strictly set
     * to the target's project directory.
     */
    public int runInteractive(TargetEntry target, String classpath, List<String> args)
            throws IOException, InterruptedException {
        Path workDir = target.resolveWorkingDir(repoRoot);
        List<String> cmd = new ArrayList<>();
        cmd.add(javaBin);
        cmd.add("-Dfile.encoding=UTF-8");
        cmd.add("-cp");
        cmd.add(classpath);
        cmd.add(target.getMainClass());
        cmd.addAll(args);

        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.directory(workDir.toFile());
        pb.inheritIO();

        Process process = pb.start();
        return process.waitFor();
    }

    public List<DirectoryItem> listWorkingDirItems(TargetEntry target) {
        Path workDir = target.resolveWorkingDir(repoRoot);
        if (!Files.isDirectory(workDir)) {
            return List.of();
        }
        List<DirectoryItem> items = new ArrayList<>();
        try (Stream<Path> stream = Files.list(workDir)) {
            List<Path> entries = stream
                    .filter(p -> {
                        String n = p.getFileName().toString();
                        return !n.startsWith(".") && !n.equals("bin") && !n.equals("target") && !n.equals("out");
                    })
                    .sorted(Comparator
                            .comparing((Path p) -> !Files.isDirectory(p))
                            .thenComparing(p -> p.getFileName().toString().toLowerCase(Locale.ROOT)))
                    .toList();

            for (Path p : entries) {
                String name = p.getFileName().toString();
                if (Files.isDirectory(p)) {
                    long javaCount = countJavaFiles(p);
                    String detail = javaCount > 0 ? javaCount + " .java" : "dizin";
                    items.add(new DirectoryItem(name + "/", true, detail));
                } else {
                    items.add(new DirectoryItem(name, false, formatSize(Files.size(p))));
                }
            }
        } catch (IOException ignored) {
            // preview fallback
        }
        return items;
    }

    public Path resolveSourceRoot(TargetEntry target) {
        Path workDir = target.resolveWorkingDir(repoRoot);
        if (target.isMaven()) {
            Path std = workDir.resolve(Path.of("src", "main", "java"));
            if (Files.isDirectory(std)) {
                return std;
            }
            Path src = workDir.resolve("src");
            return Files.isDirectory(src) ? src : workDir;
        }

        List<Path> candidates = List.of(
                workDir.resolve(Path.of("src", "main", "java")),
                workDir.resolve("src"),
                workDir.resolve("SRC"),
                workDir);

        for (Path cand : candidates) {
            if (Files.isDirectory(cand) && locateMainJavaFile(cand, target.getMainClass()) != null) {
                return cand;
            }
        }
        for (Path cand : candidates) {
            if (Files.isDirectory(cand)) {
                return cand;
            }
        }
        return workDir;
    }

    public static List<String> parseCliArgs(String rawArgs) {
        List<String> result = new ArrayList<>();
        if (rawArgs == null || rawArgs.isBlank()) {
            return result;
        }
        StringBuilder current = new StringBuilder();
        boolean inSingle = false;
        boolean inDouble = false;
        boolean hasToken = false;

        for (int i = 0; i < rawArgs.length(); i++) {
            char c = rawArgs.charAt(i);
            if (c == '\\' && !inSingle && i + 1 < rawArgs.length()) {
                current.append(rawArgs.charAt(++i));
                hasToken = true;
            } else if (c == '\'' && !inDouble) {
                inSingle = !inSingle;
                hasToken = true;
            } else if (c == '"' && !inSingle) {
                inDouble = !inDouble;
                hasToken = true;
            } else if (Character.isWhitespace(c) && !inSingle && !inDouble) {
                if (hasToken) {
                    result.add(current.toString());
                    current.setLength(0);
                    hasToken = false;
                }
            } else {
                current.append(c);
                hasToken = true;
            }
        }
        if (hasToken) {
            result.add(current.toString());
        }
        return result;
    }

    private Path locateMainJavaFile(Path sourceRoot, String mainClass) {
        String relJava = mainClass.replace('.', File.separatorChar) + ".java";
        Path direct = sourceRoot.resolve(relJava);
        if (Files.isRegularFile(direct)) {
            return direct;
        }
        // Fallback: inspect .java files in sourceRoot in case filename differs from class name
        try (Stream<Path> walk = Files.walk(sourceRoot)) {
            for (Path p : walk.filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString().endsWith(".java"))
                    .toList()) {
                if (isInsideExcludedSubdir(sourceRoot, p)) {
                    continue;
                }
                JavaSourceInspector.SourceInfo info = JavaSourceInspector.inspect(p);
                if (info.hasMain() && info.fqcn().equals(mainClass)) {
                    return p;
                }
            }
        } catch (IOException ignored) {
            // ignore
        }
        return null;
    }

    private List<Path> collectHelperJavaFiles(Path sourceRoot, Path mainJavaFile) throws IOException {
        List<Path> helpers = new ArrayList<>();
        boolean flatRoot = sourceRoot.equals(mainJavaFile.getParent())
                && !sourceRoot.getFileName().toString().equalsIgnoreCase("src")
                && !sourceRoot.getFileName().toString().equals("java");

        List<Path> allJava = new ArrayList<>();
        collectSourceRootJava(sourceRoot, flatRoot, allJava);

        for (Path jf : allJava) {
            if (jf.normalize().equals(mainJavaFile.normalize())) {
                continue;
            }
            try {
                JavaSourceInspector.SourceInfo info = JavaSourceInspector.inspect(jf);
                if (!info.hasMain()) {
                    helpers.add(jf);
                }
            } catch (IOException ignored) {
                // ignore unreadable helper
            }
        }
        return helpers;
    }

    private void collectSourceRootJava(Path dir, boolean flatRoot, List<Path> out) throws IOException {
        try (Stream<Path> stream = Files.list(dir)) {
            List<Path> entries = stream.toList();
            for (Path entry : entries) {
                String name = entry.getFileName().toString();
                if (Files.isRegularFile(entry) && name.endsWith(".java")) {
                    out.add(entry);
                } else if (Files.isDirectory(entry) && !name.startsWith(".")
                        && !ProjectScanner.EXCLUDED_NESTED_DIRS.contains(name)) {
                    if (!flatRoot || isPackageSubdir(dir, entry)) {
                        collectSourceRootJava(entry, false, out);
                    }
                }
            }
        }
    }

    private boolean isPackageSubdir(Path root, Path subDir) {
        if (Files.isRegularFile(subDir.resolve("pom.xml"))
                || Files.isDirectory(subDir.resolve("src"))
                || Files.isDirectory(subDir.resolve("SRC"))) {
            return false;
        }
        try (Stream<Path> walk = Files.walk(subDir)) {
            for (Path p : walk.filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString().endsWith(".java"))
                    .toList()) {
                JavaSourceInspector.SourceInfo info = JavaSourceInspector.inspect(p);
                String expectedPkg = ProjectScanner.toUnixPath(root.relativize(p.getParent())).replace('/', '.');
                if (!info.packageName().isEmpty() && info.packageName().equals(expectedPkg)) {
                    return true;
                }
            }
        } catch (IOException ignored) {
            return false;
        }
        return false;
    }

    private boolean isInsideExcludedSubdir(Path base, Path file) {
        Path rel = base.relativize(file);
        for (int i = 0; i < rel.getNameCount() - 1; i++) {
            String part = rel.getName(i).toString();
            if (part.startsWith(".") || ProjectScanner.EXCLUDED_NESTED_DIRS.contains(part)) {
                return true;
            }
        }
        return false;
    }

    private Path simpleOutDir(Path workDir, String mainClass) {
        String safe = mainClass.replace('.', '_').replace('/', '_').replace('\\', '_');
        return workDir.resolve("bin").resolve(".launcher").resolve(safe);
    }

    private long newestJavaMillis(Path sourceRoot) {
        if (!Files.isDirectory(sourceRoot)) {
            return 0L;
        }
        long newest = 0L;
        try (Stream<Path> walk = Files.walk(sourceRoot)) {
            for (Path p : walk.filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString().endsWith(".java"))
                    .toList()) {
                if (isInsideExcludedSubdir(sourceRoot, p)) {
                    continue;
                }
                newest = Math.max(newest, lastModifiedMillis(p));
            }
        } catch (IOException ignored) {
            // ignore
        }
        return newest;
    }

    private long countJavaFiles(Path dir) {
        try (Stream<Path> walk = Files.walk(dir)) {
            return walk.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".java"))
                    .count();
        } catch (IOException e) {
            return 0;
        }
    }

    private static long lastModifiedMillis(Path path) {
        try {
            return Files.isRegularFile(path) ? Files.getLastModifiedTime(path).toMillis() : 0L;
        } catch (IOException e) {
            return 0L;
        }
    }

    private static String formatSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }
        if (bytes < 1024 * 1024) {
            return String.format(Locale.ROOT, "%.1f KB", bytes / 1024.0);
        }
        return String.format(Locale.ROOT, "%.1f MB", bytes / (1024.0 * 1024.0));
    }

    private static void cleanAndCreateDir(Path dir) throws IOException {
        deleteRecursive(dir);
        Files.createDirectories(dir);
    }

    private static void deleteRecursive(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(path)) {
            List<Path> paths = walk.sorted(Comparator.reverseOrder()).toList();
            for (Path p : paths) {
                Files.deleteIfExists(p);
            }
        }
    }

    private record ProcessOutput(int exitCode, String output) {
    }

    private static ProcessOutput runCaptured(List<String> command, Path workDir)
            throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(workDir.toFile());
        pb.redirectErrorStream(true);
        Process process = pb.start();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (InputStream is = process.getInputStream()) {
            is.transferTo(baos);
        }
        int code = process.waitFor();
        return new ProcessOutput(code, baos.toString(StandardCharsets.UTF_8).trim());
    }

    private static String resolveJdkTool(String name) {
        boolean windows = System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
        String exe = windows ? name + ".exe" : name;
        String javaHome = System.getProperty("java.home");
        if (javaHome != null && !javaHome.isBlank()) {
            Path candidate = Path.of(javaHome, "bin", exe);
            if (Files.isExecutable(candidate)) {
                return candidate.toString();
            }
        }
        return exe;
    }

    private static String resolveMavenTool() {
        boolean windows = System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
        String exe = windows ? "mvn.cmd" : "mvn";
        String pathEnv = System.getenv("PATH");
        if (pathEnv != null) {
            for (String dir : pathEnv.split(File.pathSeparator)) {
                if (dir.isBlank()) {
                    continue;
                }
                Path candidate = Path.of(dir, exe);
                if (Files.isExecutable(candidate)) {
                    return candidate.toString();
                }
            }
        }
        if (!windows) {
            for (String fallback : List.of("/opt/homebrew/bin/mvn", "/usr/local/bin/mvn", "/usr/bin/mvn")) {
                if (Files.isExecutable(Path.of(fallback))) {
                    return fallback;
                }
            }
        }
        return null;
    }
}
