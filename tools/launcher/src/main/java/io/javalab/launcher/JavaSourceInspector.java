package io.javalab.launcher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lightweight, comment- and string-safe Java source analyzer for detecting
 * package declarations, public class names, and standard main methods.
 */
public final class JavaSourceInspector {

    private static final Pattern PACKAGE_PATTERN = Pattern.compile(
            "(?m)^\\s*package\\s+([\\p{L}_$][\\p{L}\\p{N}_$]*(?:\\.[\\p{L}_$][\\p{L}\\p{N}_$]*)*)\\s*;");

    private static final Pattern MAIN_METHOD_PATTERN = Pattern.compile(
            "\\b(?:public\\s+static|static\\s+public)\\s+void\\s+main\\s*\\(\\s*(?:final\\s+)?(?:java\\.lang\\.)?String\\s*(?:\\[\\s*\\]\\s*[\\p{L}_$][\\p{L}\\p{N}_$]*|[\\p{L}_$][\\p{L}\\p{N}_$]*\\s*\\[\\s*\\]|\\.\\.\\.\\s*[\\p{L}_$][\\p{L}\\p{N}_$]*)\\s*\\)");

    private static final Pattern PUBLIC_CLASS_PATTERN = Pattern.compile(
            "\\bpublic\\s+(?:final\\s+|abstract\\s+|sealed\\s+|non-sealed\\s+)?(?:class|record|enum)\\s+([\\p{L}_$][\\p{L}\\p{N}_$]*)");

    private static final Pattern ANY_CLASS_PATTERN = Pattern.compile(
            "\\b(?:class|record|enum)\\s+([\\p{L}_$][\\p{L}\\p{N}_$]*)");

    public record SourceInfo(
            Path file,
            String packageName,
            String simpleClassName,
            boolean hasMain) {
        public String fqcn() {
            return packageName.isEmpty() ? simpleClassName : packageName + "." + simpleClassName;
        }
    }

    private JavaSourceInspector() {
    }

    public static SourceInfo inspect(Path javaFile) throws IOException {
        String raw = Files.readString(javaFile, StandardCharsets.UTF_8);
        return inspectContent(javaFile, raw);
    }

    public static SourceInfo inspectContent(Path javaFile, String rawSource) {
        String cleaned = stripCommentsAndLiterals(rawSource);

        String packageName = "";
        Matcher pkgMatcher = PACKAGE_PATTERN.matcher(cleaned);
        if (pkgMatcher.find()) {
            packageName = pkgMatcher.group(1).trim();
        }

        boolean hasMain = MAIN_METHOD_PATTERN.matcher(cleaned).find();

        String fileStem = stripJavaExtension(javaFile.getFileName().toString());
        String className = fileStem;

        Matcher publicClassMatcher = PUBLIC_CLASS_PATTERN.matcher(cleaned);
        if (publicClassMatcher.find()) {
            className = publicClassMatcher.group(1);
        } else if (hasMain) {
            Matcher anyClassMatcher = ANY_CLASS_PATTERN.matcher(cleaned);
            if (anyClassMatcher.find()) {
                className = anyClassMatcher.group(1);
            }
        }

        return new SourceInfo(javaFile, packageName, className, hasMain);
    }

    private static String stripJavaExtension(String fileName) {
        if (fileName.endsWith(".java")) {
            return fileName.substring(0, fileName.length() - 5);
        }
        return fileName;
    }

    /**
     * Replaces line comments, block comments, string literals, text blocks, and char literals
     * with spaces while preserving newlines so regexes do not produce false matches.
     */
    static String stripCommentsAndLiterals(String src) {
        int len = src.length();
        StringBuilder sb = new StringBuilder(len);
        int i = 0;
        while (i < len) {
            char c = src.charAt(i);
            char next = (i + 1 < len) ? src.charAt(i + 1) : '\0';

            // Line comment
            if (c == '/' && next == '/') {
                i += 2;
                while (i < len && src.charAt(i) != '\n') {
                    i++;
                }
                continue;
            }
            // Block comment
            if (c == '/' && next == '*') {
                i += 2;
                while (i + 1 < len && !(src.charAt(i) == '*' && src.charAt(i + 1) == '/')) {
                    if (src.charAt(i) == '\n') {
                        sb.append('\n');
                    }
                    i++;
                }
                i = Math.min(len, i + 2);
                sb.append(' ');
                continue;
            }
            // Text block """..."""
            if (c == '"' && i + 2 < len && src.charAt(i + 1) == '"' && src.charAt(i + 2) == '"') {
                i += 3;
                while (i + 2 < len && !(src.charAt(i) == '"' && src.charAt(i + 1) == '"' && src.charAt(i + 2) == '"')) {
                    if (src.charAt(i) == '\\' && i + 1 < len) {
                        i += 2;
                    } else {
                        if (src.charAt(i) == '\n') {
                            sb.append('\n');
                        }
                        i++;
                    }
                }
                i = Math.min(len, i + 3);
                sb.append(' ');
                continue;
            }
            // String literal
            if (c == '"') {
                i++;
                while (i < len && src.charAt(i) != '"') {
                    if (src.charAt(i) == '\\' && i + 1 < len) {
                        i += 2;
                    } else {
                        if (src.charAt(i) == '\n') {
                            sb.append('\n');
                        }
                        i++;
                    }
                }
                i = Math.min(len, i + 1);
                sb.append(' ');
                continue;
            }
            // Char literal
            if (c == '\'') {
                i++;
                while (i < len && src.charAt(i) != '\'') {
                    if (src.charAt(i) == '\\' && i + 1 < len) {
                        i += 2;
                    } else {
                        i++;
                    }
                }
                i = Math.min(len, i + 1);
                sb.append(' ');
                continue;
            }
            sb.append(c);
            i++;
        }
        return sb.toString();
    }
}
