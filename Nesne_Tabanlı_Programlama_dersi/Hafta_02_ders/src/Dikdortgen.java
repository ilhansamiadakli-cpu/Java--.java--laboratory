/*
 * Nesne Yonelimli Programlama - Hafta 02
 * Ornek 9: Erisim belirleyicilerine giris (public / private)
 *
 * Alanlar private oldugu icin gecersiz duruma dusmek imkansizdir.
 * Kural sinifin ICINDE, tek yerde tanimlidir.
 */
public class Dikdortgen {

    private double genislik;
    private double yukseklik;

    public Dikdortgen(double genislik, double yukseklik) {
        this.genislik = pozitifYap(genislik);
        this.yukseklik = pozitifYap(yukseklik);
    }

    /** Kare olusturmak icin kisa yapici. */
    public Dikdortgen(double kenar) {
        this(kenar, kenar);
    }

    private static double pozitifYap(double deger) {
        return (deger > 0) ? deger : 1.0;
    }

    // --- Okuma erisimi (getter) ---
    public double getGenislik() {
        return genislik;
    }

    public double getYukseklik() {
        return yukseklik;
    }

    // --- Denetimli yazma erisimi (setter) ---
    public void setGenislik(double genislik) {
        if (genislik <= 0) {
            System.out.println("Uyari: genislik pozitif olmalidir; degisiklik yapilmadi.");
            return;
        }
        this.genislik = genislik;
    }

    public void setYukseklik(double yukseklik) {
        if (yukseklik <= 0) {
            System.out.println("Uyari: yukseklik pozitif olmalidir; degisiklik yapilmadi.");
            return;
        }
        this.yukseklik = yukseklik;
    }

    public double alan() {
        return genislik * yukseklik;
    }

    public double cevre() {
        return 2 * (genislik + yukseklik);
    }

    public boolean karedirMi() {
        return Math.abs(genislik - yukseklik) < 1e-9;
    }

    @Override
    public String toString() {
        return String.format("%.1f x %.1f | alan=%.2f | cevre=%.2f | %s",
                genislik, yukseklik, alan(), cevre(),
                karedirMi() ? "kare" : "dikdortgen");
    }

    public static void main(String[] args) {
        Dikdortgen d1 = new Dikdortgen(4, 3);
        Dikdortgen kare = new Dikdortgen(5);
        System.out.println("d1   = " + d1);
        System.out.println("kare = " + kare);

        System.out.println("\n--- Gecersiz deger denemeleri ---");
        Dikdortgen d2 = new Dikdortgen(-10, 0);   // yapici duzeltir
        System.out.println("new Dikdortgen(-10, 0) -> " + d2);

        d1.setGenislik(-5);                        // setter reddeder
        System.out.println("d1 (setGenislik(-5) sonrasi) = " + d1);

        // d1.genislik = -5;   <-- DERLENMEZ: genislik private'tir
    }
}
