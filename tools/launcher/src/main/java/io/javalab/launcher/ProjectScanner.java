package io.javalab.launcher;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Automatically discovers runnable Java targets across all non-excluded root category folders.
 * Supports both simple Java folders (flat, src/, SRC/, src/main/java/) and standard Maven projects (pom.xml).
 */
public final class ProjectScanner {

    public static final Set<String> EXCLUDED_ROOT_DIRS = Set.of(
            "IntelliJProjects",
            "tools",
            "target",
            "build",
            "out",
            "bin",
            "node_modules");

    public static final Set<String> EXCLUDED_NESTED_DIRS = Set.of(
            "target",
            "build",
            "out",
            "bin",
            "node_modules");

    public static final Path MANIFEST_RELATIVE_PATH = Path.of("target", "targets.manifest");

    private final Path repoRoot;
    private final Set<String> extraExcludedRoots;

    public ProjectScanner(Path repoRoot) {
        this(repoRoot, Set.of());
    }

    public ProjectScanner(Path repoRoot, Set<String> extraExcludedRoots) {
        this.repoRoot = repoRoot.toAbsolutePath().normalize();
        this.extraExcludedRoots = extraExcludedRoots;
    }

    public record ScanResult(
            List<String> categories,
            List<TargetEntry> targets,
            int skippedFiles) {
    }

    public ScanResult scanAndWriteManifest(boolean verbose, PrintStream log) throws IOException {
        ScanResult result = scan(verbose, log);
        writeManifest(result.targets());
        if (verbose && log != null) {
            log.printf("[sync] Manifest yazildi: %s (%d hedef, %d kategori)%n",
                    repoRoot.resolve(MANIFEST_RELATIVE_PATH),
                    result.targets().size(),
                    result.categories().size());
        }
        return result;
    }

    public ScanResult scan(boolean verbose, PrintStream log) throws IOException {
        List<String> categories = new ArrayList<>();
        List<TargetEntry> targets = new ArrayList<>();
        Set<String> seenKeys = new HashSet<>();
        int[] skippedFiles = {0};

        List<Path> rootEntries = listDirectoriesSorted(repoRoot);
        for (Path dir : rootEntries) {
            String name = dir.getFileName().toString();
            if (isExcludedRoot(name)) {
                if (verbose && log != null) {
                    log.printf("[skip] Kok dizin haric tutuldu: %s/%n", name);
                }
                continue;
            }
            categories.add(name);
            if (verbose && log != null) {
                log.printf("[cat ] Kategori taraniyor: %s/%n", name);
            }
            scanDirectory(name, dir, dir, targets, seenKeys, skippedFiles, verbose, log);
        }

        targets.sort(Comparator
                .comparing((TargetEntry t) -> FuzzyMatcher.normalizeTurkish(t.getCategory()))
                .thenComparing(t -> FuzzyMatcher.normalizeTurkish(t.getDisplayName()))
                .thenComparing(TargetEntry::getMainClass));

        return new ScanResult(categories, targets, skippedFiles[0]);
    }

    public void writeManifest(List<TargetEntry> targets) throws IOException {
        Path manifestPath = repoRoot.resolve(MANIFEST_RELATIVE_PATH);
        Files.createDirectories(manifestPath.getParent());
        List<String> lines = new ArrayList<>(targets.size());
        for (TargetEntry target : targets) {
            lines.add(target.toManifestLine());
        }
        Files.write(manifestPath, lines, StandardCharsets.UTF_8);
    }

    public List<TargetEntry> readManifest() throws IOException {
        Path manifestPath = repoRoot.resolve(MANIFEST_RELATIVE_PATH);
        if (!Files.exists(manifestPath)) {
            return List.of();
        }
        List<TargetEntry> targets = new ArrayList<>();
        for (String line : Files.readAllLines(manifestPath, StandardCharsets.UTF_8)) {
            TargetEntry entry = TargetEntry.fromManifestLine(line);
            if (entry != null) {
                targets.add(entry);
            }
        }
        return targets;
    }

    public List<String> discoverRootCategories() throws IOException {
        List<String> categories = new ArrayList<>();
        for (Path dir : listDirectoriesSorted(repoRoot)) {
            String name = dir.getFileName().toString();
            if (!isExcludedRoot(name)) {
                categories.add(name);
            }
        }
        return categories;
    }

    private boolean isExcludedRoot(String name) {
        if (name.startsWith(".")) {
            return true;
        }
        if (EXCLUDED_ROOT_DIRS.contains(name) || extraExcludedRoots.contains(name)) {
            return true;
        }
        return false;
    }

    private boolean isExcludedNested(String name) {
        return name.startsWith(".") || EXCLUDED_NESTED_DIRS.contains(name);
    }

