/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 3 : Ikinci alt sinif - ayni ust siniftan tureme
 *
 * Amac : Bir ust siniftan birden cok alt sinif turetilebildigini,
 *        her alt sinifin ortak paydayi PAYLASIRKEN kendi durumunu
 *        ekleyebildigini gostermek.
 *
 * Not : Bu sinif "final" degildir; Yonetici sinifi bundan turetilir.
 *       Boylece iki seviyeli (Kisi -> Personel -> Yonetici) bir
 *       hiyerarsi elde edilir.
 * ========================================================== */

public class Personel extends Kisi {

    private final String sicilNo;
    private String unvan;
    private double aylikBrut;

    public Personel(String ad, String tcNo, int dogumYili,
                    String sicilNo, String unvan, double aylikBrut) {
        super(ad, tcNo, dogumYili);
        System.out.println("    [Personel yapicisi calisti]");
        if (aylikBrut <= 0) {
            throw new IllegalArgumentException("Brut ucret pozitif olmalidir.");
        }
        this.sicilNo   = sicilNo;
        this.unvan     = unvan;
        this.aylikBrut = aylikBrut;
    }

    public String getSicilNo()   { return sicilNo; }
    public String getUnvan()     { return unvan; }
    public double getAylikBrut() { return aylikBrut; }

    public void unvanDegistir(String yeni) {
        if (yeni == null || yeni.isBlank()) {
            System.out.println("  UYARI: unvan bos olamaz.");
            return;
        }
        this.unvan = yeni;
    }

    /** Alt siniflar bu metodu ezip kendi eklerini katacak. */
    public double aylikNet() {
        return aylikBrut * 0.85;      // basitlestirilmis kesinti
    }

    public int kidemYili() {
        return Math.max(0, yas() - 24);   // ust siniftan MIRAS ALINAN yas()
    }

    @Override
    public String kimlikKarti() {
        return super.kimlikKarti() + " | PERSONEL " + sicilNo + " (" + unvan + ")";
    }

    @Override
    public String toString() {
        return "Personel{" + getAd() + ", " + sicilNo + ", " + unvan + "}";
    }
}
