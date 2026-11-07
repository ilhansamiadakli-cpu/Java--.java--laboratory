public class Daktilo {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    public static void Daktilo(String metin, int ms, String renk) {
    if (renk != null) {
        System.out.print(renk);
    }

    for (int i = 0; i < metin.length();) {
        int codePoint = metin.codePointAt(i);

        System.out.print(Character.toChars(codePoint));
        System.out.flush();

        i += Character.charCount(codePoint);

        if (ms > 0) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    System.out.print(RESET);
    System.out.flush();
}
public static void Sleep(float ms) {
    if (ms <= 0) return;

    try {
        long nanos = (long) (ms * 1_000_000);
        Thread.sleep(nanos / 1_000_000, (int) (nanos % 1_000_000));
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
public static void main(String[] args) {

    Daktilo("Merhaba Dünya!", 50, GREEN);
    Sleep(1000);
    Daktilo("\nC ve Java dünyasına hoş geldin.", 30, CYAN);
    Sleep(1000);
    Daktilo("\nBu bir hata mesajıdır!", 20, RED);
}
}