    private void scanDirectory(
            String category,
            Path categoryDir,
            Path dir,
            List<TargetEntry> targets,
            Set<String> seenKeys,
            int[] skippedFiles,
            boolean verbose,
            PrintStream log) throws IOException {

        // 1. Standard Maven project (contains pom.xml)
        Path pomFile = dir.resolve("pom.xml");
        if (Files.isRegularFile(pomFile)) {
            Path mavenSrc = dir.resolve(Path.of("src", "main", "java"));
            if (Files.isDirectory(mavenSrc)) {
                scanSourceTree(category, categoryDir, dir, mavenSrc, TargetEntry.BUILD_MAVEN,
                        targets, seenKeys, skippedFiles, verbose, log);
            } else if (Files.isDirectory(dir.resolve("src"))) {
                scanSourceTree(category, categoryDir, dir, dir.resolve("src"), TargetEntry.BUILD_MAVEN,
                        targets, seenKeys, skippedFiles, verbose, log);
            }

            // Support multi-module Maven parent directories by recursing only into non-src subdirs
            for (Path sub : listDirectoriesSorted(dir)) {
                String subName = sub.getFileName().toString();
                if (isExcludedNested(subName) || subName.equalsIgnoreCase("src")) {
                    continue;
                }
                if (Files.isRegularFile(sub.resolve("pom.xml"))) {
                    scanDirectory(category, categoryDir, sub, targets, seenKeys, skippedFiles, verbose, log);
                }
            }
            return;
        }

        Set<String> consumedSubdirsLower = new HashSet<>();

        // 2. Standard Maven-like directory structure without pom.xml (src/main/java)
        Path actualSrcDir = findChildDirIgnoreCase(dir, "src");
        Path stdSrcMainJava = actualSrcDir != null
                ? actualSrcDir.resolve(Path.of("main", "java"))
                : null;
        if (stdSrcMainJava != null && Files.isDirectory(stdSrcMainJava)) {
            scanSourceTree(category, categoryDir, dir, stdSrcMainJava, TargetEntry.BUILD_SIMPLE,
                    targets, seenKeys, skippedFiles, verbose, log);
            consumedSubdirsLower.add(actualSrcDir.getFileName().toString().toLowerCase(Locale.ROOT));
        } else if (actualSrcDir != null) {
            // 3. Conventional src/ or SRC/ source folder
            scanSourceTree(category, categoryDir, dir, actualSrcDir, TargetEntry.BUILD_SIMPLE,
                    targets, seenKeys, skippedFiles, verbose, log);
            consumedSubdirsLower.add(actualSrcDir.getFileName().toString().toLowerCase(Locale.ROOT));
        }

        // 4. Flat simple layout: .java files directly inside dir (plus matching package subdirs)
        List<Path> directJavaFiles = listDirectJavaFiles(dir);
        if (!directJavaFiles.isEmpty()) {
            List<Path> flatSourceFiles = new ArrayList<>(directJavaFiles);

            for (Path sub : listDirectoriesSorted(dir)) {
                String subName = sub.getFileName().toString();
                if (isExcludedNested(subName) || consumedSubdirsLower.contains(subName.toLowerCase(Locale.ROOT))) {
                    continue;
                }
                if (looksLikePackageTree(dir, sub)) {
                    consumedSubdirsLower.add(subName.toLowerCase(Locale.ROOT));
                    collectJavaFilesRecursive(sub, flatSourceFiles);
                }
            }

            registerTargetsFromFiles(category, categoryDir, dir, dir, TargetEntry.BUILD_SIMPLE,
                    flatSourceFiles, targets, seenKeys, skippedFiles, verbose, log);
        }

        // 5. Recurse into remaining subdirectories
        for (Path sub : listDirectoriesSorted(dir)) {
            String subName = sub.getFileName().toString();
            if (isExcludedNested(subName) || consumedSubdirsLower.contains(subName.toLowerCase(Locale.ROOT))) {
                continue;
            }
            scanDirectory(category, categoryDir, sub, targets, seenKeys, skippedFiles, verbose, log);
        }
    }

    private void scanSourceTree(
            String category,
            Path categoryDir,
            Path projectDir,
            Path sourceRoot,
            String buildType,
            List<TargetEntry> targets,
            Set<String> seenKeys,
            int[] skippedFiles,
            boolean verbose,
            PrintStream log) throws IOException {

        List<Path> javaFiles = new ArrayList<>();
        collectJavaFilesRecursive(sourceRoot, javaFiles);
        if (javaFiles.isEmpty()) {
            return;
        }
        registerTargetsFromFiles(category, categoryDir, projectDir, sourceRoot, buildType,
                javaFiles, targets, seenKeys, skippedFiles, verbose, log);
    }

