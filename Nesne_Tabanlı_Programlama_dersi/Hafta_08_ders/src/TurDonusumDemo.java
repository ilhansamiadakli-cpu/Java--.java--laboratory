/*
 * Nesne Yonelimli Programlama - Hafta 09
 * Ders Ornegi 5: Yukari donusum (upcast), asagi donusum (downcast),
 *                instanceof ve desen eslemeli instanceof.
 *
 * Amac: Upcast'in neden GUVENLI ve ortuk, downcast'in neden TEHLIKELI ve
 *       acik oldugunu; ClassCastException'in ne zaman firladigini gostermek.
 */
public class TurDonusumDemo {

    public static void main(String[] args) {
        System.out.println("== 1. Yukari donusum (upcast) - ortuk ve guvenli ==");
        Daire d = new Daire(2.0);
        Sekil s = d;                        // cast yazmaya gerek yok
        System.out.printf("  Daire -> Sekil : alan = %.4f%n", s.alan());
        System.out.println("  Her Daire bir Sekil'dir; derleyici bunu bilir.");

        System.out.println();
        System.out.println("== 2. Asagi donusum (downcast) - acik ve riskli ==");
        Sekil s2 = new Kare(3.0);
        Kare k = (Kare) s2;                 // cast ZORUNLU
        System.out.printf("  Sekil -> Kare  : cevre = %.2f%n", k.cevre());

        System.out.println();
        System.out.println("== 3. Yanlis downcast: ClassCastException ==");
        Sekil s3 = new Daire(1.0);
        try {
            Kare hatali = (Kare) s3;        // derlenir ama CALISMAZ
            System.out.println("  bu satira asla ulasilmaz " + hatali);
        } catch (ClassCastException e) {
            System.out.println("  Yakalanan istisna: " + e.getClass().getSimpleName());
            System.out.println("  Derleyici izin verdi, JVM engelledi.");
        }

        System.out.println();
        System.out.println("== 4. instanceof ile guvenli downcast ==");
        Sekil[] sekiller = { new Daire(1.0), new Kare(2.0), new Ucgen(3, 4, 5) };
        for (Sekil x : sekiller) {
            if (x instanceof Kare) {
                Kare kk = (Kare) x;         // klasik bicim
                System.out.printf("  Kare bulundu, cevre = %.2f%n", kk.cevre());
            } else {
                System.out.println("  Kare degil: " + x.getAd());
            }
        }

        System.out.println();
        System.out.println("== 5. Desen eslemeli instanceof (Java 16+) ==");
        for (Sekil x : sekiller) {
            // Tek satirda: tur denetimi + donusum + degisken bildirimi
            if (x instanceof Daire dd) {
                System.out.printf("  Daire  -> alan  = %.4f%n", dd.alan());
            } else if (x instanceof Kare kk) {
                System.out.printf("  Kare   -> cevre = %.2f%n", kk.cevre());
            } else {
                System.out.println("  Diger  -> " + x.getAd());
            }
        }

        System.out.println();
        System.out.println("== 6. instanceof ile getClass() farki ==");
        Sekil bos = new Sekil("Bos");
        Sekil daire = new Daire(1.0);
        System.out.println("  daire instanceof Sekil        : " + (daire instanceof Sekil));
        System.out.println("  daire.getClass() == Sekil     : "
                           + (daire.getClass() == Sekil.class));
        System.out.println("  bos.getClass()   == Sekil     : "
                           + (bos.getClass() == Sekil.class));
        System.out.println("  instanceof ALT SINIFLARI da kabul eder; getClass() etmez.");
    }
}
