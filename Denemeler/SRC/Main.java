
public class Main {

    static final String RESET  = "\u001B[0m";
    static final String CYAN   = "\u001B[36m";
    static final String YELLOW = "\u001B[33m";
    static final String WHITE  = "\u001B[37m";
    static final String DIM    = "\u001B[90m";

    static void Sleep(float ms) {
        if (ms <= 0) return;

        try {
            long nanos = (long)(ms * 1_000_000);
            Thread.sleep(nanos / 1_000_000,
                         (int)(nanos % 1_000_000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static void Daktilo(String text, int ms, String color) {
        System.out.print(color);

        for (int i = 0; i < text.length();) {
            int cp = text.codePointAt(i);
            System.out.print(Character.toChars(cp));
            System.out.flush();

            i += Character.charCount(cp);
            Sleep(ms);
        }

        System.out.print(RESET);
    }

    static void drawFrame(int position, int frame) {

        final int WIDTH = 65;
        final int HEIGHT = 19;

        char[][] screen = new char[HEIGHT][WIDTH];
        int[][] colors = new int[HEIGHT][WIDTH];

        // Background
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                screen[y][x] = ' ';
            }
        }

        // Stars
        int[] starX = {
            3, 12, 20, 29, 37, 45, 54, 61,
            8, 17, 25, 34, 42, 50, 58, 63,
            5, 15, 23, 32, 40, 48, 56, 60
        };

        int[] starY = {
            2, 5, 1, 8, 3, 6, 2, 10,
            12, 15, 11, 17, 13, 9, 16, 4,
            7, 14, 4, 10, 1, 15, 8, 13
        };

        for (int i = 0; i < starX.length; i++) {
            screen[starY[i]][starX[i]] =
                (frame % 2 == 0) ? '.' : '*';
        }

        // Spaceship
        String[] ship = {
            "   /\\",
            "__/==\\________",
            "<|  VOID ENGINE ===>",
            "  \\____________/"
        };

        for (int y = 0; y < ship.length; y++) {
            for (int x = 0; x < ship[y].length(); x++) {

                int sx = position + x;
                int sy = 7 + y;

                if (sx >= 0 && sx < WIDTH) {
                    screen[sy][sx] = ship[y].charAt(x);
                    colors[sy][sx] = 1;
                }
            }
        }

        // Engine flames
        String flame = (frame % 2 == 0) ? "<~~" : "<~~~";

        for (int i = 0; i < flame.length(); i++) {
            int sx = position - flame.length() + i;

            if (sx >= 0 && sx < WIDTH) {
                screen[9][sx] = flame.charAt(i);
                colors[9][sx] = 2;
            }
        }

        // Render
        StringBuilder output = new StringBuilder();

        output.append("\u001B[H");
        output.append(CYAN)
              .append("           V O I D   E N G I N E")
              .append(RESET)
              .append("\n\n");

        for (int y = 0; y < HEIGHT; y++) {

            for (int x = 0; x < WIDTH; x++) {

                char c = screen[y][x];

                if (colors[y][x] == 1) {
                    output.append(CYAN);
                } else if (colors[y][x] == 2) {
                    output.append(YELLOW);
                } else if (c != ' ') {
                    output.append(DIM);
                }

                output.append(c);

                if (colors[y][x] != 0 || c != ' ') {
                    output.append(RESET);
                }
            }

            output.append('\n');
        }

        output.append("\n")
              .append(DIM)
              .append("       Exploring the unknown...")
              .append(RESET);

        System.out.print(output);
        System.out.flush();
    }

    public static void main(String[] args) {

        // Clear terminal and hide cursor
        System.out.print("\u001B[2J\u001B[?25l");

        int width = 65;

        for (int cycle = 0; cycle < 3; cycle++) {

            for (int x = -18; x < width; x++) {
                drawFrame(x, x);
                Sleep(75);
            }
        }

        // Restore cursor
        System.out.print("\u001B[0m\u001B[?25h\n");

        Daktilo(
            "MISSION COMPLETE. WELCOME TO THE VOID.",
            40,
            GREEN
        );
    }

    static final String GREEN = "\u001B[32m";
}
