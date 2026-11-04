/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 2 : ALT SINIF (subclass) - extends ile turetme
 *
 * Amac : "Ogrenci BIR Kisidir" (IS-A) iliskisini kodda gostermek;
 *        super(...) ile ust sinif yapicisini cagirmak, ust siniftan
 *        gelen metodu ezmek (override) ve super.metot() ile ezilen
 *        surumu yeniden kullanmak.
 * ========================================================== */

public class Ogrenci extends Kisi {

    // Ogrenci'ye OZGU durum: Kisi'de bunlarin karsiligi yoktur.
    private final String ogrenciNo;
    private final String bolum;
    private double gno;

    private static int uretilen = 0;   // Hafta 6'dan: statik sayac

    public Ogrenci(String ad, String tcNo, int dogumYili,
                   String ogrenciNo, String bolum) {
        // super(...) ILK satir olmak ZORUNDADIR.
        super(ad, tcNo, dogumYili);
        System.out.println("    [Ogrenci yapicisi calisti]");
        this.ogrenciNo = ogrenciNo;
        this.bolum     = bolum;
        this.gno       = 0.0;
        uretilen++;
    }

    public String getOgrenciNo() { return ogrenciNo; }
    public String getBolum()     { return bolum; }
    public double getGno()       { return gno; }

    public void gnoGuncelle(double yeni) {
        if (yeni < 0.0 || yeni > 4.0) {
            System.out.println("  UYARI: GNO 0.0-4.0 araliginda olmalidir.");
            return;
        }
        this.gno = yeni;
    }

    public boolean basariliMi() {
        return gno >= 2.0;
    }

    /* Ust siniftan gelen kimlikKarti() metodunu EZIYORUZ.
     * super.kimlikKarti() ile ust surumu cagirip UZERINE ekliyoruz;
     * boylece ortak kismi yeniden yazmiyoruz. */
    @Override
    public String kimlikKarti() {
        return super.kimlikKarti() + " | OGRENCI " + ogrenciNo + " (" + bolum + ")";
    }

    @Override
    public String toString() {
        return "Ogrenci{" + getAd() + ", " + ogrenciNo + ", GNO=" + gno + "}";
    }

    public static int uretilen() { return uretilen; }
}
