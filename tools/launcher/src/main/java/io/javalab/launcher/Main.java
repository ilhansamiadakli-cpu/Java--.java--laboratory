package io.javalab.launcher;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for Java Laboratory Launcher.
 *
 * Modes:
 *   (no args)                     Launch interactive Lanterna TUI
 *   --sync [--verbose | --quiet]  Scan repository and update target/targets.manifest
 *   --list                        Print discovered targets from manifest
 *   --compile <query>             Compile the top matching target in isolation
 *   --run <query> [-- args...]    Compile and run the top matching target in its project directory
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) throws Exception {
        Path repoRoot = Path.of(System.getProperty("javalab.root", System.getProperty("user.dir")))
                .toAbsolutePath()
                .normalize();

        ProjectScanner scanner = new ProjectScanner(repoRoot);
        ProjectRunner runner = new ProjectRunner(repoRoot);

        if (args.length > 0) {
            switch (args[0]) {
                case "--sync" -> {
                    boolean verbose = true;
                    for (int i = 1; i < args.length; i++) {
                        if ("--quiet".equals(args[i]) || "-q".equals(args[i])) {
                            verbose = false;
                        } else if ("--verbose".equals(args[i]) || "-v".equals(args[i])) {
                            verbose = true;
                        }
                    }
                    scanner.scanAndWriteManifest(verbose, System.out);
                    return;
                }
                case "--list" -> {
                    List<TargetEntry> targets = ensureTargets(scanner);
                    for (TargetEntry t : targets) {
                        System.out.println(t.toManifestLine());
                    }
                    return;
                }
                case "--compile" -> {
                    if (args.length < 2) {
                        System.err.println("Kullanim: --compile <hedef-arama>");
                        System.exit(2);
                    }
                    TargetEntry target = resolveSingleTarget(scanner, args[1]);
                    ProjectRunner.CompileResult res = runner.compile(target);
                    System.out.println("Hedef  : " + target.getDisplayName());
                    System.out.println("Dizin  : " + target.resolveWorkingDir(repoRoot));
                    System.out.println("Durum  : " + (res.success() ? "BASARILI" : "HATA"));
                    if (!res.output().isBlank()) {
                        System.out.println(res.output());
                    }
                    System.exit(res.success() ? 0 : 1);
                }
                case "--run" -> {
                    if (args.length < 2) {
                        System.err.println("Kullanim: --run <hedef-arama> [-- arg1 arg2 ...]");
                        System.exit(2);
                    }
                    TargetEntry target = resolveSingleTarget(scanner, args[1]);
                    List<String> progArgs = new ArrayList<>();
                    boolean collecting = false;
                    for (int i = 2; i < args.length; i++) {
                        if (!collecting && "--".equals(args[i])) {
                            collecting = true;
                            continue;
                        }
                        progArgs.add(args[i]);
                    }
                    ProjectRunner.CompileResult res = runner.compile(target);
                    if (!res.success()) {
                        System.err.println("Derleme hatasi (" + target.getDisplayName() + "):");
                        System.err.println(res.output());
                        System.exit(1);
                    }
                    int exitCode = runner.runInteractive(target, res.classpath(), progArgs);
                    System.exit(exitCode);
                }
                default -> {
                    System.err.println("Bilinmeyen secenek: " + args[0]);
                    System.exit(2);
                }
            }
        }

        List<TargetEntry> targets = ensureTargets(scanner);
        List<String> categories = scanner.discoverRootCategories();
        LauncherTui tui = new LauncherTui(repoRoot, scanner, runner, categories, targets);
        tui.start();
    }

    private static List<TargetEntry> ensureTargets(ProjectScanner scanner) throws Exception {
        List<TargetEntry> targets = scanner.readManifest();
        if (targets.isEmpty()) {
            targets = scanner.scanAndWriteManifest(false, null).targets();
        }
        return targets;
    }

    private static TargetEntry resolveSingleTarget(ProjectScanner scanner, String query) throws Exception {
        List<TargetEntry> targets = ensureTargets(scanner);
        // Prefer exact match on mainClass or displayName first
        for (TargetEntry t : targets) {
            if (t.getMainClass().equals(query) || t.getDisplayName().equalsIgnoreCase(query)) {
                return t;
            }
        }
        List<FuzzyMatcher.ScoredTarget> ranked = FuzzyMatcher.filterAndRank(targets, query);
        if (ranked.isEmpty()) {
            System.err.println("Eslesen hedef bulunamadi: " + query);
            System.exit(1);
        }
        return ranked.get(0).target();
    }
}
