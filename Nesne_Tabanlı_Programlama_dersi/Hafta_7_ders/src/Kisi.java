/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 1 : UST SINIF (superclass)
 *
 * Amac : Hafta 2-6 boyunca Ogrenci ve Personel siniflarini
 *        birbirinden bagimsiz yazdik. Ikisinde de ad, tcNo ve
 *        dogumYili alanlari ile yas() metodu birebir tekrarlaniyordu.
 *        Bu sinif o ortak paydayi TEK yere toplar.
 *
 * Dikkat : Alanlar "protected" degil "private"; alt siniflar
 *          bunlara getter uzerinden erisir. protected'in ne zaman
 *          gerektigini Bolum 4.4'te ayri ele aliyoruz.
 * ========================================================== */

public class Kisi {

    // Ortak durum: her kiside vardir, turu ne olursa olsun.
    private final String ad;
    private final String tcNo;
    private final int    dogumYili;

    public static final int SIMDIKI_YIL = 2026;

    /* Ust sinifin yapicisi. Alt siniflar bu yapiciyi super(...) ile
     * cagirmak ZORUNDADIR; cunku parametresiz bir yapici yoktur. */
    public Kisi(String ad, String tcNo, int dogumYili) {
        System.out.println("    [Kisi yapicisi calisti]");
        if (ad == null || ad.isBlank()) {
            throw new IllegalArgumentException("Ad bos olamaz.");
        }
        if (tcNo == null || tcNo.length() != 11) {
            throw new IllegalArgumentException("TC No 11 haneli olmalidir.");
        }
        if (dogumYili < 1900 || dogumYili > SIMDIKI_YIL) {
            throw new IllegalArgumentException("Dogum yili gecersiz: " + dogumYili);
        }
        this.ad        = ad;
        this.tcNo      = tcNo;
        this.dogumYili = dogumYili;
    }

    // ---- Ortak davranis: alt siniflar bunlari MIRAS ALIR ----
    public String getAd()      { return ad; }
    public int    getDogumYili() { return dogumYili; }

    /** TC numarasinin yalnizca son iki hanesi disariya verilir. */
    public String maskeliTcNo() {
        return "*********" + tcNo.substring(9);
    }

    public int yas() {
        return SIMDIKI_YIL - dogumYili;
    }

    public boolean resitMi() {
        return yas() >= 18;
    }

    /** Alt siniflar bu metodu EZEREK (override) genisletecek. */
    public String kimlikKarti() {
        return ad + " | " + maskeliTcNo() + " | " + yas() + " yasinda";
    }

    @Override
    public String toString() {
        return "Kisi{" + ad + ", " + yas() + "}";
    }
}
