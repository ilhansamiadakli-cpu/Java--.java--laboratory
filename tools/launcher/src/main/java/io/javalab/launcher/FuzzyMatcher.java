package io.javalab.launcher;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Turkish-compatible, case-insensitive, multi-word, acronym-aware fuzzy matcher.
 */
public final class FuzzyMatcher {

    public record ScoredTarget(TargetEntry target, int score) {
    }

    private FuzzyMatcher() {
    }

    /**
     * Folds Turkish characters (ç, ğ, ı, İ, ö, ş, ü and uppercase forms) and combining diacritics
     * into plain lowercase ASCII.
     */
    public static String normalizeTurkish(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        StringBuilder pre = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            switch (ch) {
                case 'ç', 'Ç' -> pre.append('c');
                case 'ğ', 'Ğ' -> pre.append('g');
                case 'ı', 'I', 'İ' -> pre.append('i');
                case 'ö', 'Ö' -> pre.append('o');
                case 'ş', 'Ş' -> pre.append('s');
                case 'ü', 'Ü' -> pre.append('u');
                default -> pre.append(ch);
            }
        }
        String decomposed = Normalizer.normalize(pre.toString(), Normalizer.Form.NFD);
        StringBuilder out = new StringBuilder(decomposed.length());
        for (int i = 0; i < decomposed.length(); i++) {
            char ch = decomposed.charAt(i);
            if (Character.getType(ch) != Character.NON_SPACING_MARK) {
                out.append(Character.toLowerCase(ch));
            }
        }
        return out.toString();
    }

    public static List<ScoredTarget> filterAndRank(List<TargetEntry> targets, String query) {
        String normQuery = normalizeTurkish(query).trim();
        if (normQuery.isEmpty()) {
            List<ScoredTarget> all = new ArrayList<>(targets.size());
            for (TargetEntry t : targets) {
                all.add(new ScoredTarget(t, 0));
            }
            return all;
        }

        String[] tokens = normQuery.split("\\s+");
        List<ScoredTarget> matches = new ArrayList<>();
        for (TargetEntry target : targets) {
            int score = scoreTarget(target, tokens);
            if (score > 0) {
                matches.add(new ScoredTarget(target, score));
            }
        }

        matches.sort(Comparator
                .comparingInt(ScoredTarget::score).reversed()
                .thenComparingInt(st -> st.target().getDisplayName().length())
                .thenComparing(st -> normalizeTurkish(st.target().getDisplayName())));
        return matches;
    }

    public static int scoreTarget(TargetEntry target, String query) {
        String normQuery = normalizeTurkish(query).trim();
        if (normQuery.isEmpty()) {
            return 1;
        }
        return scoreTarget(target, normQuery.split("\\s+"));
    }

    private static int scoreTarget(TargetEntry target, String[] tokens) {
        String rawDisplay = target.getDisplayName();
        String rawMain = target.getMainClass();
        String rawCategory = target.getCategory();
        String rawWorkDir = target.getWorkingDir();

        String combinedRaw = rawDisplay + " " + rawMain + " " + rawCategory + " " + rawWorkDir;
        String combinedNorm = normalizeTurkish(combinedRaw);

        int totalScore = 0;
        int lastMatchPos = -1;
        int inOrderHits = 0;

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }
            int mainScore = scoreField(rawMain, token, 1.35);
            int displayScore = scoreField(rawDisplay, token, 1.25);
            int workDirScore = scoreField(rawWorkDir, token, 0.90);
            int catScore = scoreField(rawCategory, token, 0.85);
            int combinedScore = scoreField(combinedRaw, token, 0.70);

            int best = Math.max(mainScore, Math.max(displayScore, Math.max(workDirScore, Math.max(catScore, combinedScore))));
            if (best <= 0) {
                return 0;
            }
            totalScore += best;

            int idx = combinedNorm.indexOf(token);
            if (idx >= 0) {
                if (idx >= lastMatchPos) {
                    inOrderHits++;
                    lastMatchPos = idx;
                }
            }
        }

        if (tokens.length > 1 && inOrderHits == tokens.length) {
            totalScore += 45;
        }
        return totalScore;
    }

    static int scoreField(String rawField, String normToken, double weight) {
        if (rawField == null || rawField.isEmpty() || normToken.isEmpty()) {
            return 0;
        }
        String normField = normalizeTurkish(rawField);
        if (normField.isEmpty()) {
            return 0;
        }

        int baseScore = 0;

        // 1. Exact full match
        if (normField.equals(normToken)) {
            baseScore = Math.max(baseScore, 400);
        }

        // 2. Prefix match
        if (normField.startsWith(normToken)) {
            baseScore = Math.max(baseScore, 300 - Math.min(40, normField.length() - normToken.length()));
        }

        // 3. Direct substring match (extra bonus when starting on a word/CamelCase boundary)
        int subIdx = normField.indexOf(normToken);
        if (subIdx >= 0) {
            int subScore = 200;
            if (isWordBoundary(rawField, subIdx)) {
                subScore += 65;
            }
            subScore -= Math.min(50, subIdx * 2);
            subScore -= Math.min(30, Math.max(0, normField.length() - normToken.length()) / 2);
            baseScore = Math.max(baseScore, Math.max(80, subScore));
        }

        // 4. Acronym / initialism match (e.g., "g5k" -> Gorev5_KutuphaneDemo, "ntp" -> Nesne_Tabanlı_Programlama)
        String acronym = extractAcronym(rawField);
        if (!acronym.isEmpty()) {
            if (acronym.equals(normToken)) {
                baseScore = Math.max(baseScore, 260);
            } else if (acronym.startsWith(normToken)) {
                baseScore = Math.max(baseScore, 210);
            } else if (acronym.contains(normToken)) {
                baseScore = Math.max(baseScore, 165);
            } else if (isSubsequence(acronym, normToken)) {
                baseScore = Math.max(baseScore, 130);
            }
        }

        // 5. Fuzzy subsequence match with boundary & consecutive bonuses
        int fuzzy = scoreSubsequence(rawField, normField, normToken);
        if (fuzzy > 0) {
            baseScore = Math.max(baseScore, fuzzy);
        }

        if (baseScore <= 0) {
            return 0;
        }
        return (int) Math.round(baseScore * weight);
    }

    private static int scoreSubsequence(String rawField, String normField, String token) {
        int ti = 0;
        int score = 0;
        int consecutive = 0;
        int firstMatch = -1;
        int prevMatch = -1;

        for (int fi = 0; fi < normField.length() && ti < token.length(); fi++) {
            if (normField.charAt(fi) == token.charAt(ti)) {
                if (firstMatch < 0) {
                    firstMatch = fi;
                }
                score += 18;
                if (prevMatch == fi - 1) {
                    consecutive++;
                    score += 12 + Math.min(18, consecutive * 4);
                } else {
                    consecutive = 0;
                    if (prevMatch >= 0) {
                        int gap = fi - prevMatch - 1;
                        score -= Math.min(10, gap);
                    }
                }
                if (isWordBoundary(rawField, fi)) {
                    score += 22;
                }
                prevMatch = fi;
                ti++;
            }
        }

        if (ti < token.length()) {
            return 0;
        }

        int span = (prevMatch - firstMatch + 1);
        int extraSpread = Math.max(0, span - token.length());
        score -= Math.min(35, extraSpread * 2);
        score -= Math.min(20, firstMatch);
        return Math.max(20, score);
    }

    static String extractAcronym(String rawField) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rawField.length(); i++) {
            char ch = rawField.charAt(i);
            if (!Character.isLetterOrDigit(ch)) {
                continue;
            }
            if (isWordBoundary(rawField, i)) {
                sb.append(normalizeTurkish(String.valueOf(ch)));
            }
        }
        return sb.toString();
    }

    private static boolean isWordBoundary(String raw, int index) {
        if (index <= 0 || index >= raw.length()) {
            return index == 0;
        }
        char curr = raw.charAt(index);
        char prev = raw.charAt(index - 1);
        if (!Character.isLetterOrDigit(prev)) {
            return true;
        }
        if (Character.isUpperCase(curr) && Character.isLowerCase(prev)) {
            return true;
        }
        if (Character.isDigit(curr) && !Character.isDigit(prev)) {
            return true;
        }
        if (Character.isLetter(curr) && Character.isDigit(prev)) {
            return true;
        }
        return false;
    }

    private static boolean isSubsequence(String haystack, String needle) {
        int ni = 0;
        for (int hi = 0; hi < haystack.length() && ni < needle.length(); hi++) {
            if (haystack.charAt(hi) == needle.charAt(ni)) {
                ni++;
            }
        }
        return ni == needle.length();
    }
}
