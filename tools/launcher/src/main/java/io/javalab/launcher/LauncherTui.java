package io.javalab.launcher;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Full-screen Lanterna TUI launcher for Java Laboratory.
 */
public final class LauncherTui {

    private static final String ALL_TAB = "Tümü";

    private static final String ANSI_RESET = "\033[0m";
    private static final String ANSI_CYAN = "\033[96m";
    private static final String ANSI_GREEN = "\033[92m";
    private static final String ANSI_RED = "\033[91m";
    private static final String ANSI_GRAY = "\033[90m";
    private static final String RUN_SEPARATOR =
            ANSI_GRAY + "────────────────────────────────────────────────────────────────────────" + ANSI_RESET;

    private enum FocusArea {
        SEARCH,
        LIST,
        ARGS
    }

    private final Path repoRoot;
    private final ProjectScanner scanner;
    private final ProjectRunner runner;

    private List<String> tabs = new ArrayList<>();
    private List<TargetEntry> allTargets = new ArrayList<>();
    private List<FuzzyMatcher.ScoredTarget> filteredTargets = new ArrayList<>();

    private int activeTabIndex = 0;
    private int selectedIndex = 0;
    private int scrollOffset = 0;
    private FocusArea focusArea = FocusArea.SEARCH;

    private final StringBuilder searchQuery = new StringBuilder();
    private int searchCursor = 0;

    private final StringBuilder cliArgs = new StringBuilder();
    private int argsCursor = 0;

    private final Map<String, Boolean> failedBuilds = new HashMap<>();
    private final Map<String, String> lastBuildLogs = new HashMap<>();
    private String statusMessage = "Hazır. Yazmaya başlayarak arama yapabilirsiniz.";

    private Terminal terminal;
    private Screen screen;

    public LauncherTui(Path repoRoot, ProjectScanner scanner, ProjectRunner runner,
                       List<String> rootCategories, List<TargetEntry> initialTargets) {
        this.repoRoot = repoRoot;
        this.scanner = scanner;
        this.runner = runner;
        loadData(rootCategories, initialTargets);
    }

    private void loadData(List<String> rootCategories, List<TargetEntry> targets) {
        this.allTargets = new ArrayList<>(targets);
        Set<String> tabSet = new LinkedHashSet<>();
        tabSet.add(ALL_TAB);
        tabSet.addAll(rootCategories);
        for (TargetEntry entry : targets) {
            tabSet.add(entry.getCategory());
        }
        this.tabs = new ArrayList<>(tabSet);
        if (activeTabIndex >= tabs.size()) {
            activeTabIndex = 0;
        }
        recomputeFiltered();
    }

    public void start() throws IOException {
        openScreen();
        try {
            boolean running = true;
            while (running) {
                screen.doResizeIfNecessary();
                render();
                KeyStroke key = screen.readInput();
                if (key == null) {
                    continue;
                }
                running = handleKey(key);
            }
        } finally {
            closeScreen();
        }
    }

