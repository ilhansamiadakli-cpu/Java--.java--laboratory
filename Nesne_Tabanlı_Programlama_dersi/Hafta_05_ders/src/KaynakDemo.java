/*
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 05 - Yapici ve Yikici Yontemler
 * Ders ornegi 7
 *
 * Amac: Ayni isi uc farkli bicimde yapip kaynak kapatma guvencesini
 *       karsilastirmak:
 *         (a) elle kapatma          -> hata olursa KAPANMAZ (sizinti)
 *         (b) try-finally           -> kapanir ama kod uzun
 *         (c) try-with-resources    -> kisa ve garantili
 */
public class KaynakDemo {

    public static void main(String[] args) {
        System.out.println("=== (a) ELLE KAPATMA - hata olursa sizinti ===");
        elleKapat();

        System.out.println();
        System.out.println("=== (b) TRY-FINALLY - kapanir, kod uzun ===");
        tryFinally();

        System.out.println();
        System.out.println("=== (c) TRY-WITH-RESOURCES - kisa ve garantili ===");
        tryWithResources();

        System.out.println();
        System.out.println("=== (d) BIRDEN COK KAYNAK: kapanma sirasi TERSTIR ===");
        cokluKaynak();
    }

    private static void elleKapat() {
        DosyaKaynagi d = new DosyaKaynagi("rapor_a.txt");
        try {
            d.yaz("satir 1");
            if (d.acikMi()) {
                throw new RuntimeException("beklenmedik hata");
            }
            d.close();                       // buraya HIC ULASILAMAZ
        } catch (RuntimeException e) {
            System.out.println("    HATA    : " + e.getMessage());
        }
        System.out.println("    Durum   : " + d);
        System.out.println("    Sonuc   : dosya ACIK kaldi -> KAYNAK SIZINTISI");
    }

    private static void tryFinally() {
        DosyaKaynagi d = new DosyaKaynagi("rapor_b.txt");
        try {
            d.yaz("satir 1");
            throw new RuntimeException("beklenmedik hata");
        } catch (RuntimeException e) {
            System.out.println("    HATA    : " + e.getMessage());
        } finally {
            d.close();
        }
        System.out.println("    Durum   : " + d);
        System.out.println("    Sonuc   : kapandi, ama 5 satir fazladan kod yazildi");
    }

    private static void tryWithResources() {
        DosyaKaynagi disKopya = null;
        try (DosyaKaynagi d = new DosyaKaynagi("rapor_c.txt")) {
            disKopya = d;
            d.yaz("satir 1");
            throw new RuntimeException("beklenmedik hata");
        } catch (RuntimeException e) {
            System.out.println("    HATA    : " + e.getMessage());
        }
        System.out.println("    Durum   : " + disKopya);
        System.out.println("    Sonuc   : close() catch'ten ONCE otomatik cagrildi");
    }

    private static void cokluKaynak() {
        try (DosyaKaynagi giris = new DosyaKaynagi("giris.txt");
             DosyaKaynagi cikis = new DosyaKaynagi("cikis.txt")) {
            giris.yaz("okundu");
            cikis.yaz("yazildi");
        }
        System.out.println("    Sonuc   : acilis giris->cikis, kapanis cikis->giris");
    }
}