    private void registerTargetsFromFiles(
            String category,
            Path categoryDir,
            Path projectDir,
            Path sourceRoot,
            String buildType,
            List<Path> javaFiles,
            List<TargetEntry> targets,
            Set<String> seenKeys,
            int[] skippedFiles,
            boolean verbose,
            PrintStream log) {

        List<JavaSourceInspector.SourceInfo> mainFiles = new ArrayList<>();
        int helperCount = 0;

        for (Path javaFile : javaFiles) {
            try {
                JavaSourceInspector.SourceInfo info = JavaSourceInspector.inspect(javaFile);
                if (info.hasMain()) {
                    mainFiles.add(info);
                } else {
                    helperCount++;
                }
            } catch (IOException e) {
                skippedFiles[0]++;
                if (verbose && log != null) {
                    log.printf("[warn] Dosya okunamadi: %s (%s)%n", repoRoot.relativize(javaFile), e.getMessage());
                }
            }
        }

        if (mainFiles.isEmpty()) {
            return;
        }

        String relWorkDir = toUnixPath(repoRoot.relativize(projectDir));
        String relFromCat = projectDir.equals(categoryDir)
                ? ""
                : toUnixPath(categoryDir.relativize(projectDir));

        if (verbose && log != null) {
            log.printf("  [%s] %s (src: %s) -> %d ana hedef, %d yardimci sinif%n",
                    buildType.toUpperCase(Locale.ROOT),
                    relWorkDir,
                    toUnixPath(repoRoot.relativize(sourceRoot)),
                    mainFiles.size(),
                    helperCount);
        }

        for (JavaSourceInspector.SourceInfo info : mainFiles) {
            String mainClass = info.fqcn();
            String displayName = relFromCat.isEmpty()
                    ? mainClass
                    : relFromCat + " / " + mainClass;

            TargetEntry entry = new TargetEntry(category, displayName, mainClass, relWorkDir, buildType);
            if (seenKeys.add(entry.getUniqueKey())) {
                targets.add(entry);
                if (verbose && log != null) {
                    log.printf("       + %s (class: %s)%n", displayName, mainClass);
                }
            }
        }
    }

    /**
     * Checks if Java files inside a subdirectory declare a package matching the relative path
     * from parentSourceRoot (meaning it is a Java subpackage rather than an independent subproject).
     */
    private boolean looksLikePackageTree(Path parentSourceRoot, Path subDir) throws IOException {
        if (Files.isRegularFile(subDir.resolve("pom.xml"))
                || Files.isDirectory(subDir.resolve("src"))
                || Files.isDirectory(subDir.resolve("SRC"))) {
            return false;
        }
        List<Path> subJavaFiles = new ArrayList<>();
        collectJavaFilesRecursive(subDir, subJavaFiles);
        if (subJavaFiles.isEmpty()) {
            return false;
        }
        for (Path jf : subJavaFiles) {
            try {
                JavaSourceInspector.SourceInfo info = JavaSourceInspector.inspect(jf);
                String expectedPkg = toUnixPath(parentSourceRoot.relativize(jf.getParent())).replace('/', '.');
                if (!info.packageName().isEmpty() && info.packageName().equals(expectedPkg)) {
                    return true;
                }
            } catch (IOException ignored) {
                // ignore unreadable file
            }
        }
        return false;
    }

    private Path findChildDirIgnoreCase(Path parent, String targetName) throws IOException {
        for (Path dir : listDirectoriesSorted(parent)) {
            if (dir.getFileName().toString().equalsIgnoreCase(targetName)) {
                return dir;
            }
        }
        return null;
    }

    private List<Path> listDirectoriesSorted(Path parent) throws IOException {
        if (!Files.isDirectory(parent)) {
            return List.of();
        }
        List<Path> dirs = new ArrayList<>();
        try (Stream<Path> stream = Files.list(parent)) {
            stream.filter(Files::isDirectory)
                    .sorted(Comparator.comparing(p -> p.getFileName().toString().toLowerCase(Locale.ROOT)))
                    .forEach(dirs::add);
        }
        return dirs;
    }

    private List<Path> listDirectJavaFiles(Path dir) throws IOException {
        List<Path> files = new ArrayList<>();
        try (Stream<Path> stream = Files.list(dir)) {
            stream.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".java"))
                    .sorted(Comparator.comparing(p -> p.getFileName().toString().toLowerCase(Locale.ROOT)))
                    .forEach(files::add);
        }
        return files;
    }

    private void collectJavaFilesRecursive(Path root, List<Path> out) throws IOException {
        if (!Files.isDirectory(root)) {
            return;
        }
        List<Path> children = new ArrayList<>();
        try (Stream<Path> stream = Files.list(root)) {
            stream.sorted(Comparator.comparing(p -> p.getFileName().toString().toLowerCase(Locale.ROOT)))
                    .forEach(children::add);
        }
        for (Path child : children) {
            String name = child.getFileName().toString();
            if (Files.isDirectory(child)) {
                if (!isExcludedNested(name)) {
                    collectJavaFilesRecursive(child, out);
                }
            } else if (Files.isRegularFile(child) && name.endsWith(".java")) {
                out.add(child);
            }
        }
    }

    public static String toUnixPath(Path path) {
        return path.toString().replace('\\', '/');
    }
}