    private void openScreen() throws IOException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory(System.out, System.in, StandardCharsets.UTF_8);
        factory.setForceTextTerminal(true);
        terminal = factory.createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.clear();
    }

    private void closeScreen() {
        if (screen != null) {
            try {
                screen.stopScreen();
            } catch (IOException ignored) {
                // ignore shutdown error
            }
            try {
                screen.close();
            } catch (IOException ignored) {
                // ignore shutdown error
            }
            screen = null;
        }
        if (terminal != null) {
            try {
                terminal.close();
            } catch (IOException ignored) {
                // ignore shutdown error
            }
            terminal = null;
        }
    }

    private boolean handleKey(KeyStroke key) throws IOException {
        KeyType type = key.getKeyType();

        if (type == KeyType.EOF) {
            return false;
        }

        // Ctrl+C or Ctrl+Q quits from anywhere
        if (type == KeyType.Character && key.isCtrlDown()) {
            char ch = Character.toLowerCase(key.getCharacter());
            if (ch == 'c' || ch == 'q') {
                return false;
            }
            if (ch == 'r') {
                compileSelectedInPlace();
                return true;
            }
            if (ch == 'l') {
                clearSearch();
                return true;
            }
        }

        // Global shortcuts
        if (type == KeyType.Enter) {
            runSelectedTarget();
            return true;
        }
        if (type == KeyType.F5) {
            compileSelectedInPlace();
            return true;
        }
        if (type == KeyType.F6) {
            rescanManifest();
            return true;
        }
        if (type == KeyType.F2) {
            switchTab(-1);
            return true;
        }
        if (type == KeyType.F3) {
            switchTab(1);
            return true;
        }
        if (type == KeyType.Tab) {
            cycleFocus(1);
            return true;
        }
        if (type == KeyType.ReverseTab) {
            cycleFocus(-1);
            return true;
        }

        // List navigation works from ANY focus (including right inside Search!)
        if (type == KeyType.ArrowUp) {
            moveSelection(-1);
            return true;
        }
        if (type == KeyType.ArrowDown) {
            moveSelection(1);
            return true;
        }
        if (type == KeyType.PageUp) {
            moveSelection(-10);
            return true;
        }
        if (type == KeyType.PageDown) {
            moveSelection(10);
            return true;
        }

        // Ctrl+Left/Right or Alt+Left/Right switches tabs from any focus
        if ((key.isCtrlDown() || key.isAltDown()) && type == KeyType.ArrowLeft) {
            switchTab(-1);
            return true;
        }
        if ((key.isCtrlDown() || key.isAltDown()) && type == KeyType.ArrowRight) {
            switchTab(1);
            return true;
        }

        // Escape: clear search/args first, or quit if already clean
        if (type == KeyType.Escape) {
            if (focusArea == FocusArea.ARGS && cliArgs.length() > 0) {
                cliArgs.setLength(0);
                argsCursor = 0;
                statusMessage = "CLI argümanları temizlendi.";
                return true;
            }
            if (searchQuery.length() > 0) {
                clearSearch();
                return true;
            }
            return false;
        }

        // Focus-specific handling
        return switch (focusArea) {
            case SEARCH -> handleSearchInput(key);
            case ARGS -> handleArgsInput(key);
            case LIST -> handleListInput(key);
        };
    }

    private boolean handleSearchInput(KeyStroke key) {
        KeyType type = key.getKeyType();
        switch (type) {
            case Character -> {
                char c = key.getCharacter();
                if (!Character.isISOControl(c)) {
                    searchQuery.insert(searchCursor, c);
                    searchCursor++;
                    recomputeFiltered();
                }
            }
            case Backspace -> {
                if (searchCursor > 0) {
                    searchQuery.deleteCharAt(searchCursor - 1);
                    searchCursor--;
                    recomputeFiltered();
                }
            }
            case Delete -> {
                if (searchCursor < searchQuery.length()) {
                    searchQuery.deleteCharAt(searchCursor);
                    recomputeFiltered();
                }
            }
            case ArrowLeft -> {
                if (searchQuery.length() == 0) {
                    switchTab(-1);
                } else if (searchCursor > 0) {
                    searchCursor--;
                }
            }
            case ArrowRight -> {
                if (searchQuery.length() == 0) {
                    switchTab(1);
                } else if (searchCursor < searchQuery.length()) {
                    searchCursor++;
                }
            }
            case Home -> searchCursor = 0;
            case End -> searchCursor = searchQuery.length();
            default -> {
            }
        }
        return true;
    }

    private boolean handleArgsInput(KeyStroke key) {
        KeyType type = key.getKeyType();
        switch (type) {
            case Character -> {
                char c = key.getCharacter();
                if (!Character.isISOControl(c)) {
                    cliArgs.insert(argsCursor, c);
                    argsCursor++;
                }
            }
            case Backspace -> {
                if (argsCursor > 0) {
                    cliArgs.deleteCharAt(argsCursor - 1);
                    argsCursor--;
                }
            }
            case Delete -> {
                if (argsCursor < cliArgs.length()) {
                    cliArgs.deleteCharAt(argsCursor);
                }
            }
            case ArrowLeft -> {
                if (argsCursor > 0) {
                    argsCursor--;
                }
            }
            case ArrowRight -> {
                if (argsCursor < cliArgs.length()) {
                    argsCursor++;
                }
            }
            case Home -> argsCursor = 0;
            case End -> argsCursor = cliArgs.length();
            default -> {
            }
        }
        return true;
    }

    private boolean handleListInput(KeyStroke key) {
        KeyType type = key.getKeyType();
        if (type == KeyType.ArrowLeft) {
            switchTab(-1);
            return true;
        }
        if (type == KeyType.ArrowRight) {
            switchTab(1);
            return true;
        }
        if (type == KeyType.Home) {
            selectedIndex = 0;
            scrollOffset = 0;
            return true;
        }
        if (type == KeyType.End && !filteredTargets.isEmpty()) {
            selectedIndex = filteredTargets.size() - 1;
            return true;
        }
        if (type == KeyType.Character) {
            char c = Character.toLowerCase(key.getCharacter());
            if (c == 'q') {
                return false;
            }
            if (c == 'r') {
                compileSelectedInPlace();
                return true;
            }
            if (c == 'j') {
                moveSelection(1);
                return true;
            }
            if (c == 'k') {
                moveSelection(-1);
                return true;
            }
            if (c == '/') {
                focusArea = FocusArea.SEARCH;
                return true;
            }
        }
        return true;
    }

    private void cycleFocus(int delta) {
        FocusArea[] areas = FocusArea.values();
        int next = (focusArea.ordinal() + delta + areas.length) % areas.length;
        focusArea = areas[next];
    }

    private void switchTab(int delta) {
        if (tabs.isEmpty()) {
            return;
        }
        activeTabIndex = (activeTabIndex + delta + tabs.size()) % tabs.size();
        recomputeFiltered();
        statusMessage = "Kategori: " + tabs.get(activeTabIndex);
    }

    private void clearSearch() {
        searchQuery.setLength(0);
        searchCursor = 0;
        recomputeFiltered();
        statusMessage = "Arama temizlendi.";
    }

    private void moveSelection(int delta) {
        if (filteredTargets.isEmpty()) {
            selectedIndex = 0;
            scrollOffset = 0;
            return;
        }
        selectedIndex = Math.max(0, Math.min(filteredTargets.size() - 1, selectedIndex + delta));
    }

    private void recomputeFiltered() {
        String activeTab = tabs.isEmpty() ? ALL_TAB : tabs.get(activeTabIndex);
        List<TargetEntry> scoped = new ArrayList<>();
        for (TargetEntry t : allTargets) {
            if (ALL_TAB.equals(activeTab) || t.getCategory().equals(activeTab)) {
                scoped.add(t);
            }
        }
        filteredTargets = FuzzyMatcher.filterAndRank(scoped, searchQuery.toString());
        if (filteredTargets.isEmpty()) {
            selectedIndex = 0;
            scrollOffset = 0;
        } else if (selectedIndex >= filteredTargets.size()) {
            selectedIndex = filteredTargets.size() - 1;
        }
    }

    private TargetEntry getSelectedTarget() {
        if (filteredTargets.isEmpty() || selectedIndex < 0 || selectedIndex >= filteredTargets.size()) {
            return null;
        }
        return filteredTargets.get(selectedIndex).target();
    }

    private void compileSelectedInPlace() {
        TargetEntry target = getSelectedTarget();
        if (target == null) {
            statusMessage = "Derlenecek hedef secili degil.";
            return;
        }
        statusMessage = "Derleniyor: " + target.getDisplayName() + " ...";
        try {
            render();
        } catch (IOException ignored) {
            // ignore render error
        }

        ProjectRunner.CompileResult res = runner.compile(target);
        String key = target.getUniqueKey();
        failedBuilds.put(key, !res.success());
        lastBuildLogs.put(key, res.output());

        if (res.success()) {
            statusMessage = String.format("Derleme basarili (%d ms): %s", res.durationMs(), target.getDisplayName());
        } else {
            statusMessage = String.format("Derleme hatasi (%d ms): %s (ayrintilar sag panelde)",
                    res.durationMs(), target.getDisplayName());
        }
    }

    private void rescanManifest() {
        try {
            ProjectScanner.ScanResult result = scanner.scanAndWriteManifest(false, null);
            loadData(result.categories(), result.targets());
            statusMessage = String.format("Tarama yenilendi: %d hedef, %d kategori.",
                    result.targets().size(), result.categories().size());
        } catch (IOException e) {
            statusMessage = "Tarama hatasi: " + e.getMessage();
        }
    }

    private void runSelectedTarget() throws IOException {
        TargetEntry target = getSelectedTarget();
        if (target == null) {
            statusMessage = "Calistirilacak hedef secili degil.";
            return;
        }

        List<String> parsedArgs = ProjectRunner.parseCliArgs(cliArgs.toString());

        // Fully stop and close screen/terminal so the child process gets a clean normal TTY
        closeScreen();

        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println(ANSI_CYAN + "[1/2] Derleniyor..." + ANSI_RESET);
            if (!parsedArgs.isEmpty()) {
                System.out.println(ANSI_GRAY + "Argümanlar: " + String.join(" ", parsedArgs) + ANSI_RESET);
            }
            System.out.flush();

            ProjectRunner.CompileResult compileResult = runner.compile(target);
            String key = target.getUniqueKey();
            failedBuilds.put(key, !compileResult.success());
            lastBuildLogs.put(key, compileResult.output());

            if (!compileResult.success()) {
                System.out.println();
                System.out.println(ANSI_RED + "[HATA] Derleme başarısız oldu:" + ANSI_RESET);
                System.out.println(compileResult.output());
                statusMessage = "Derleme hatasi: " + target.getDisplayName();
            } else {
                System.out.printf("%s[2/2] Çalıştırılıyor%s %s(%d ms içinde derlendi)%s%n",
                        ANSI_CYAN, ANSI_RESET, ANSI_GRAY, compileResult.durationMs(), ANSI_RESET);
                System.out.println(RUN_SEPARATOR);
                System.out.flush();

                try {
                    int exitCode = runner.runInteractive(target, compileResult.classpath(), parsedArgs);
                    System.out.println();
                    System.out.println(RUN_SEPARATOR);
                    String exitColor = (exitCode == 0) ? ANSI_GREEN : ANSI_RED;
                    System.out.printf("%sProgram sonlandı (Çıkış kodu: %d)%s%n", exitColor, exitCode, ANSI_RESET);
                    statusMessage = String.format("Son calistirma: %s (cikis kodu: %d)",
                            target.getDisplayName(), exitCode);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("\n" + ANSI_RED + "Program kesildi." + ANSI_RESET);
                    statusMessage = "Program kesildi: " + target.getDisplayName();
                } catch (IOException e) {
                    System.out.println("\n" + ANSI_RED + "[HATA] Çalıştırma başarısız: " + e.getMessage() + ANSI_RESET);
                    statusMessage = "Calistirma hatasi: " + e.getMessage();
                }
            }

            waitForEnter();
        } finally {
            openScreen();
        }
    }

    private void waitForEnter() {
        System.out.print("\n" + ANSI_GRAY + "Menüye dönmek için Enter'a basın..." + ANSI_RESET);
        System.out.flush();
        try {
            int ch;
            while ((ch = System.in.read()) != -1) {
                if (ch == '\n') {
                    break;
                }
            }
        } catch (IOException ignored) {
            // ignore
        }
    }

    private void render() throws IOException {
        TerminalSize size = screen.getTerminalSize();
        int width = Math.max(40, size.getColumns());
        int height = Math.max(14, size.getRows());

        screen.clear();
        TextGraphics tg = screen.newTextGraphics();

        // Fill background
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.WHITE);

        // Row 0: Header
        drawHeader(tg, width);

        // Row 1: Category Tabs
        drawTabs(tg, width);

        // Row 2: Separator
        drawHorizontalLine(tg, 0, 2, width, '─');

        // Row 3: Search bar
        int searchCursorCol = drawSearchBar(tg, width, 3);

        // Row 4: Separator with column split
        int splitCol = Math.max(26, (int) (width * 0.52));
        if (width - splitCol < 24) {
            splitCol = Math.max(20, width - 24);
        }
        drawHorizontalLine(tg, 0, 4, width, '─');
        tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
        tg.setCharacter(splitCol, 4, '┬');

        // Body rows: [5 .. height - 5]
        int bodyTop = 5;
        int bodyBottom = height - 5;
        int bodyHeight = Math.max(3, bodyBottom - bodyTop + 1);

        drawTargetList(tg, 0, bodyTop, splitCol, bodyHeight);
        drawVerticalLine(tg, splitCol, bodyTop, bodyHeight);
        drawPreviewPanel(tg, splitCol + 1, bodyTop, width - splitCol - 1, bodyHeight);

        // Row height - 4: Separator
        int argsSepRow = height - 4;
        drawHorizontalLine(tg, 0, argsSepRow, width, '─');
        tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
        tg.setCharacter(splitCol, argsSepRow, '┴');

        // Row height - 3: CLI Arguments input
        int argsRow = height - 3;
        int argsCursorCol = drawArgsBar(tg, width, argsRow);

        // Row height - 2: Status bar
        drawStatusBar(tg, width, height - 2);

        // Row height - 1: Keybinding hints
        drawHelpBar(tg, width, height - 1);

        // Cursor position
        if (focusArea == FocusArea.SEARCH) {
            screen.setCursorPosition(new TerminalPosition(Math.min(width - 1, searchCursorCol), 3));
        } else if (focusArea == FocusArea.ARGS) {
            screen.setCursorPosition(new TerminalPosition(Math.min(width - 1, argsCursorCol), argsRow));
        } else {
            screen.setCursorPosition(null);
        }

        screen.refresh();
    }

    private void drawHeader(TextGraphics tg, int width) {
        tg.setBackgroundColor(TextColor.ANSI.BLUE);
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        fillRow(tg, 0, width, ' ');
        String title = " Java Laboratory Launcher ";
        String right = String.format(" [%d hedef | %d kategori] ",
                allTargets.size(), Math.max(0, tabs.size() - 1));
        tg.putString(0, 0, truncate(title, width), SGR.BOLD);
        if (width > title.length() + right.length()) {
            tg.putString(width - right.length(), 0, right);
        }
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
    }

    private void drawTabs(TextGraphics tg, int width) {
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        int col = 1;
        for (int i = 0; i < tabs.size(); i++) {
            String tab = tabs.get(i);
            int count = countTargetsInTab(tab);
            String label = String.format(" %s (%d) ", tab, count);
            if (col + label.length() > width - 1) {
                if (col < width - 4) {
                    tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
                    tg.putString(col, 1, "...");
                }
                break;
            }
            if (i == activeTabIndex) {
                tg.setBackgroundColor(TextColor.ANSI.CYAN);
                tg.setForegroundColor(TextColor.ANSI.BLACK);
                tg.putString(col, 1, label, SGR.BOLD);
                tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
            } else {
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(col, 1, label);
            }
            col += label.length() + 1;
        }
    }

    private int drawSearchBar(TextGraphics tg, int width, int row) {
        boolean active = focusArea == FocusArea.SEARCH;
        String prefix = active ? " [Arama] > " : "  Arama  > ";
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(active ? TextColor.ANSI.CYAN_BRIGHT : TextColor.ANSI.WHITE);
        if (active) {
            tg.putString(0, row, prefix, SGR.BOLD);
        } else {
            tg.putString(0, row, prefix);
        }

        int inputStart = prefix.length();
        int available = Math.max(5, width - inputStart - 1);
        String text = searchQuery.toString();

        if (text.isEmpty() && !active) {
            tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
            tg.putString(inputStart, row, truncate("Türkçe uyumlu bulanık arama (ör: hafta 4 kutup, asal)...", available));
            return inputStart;
        }

        int viewStart = 0;
        if (searchCursor > available - 1) {
            viewStart = searchCursor - (available - 1);
        }
        String visible = text.substring(Math.min(viewStart, text.length()),
                Math.min(text.length(), viewStart + available));

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(inputStart, row, visible);

        if (text.isEmpty()) {
            tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
            tg.putString(inputStart + 1, row, truncate("ara: sınıf, klasör, kısaltma...", Math.max(0, available - 1)));
        }

        return inputStart + (searchCursor - viewStart);
    }

    private void drawTargetList(TextGraphics tg, int x, int y, int width, int height) {
        boolean active = focusArea == FocusArea.LIST;
        String header = String.format(" Hedefler (%d/%d)%s",
                filteredTargets.size(),
                countTargetsInTab(tabs.isEmpty() ? ALL_TAB : tabs.get(activeTabIndex)),
                active ? " [ODAK]" : "");

        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(active ? TextColor.ANSI.CYAN_BRIGHT : TextColor.ANSI.YELLOW);
        tg.putString(x, y, truncate(header, width), SGR.BOLD);

        int listRows = Math.max(1, height - 1);
        if (selectedIndex < scrollOffset) {
            scrollOffset = selectedIndex;
        } else if (selectedIndex >= scrollOffset + listRows) {
            scrollOffset = selectedIndex - listRows + 1;
        }
        if (scrollOffset < 0) {
            scrollOffset = 0;
        }

        if (filteredTargets.isEmpty()) {
            tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
            tg.putString(x + 1, y + 1, truncate("Eslesen hedef bulunamadi.", Math.max(0, width - 2)));
            return;
        }

        for (int rowIdx = 0; rowIdx < listRows; rowIdx++) {
            int itemIdx = scrollOffset + rowIdx;
            int screenRow = y + 1 + rowIdx;
            if (itemIdx >= filteredTargets.size()) {
                break;
            }
            TargetEntry target = filteredTargets.get(itemIdx).target();
            boolean selected = (itemIdx == selectedIndex);
            boolean failed = Boolean.TRUE.equals(failedBuilds.get(target.getUniqueKey()));
            ProjectRunner.BuildStatus status = runner.inspectBuildStatus(target, failed);

            String tag = target.isMaven() ? "[MVN]" : status.getBadge();
            String prefix = selected ? " > " : "   ";
            String line = prefix + tag + " " + target.getDisplayName();
            String fitted = padRight(truncate(line, width), width);

            if (selected) {
                tg.setBackgroundColor(active ? TextColor.ANSI.CYAN : TextColor.ANSI.BLUE);
                tg.setForegroundColor(active ? TextColor.ANSI.BLACK : TextColor.ANSI.WHITE_BRIGHT);
                tg.putString(x, screenRow, fitted, SGR.BOLD);
                tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
            } else {
                tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(x, screenRow, fitted);
                // Recolor status badge
                tg.setForegroundColor(statusColor(status, target.isMaven()));
                if (prefix.length() + tag.length() <= width) {
                    tg.putString(x + prefix.length(), screenRow, tag);
                }
            }
        }
    }

    private void drawPreviewPanel(TextGraphics tg, int x, int y, int width, int height) {
        if (width < 10) {
            return;
        }
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(x + 1, y, truncate("Önizleme", width - 2), SGR.BOLD);

        TargetEntry target = getSelectedTarget();
        if (target == null) {
            tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
            tg.putString(x + 1, y + 2, truncate("Seçili hedef yok.", width - 2));
            return;
        }

        boolean failed = Boolean.TRUE.equals(failedBuilds.get(target.getUniqueKey()));
        ProjectRunner.BuildStatus status = runner.inspectBuildStatus(target, failed);

        int row = y + 1;
        int maxRow = y + height - 1;
        int innerWidth = Math.max(6, width - 2);

        row = drawMetaRow(tg, x + 1, row, innerWidth, maxRow, "Ad       : ", target.getDisplayName(), TextColor.ANSI.WHITE_BRIGHT);
        row = drawMetaRow(tg, x + 1, row, innerWidth, maxRow, "Kategori : ", target.getCategory(), TextColor.ANSI.WHITE);
        row = drawMetaRow(tg, x + 1, row, innerWidth, maxRow, "Ana Sınıf: ", target.getMainClass(), TextColor.ANSI.CYAN_BRIGHT);
        row = drawMetaRow(tg, x + 1, row, innerWidth, maxRow, "Derleme  : ",
                target.isMaven() ? "Maven (pom.xml)" : "Basit (javac -> bin/.launcher/)", TextColor.ANSI.WHITE);
        row = drawMetaRow(tg, x + 1, row, innerWidth, maxRow, "Durum    : ",
                status.getBadge() + " " + status.getLabel(), statusColor(status, false));
        row = drawMetaRow(tg, x + 1, row, innerWidth, maxRow, "Dizin    : ", target.getWorkingDir(), TextColor.ANSI.WHITE);

        row++;
        if (row <= maxRow) {
            tg.setForegroundColor(TextColor.ANSI.YELLOW);
            tg.putString(x + 1, row++, truncate("Klasördeki Dosyalar:", innerWidth), SGR.BOLD);

            List<ProjectRunner.DirectoryItem> items = runner.listWorkingDirItems(target);
            String buildLog = lastBuildLogs.get(target.getUniqueKey());
            int reserveForLog = (buildLog != null && !buildLog.isBlank()) ? 5 : 0;
            int filesLimit = Math.max(2, maxRow - row - reserveForLog);

            if (items.isEmpty()) {
                if (row <= maxRow) {
                    tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
                    tg.putString(x + 2, row++, truncate("(dosya yok)", innerWidth - 1));
                }
            } else {
                int shown = 0;
                for (ProjectRunner.DirectoryItem item : items) {
                    if (row > maxRow || shown >= filesLimit) {
                        if (row <= maxRow && shown < items.size()) {
                            tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
                            tg.putString(x + 2, row++,
                                    truncate("... +" + (items.size() - shown) + " öğe daha", innerWidth - 1));
                        }
                        break;
                    }
                    String text = item.name() + " (" + item.detail() + ")";
                    if (item.directory()) {
                        tg.setForegroundColor(TextColor.ANSI.BLUE_BRIGHT);
                    } else if (isDataFile(item.name())) {
                        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                    } else {
                        tg.setForegroundColor(TextColor.ANSI.WHITE);
                    }
                    tg.putString(x + 2, row++, truncate(text, innerWidth - 1));
                    shown++;
                }
            }

            if (buildLog != null && !buildLog.isBlank() && row + 1 <= maxRow) {
                row++;
                tg.setForegroundColor(failed ? TextColor.ANSI.RED_BRIGHT : TextColor.ANSI.GREEN);
                tg.putString(x + 1, row++, truncate("Son Derleme Çıktısı:", innerWidth), SGR.BOLD);
                tg.setForegroundColor(failed ? TextColor.ANSI.RED : TextColor.ANSI.WHITE);
                for (String line : buildLog.split("\\R")) {
                    if (row > maxRow) {
                        break;
                    }
                    tg.putString(x + 2, row++, truncate(line, innerWidth - 1));
                }
            }
        }
    }

    private int drawMetaRow(TextGraphics tg, int x, int row, int maxWidth, int maxRow,
                            String label, String value, TextColor valueColor) {
        if (row > maxRow) {
            return row;
        }
        tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
        tg.putString(x, row, truncate(label, maxWidth));
        int valOffset = label.length();
        if (valOffset < maxWidth) {
            tg.setForegroundColor(valueColor);
            tg.putString(x + valOffset, row, truncate(value, maxWidth - valOffset));
        }
        return row + 1;
    }

    private int drawArgsBar(TextGraphics tg, int width, int row) {
        boolean active = focusArea == FocusArea.ARGS;
        String prefix = active ? " [CLI Arg] > " : "  CLI Arg  > ";
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(active ? TextColor.ANSI.CYAN_BRIGHT : TextColor.ANSI.WHITE);
        if (active) {
            tg.putString(0, row, prefix, SGR.BOLD);
        } else {
            tg.putString(0, row, prefix);
        }

        int inputStart = prefix.length();
        int available = Math.max(5, width - inputStart - 1);
        String text = cliArgs.toString();

        if (text.isEmpty() && !active) {
            tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
            tg.putString(inputStart, row, truncate("(opsiyonel parametreler - geçmek için Tab)", available));
            return inputStart;
        }

        int viewStart = 0;
        if (argsCursor > available - 1) {
            viewStart = argsCursor - (available - 1);
        }
        String visible = text.substring(Math.min(viewStart, text.length()),
                Math.min(text.length(), viewStart + available));

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(inputStart, row, visible);
        return inputStart + (argsCursor - viewStart);
    }

    private void drawStatusBar(TextGraphics tg, int width, int row) {
        tg.setBackgroundColor(TextColor.ANSI.BLACK);
        tg.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        fillRow(tg, row, width, ' ');
        tg.putString(1, row, truncate(statusMessage, Math.max(0, width - 2)));
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
    }

    private void drawHelpBar(TextGraphics tg, int width, int row) {
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
        String help = " Enter: Derle & Çalıştır | F5/r: Derle | Tab: Alan | ←/→ / F2-F3: Sekme | F6: Tara | Esc/q: Temizle/Çık";
        tg.putString(0, row, truncate(help, width));
    }

    private int countTargetsInTab(String tab) {
        if (ALL_TAB.equals(tab)) {
            return allTargets.size();
        }
        int count = 0;
        for (TargetEntry t : allTargets) {
            if (t.getCategory().equals(tab)) {
                count++;
            }
        }
        return count;
    }

    private static TextColor statusColor(ProjectRunner.BuildStatus status, boolean mavenBadge) {
        if (mavenBadge) {
            return TextColor.ANSI.MAGENTA_BRIGHT;
        }
        return switch (status) {
            case UP_TO_DATE -> TextColor.ANSI.GREEN_BRIGHT;
            case STALE -> TextColor.ANSI.YELLOW_BRIGHT;
            case NOT_BUILT -> TextColor.ANSI.BLACK_BRIGHT;
            case FAILED -> TextColor.ANSI.RED_BRIGHT;
        };
    }

    private static boolean isDataFile(String name) {
        String lower = name.toLowerCase();
        return lower.endsWith(".txt")
                || lower.endsWith(".json")
                || lower.endsWith(".csv")
                || lower.endsWith(".xml")
                || lower.endsWith(".yaml")
                || lower.endsWith(".yml")
                || lower.endsWith(".properties");
    }

    private static void drawHorizontalLine(TextGraphics tg, int x, int y, int width, char ch) {
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
        tg.putString(x, y, String.valueOf(ch).repeat(Math.max(0, width)));
    }

    private static void drawVerticalLine(TextGraphics tg, int x, int y, int height) {
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.BLACK_BRIGHT);
        for (int i = 0; i < height; i++) {
            tg.setCharacter(x, y + i, '│');
        }
    }

    private static void fillRow(TextGraphics tg, int row, int width, char ch) {
        tg.putString(0, row, String.valueOf(ch).repeat(Math.max(0, width)));
    }

    private static String truncate(String s, int maxLen) {
        if (s == null || maxLen <= 0) {
            return "";
        }
        if (s.length() <= maxLen) {
            return s;
        }
        if (maxLen <= 3) {
            return s.substring(0, maxLen);
        }
        return s.substring(0, maxLen - 3) + "...";
    }

    private static String padRight(String s, int width) {
        if (s.length() >= width) {
            return s;
        }
        return s + " ".repeat(width - s.length());
    }
}
