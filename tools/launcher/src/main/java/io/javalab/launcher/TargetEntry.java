package io.javalab.launcher;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Manifest line representation:
 * Kategori|GörünenAd|AnaSınıf|ÇalışmaDizini|DerlemeTipi
 */
public final class TargetEntry {
    public static final String BUILD_SIMPLE = "simple";
    public static final String BUILD_MAVEN = "maven";

    private final String category;
    private final String displayName;
    private final String mainClass;
    private final String workingDir;
    private final String buildType;

    public TargetEntry(String category, String displayName, String mainClass, String workingDir, String buildType) {
        this.category = Objects.requireNonNull(category, "category");
        this.displayName = Objects.requireNonNull(displayName, "displayName");
        this.mainClass = Objects.requireNonNull(mainClass, "mainClass");
        this.workingDir = Objects.requireNonNull(workingDir, "workingDir");
        this.buildType = Objects.requireNonNull(buildType, "buildType");
    }

    public String getCategory() {
        return category;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMainClass() {
        return mainClass;
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public String getBuildType() {
        return buildType;
    }

    public boolean isMaven() {
        return BUILD_MAVEN.equalsIgnoreCase(buildType);
    }

    public Path resolveWorkingDir(Path repoRoot) {
        return repoRoot.resolve(workingDir).normalize();
    }

    public String getUniqueKey() {
        return category + "|" + workingDir + "|" + mainClass;
    }

    public String toManifestLine() {
        return String.join("|",
                sanitizeField(category),
                sanitizeField(displayName),
                sanitizeField(mainClass),
                sanitizeField(workingDir),
                sanitizeField(buildType));
    }

    public static TargetEntry fromManifestLine(String line) {
        if (line == null) {
            return null;
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty() || trimmed.startsWith("#")) {
            return null;
        }
        String[] parts = trimmed.split("\\|", -1);
        if (parts.length < 5) {
            return null;
        }
        return new TargetEntry(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim());
    }

    private static String sanitizeField(String value) {
        return value.replace("|", "/").replace("\r", "").replace("\n", "").trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TargetEntry that)) {
            return false;
        }
        return category.equals(that.category)
                && displayName.equals(that.displayName)
                && mainClass.equals(that.mainClass)
                && workingDir.equals(that.workingDir)
                && buildType.equals(that.buildType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, displayName, mainClass, workingDir, buildType);
    }

    @Override
    public String toString() {
        return toManifestLine();
    }
}
