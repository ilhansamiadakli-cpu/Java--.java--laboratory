package io.javalab.launcher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LauncherCoreTest {

    @Test
    void turkishNormalizationHandlesAllCharacters() {
        assertEquals("cgiosu", FuzzyMatcher.normalizeTurkish("çğıöşü"));
        assertEquals("cgiiiosu", FuzzyMatcher.normalizeTurkish("ÇĞıIİÖŞÜ"));
        assertEquals("asalsayibulma", FuzzyMatcher.normalizeTurkish("AsalSayıBulma"));
        assertEquals("nesne_tabanli_programlama_dersi",
                FuzzyMatcher.normalizeTurkish("Nesne_Tabanlı_Programlama_dersi"));
    }

    @Test
    void fuzzyMatcherSupportsMultiWordTurkishAndAcronyms() {
        List<TargetEntry> entries = List.of(
                new TargetEntry("Denemeler", "AsalSayıBulma", "AsalSayıBulma", "Denemeler", "simple"),
                new TargetEntry("Nesne_Tabanlı_Programlama_dersi", "Hafta_04_lab / Gorev5_KutuphaneDemo",
                        "Gorev5_KutuphaneDemo", "Nesne_Tabanlı_Programlama_dersi/Hafta_04_lab", "simple"),
                new TargetEntry("Nesne_Tabanlı_Programlama_dersi", "Hafta_04_ders / disarida.PaketDisiDemo",
                        "disarida.PaketDisiDemo", "Nesne_Tabanlı_Programlama_dersi/Hafta_04_ders", "simple")
        );

        var asal = FuzzyMatcher.filterAndRank(entries, "asal sayi");
        assertFalse(asal.isEmpty());
        assertEquals("AsalSayıBulma", asal.get(0).target().getMainClass());

        var kutup = FuzzyMatcher.filterAndRank(entries, "hafta 4 kutup");
        assertFalse(kutup.isEmpty());
        assertEquals("Gorev5_KutuphaneDemo", kutup.get(0).target().getMainClass());

        var acronym = FuzzyMatcher.filterAndRank(entries, "g5kd");
        assertFalse(acronym.isEmpty());
        assertEquals("Gorev5_KutuphaneDemo", acronym.get(0).target().getMainClass());
    }

    @Test
    void sourceInspectorHandlesVariousMainSignaturesAndComments() {
        String code = """
                // public static void main(String[] args) {}
                /* public static void main(String[] args) {} */
                package ornek.alt;
                public class Demo {
                    public  static  void main(String Args[]) {
                        System.out.println("public static void main(String[] args)");
                    }
                }
                """;
        var info = JavaSourceInspector.inspectContent(Path.of("Demo.java"), code);
        assertTrue(info.hasMain());
        assertEquals("ornek.alt", info.packageName());
        assertEquals("ornek.alt.Demo", info.fqcn());

        String helperCode = """
                // public static void main(String[] args) {}
                public class Helper {
                    public String msg() { return "public static void main(String[] args)"; }
                }
                """;
        var helperInfo = JavaSourceInspector.inspectContent(Path.of("Helper.java"), helperCode);
        assertFalse(helperInfo.hasMain());
    }

    @Test
    void scannerExcludesIdeAndHiddenDirsAndIsolatesBrokenSiblings(@TempDir Path tempRepo) throws Exception {
        // Excluded IntelliJProjects
        Path ij = tempRepo.resolve("IntelliJProjects/Secret");
        Files.createDirectories(ij);
        Files.writeString(ij.resolve("SecretMain.java"),
                "public class SecretMain { public static void main(String[] args) {} }", StandardCharsets.UTF_8);

        // Simple flat project with a broken sibling + data file + helper
        Path flat = tempRepo.resolve("Denemeler/ornek");
        Files.createDirectories(flat);
        Files.writeString(flat.resolve("deneme.txt"), "merhaba dosya\n", StandardCharsets.UTF_8);
        Files.writeString(flat.resolve("Helper.java"),
                "public class Helper { public static String greet() { return \"selam\"; } }", StandardCharsets.UTF_8);
        Files.writeString(flat.resolve("GoodMain.java"), """
                import java.nio.file.*;
                public class GoodMain {
                    public static void main(String[] args) throws Exception {
                        System.out.println(Helper.greet() + ":" + Files.readString(Path.of("deneme.txt")).trim());
                    }
                }
                """, StandardCharsets.UTF_8);
        Files.writeString(flat.resolve("BrokenMain.java"),
                "public class BrokenMain { public static void main(String[] args) { SYNTAX ERROR ;;; } }",
                StandardCharsets.UTF_8);

        // Maven project layout
        Path mavenProj = tempRepo.resolve("Projeler/mini-app/src/main/java/com/demo");
        Files.createDirectories(mavenProj);
        Files.writeString(tempRepo.resolve("Projeler/mini-app/pom.xml"), "<project/>", StandardCharsets.UTF_8);
        Files.writeString(mavenProj.resolve("App.java"),
                "package com.demo;\npublic class App { public static void main(String[] args) {} }",
                StandardCharsets.UTF_8);

        // Uppercase SRC directory (tests case-insensitive filesystem deduplication)
        Path upperSrc = tempRepo.resolve("KlasorSRC/SRC");
        Files.createDirectories(upperSrc);
        Files.writeString(upperSrc.resolve("Sample.java"),
                "public class Sample { public static void main(String[] args) {} }", StandardCharsets.UTF_8);

        ProjectScanner scanner = new ProjectScanner(tempRepo);
        ProjectScanner.ScanResult scan = scanner.scanAndWriteManifest(false, null);

        assertFalse(scan.categories().contains("IntelliJProjects"));
        assertTrue(scan.categories().contains("Denemeler"));
        assertTrue(scan.categories().contains("Projeler"));
        assertTrue(scan.categories().contains("KlasorSRC"));
        assertEquals(4, scan.targets().size());

        TargetEntry good = scan.targets().stream()
                .filter(t -> t.getMainClass().equals("GoodMain")).findFirst().orElseThrow();
        TargetEntry broken = scan.targets().stream()
                .filter(t -> t.getMainClass().equals("BrokenMain")).findFirst().orElseThrow();
        TargetEntry mavenTarget = scan.targets().stream()
                .filter(t -> t.getMainClass().equals("com.demo.App")).findFirst().orElseThrow();

        assertEquals("simple", good.getBuildType());
        assertEquals("Denemeler/ornek", good.getWorkingDir());
        assertEquals("maven", mavenTarget.getBuildType());
        assertEquals("Projeler/mini-app", mavenTarget.getWorkingDir());

        // Isolation test: GoodMain compiles even though BrokenMain is in the exact same directory!
        ProjectRunner runner = new ProjectRunner(tempRepo);
        ProjectRunner.CompileResult goodCompile = runner.compile(good);
        assertTrue(goodCompile.success(), "GoodMain should compile despite BrokenMain: " + goodCompile.output());

        ProjectRunner.CompileResult brokenCompile = runner.compile(broken);
        assertFalse(brokenCompile.success(), "BrokenMain should fail compilation cleanly");
    }

    @Test
    void cliArgsParserSupportsQuotesAndEscapes() {
        assertEquals(List.of("--flag", "hello world", "single arg", "a b"),
                ProjectRunner.parseCliArgs("--flag \"hello world\" 'single arg' a\\ b"));
    }
}
