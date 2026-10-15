/*
 * Nesne Yonelimli Programlama - Hafta 03
 * Ornek 2: Dort ilkenin ortak sahnesi - Sekil ust sinifi
 *
 * Bu tek sinif dort ilkenin ucunu ayni anda tasir:
 *   KAPSULLEME  : ad ve renk alanlari private; renk yalnizca setter'dan degisir
 *   SOYUTLAMA   : alan() ve cevre() "ne" yapildigini soyler, "nasil"i soylemez
 *   MIRAS       : ozet() ve renk yonetimi tum alt siniflara oldugu gibi gecer
 * Dorduncusu (COK BICIMLILIK) SekilDemo.java icinde gorunur hale gelir.
 */
public abstract class Sekil {

    private final String ad;    // dogumda belirlenir, bir daha degismez
    private String renk;

    protected Sekil(String ad, String renk) {
        this.ad = ad;
        this.renk = gecerliRenk(renk);
    }

    private static String gecerliRenk(String r) {
        return (r == null || r.isBlank()) ? "belirsiz" : r;
    }

    public String getAd() {
        return ad;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        if (renk == null || renk.isBlank()) {
            System.out.println("Uyari: renk bos birakilamaz; degisiklik yapilmadi.");
            return;
        }
        this.renk = renk;
    }

    // SOYUTLAMA: sozlesme burada, gerceklemesi alt siniflarda.
    public abstract double alan();

    public abstract double cevre();

    // MIRAS: asagidaki iki metot hicbir alt sinifta tekrar yazilmaz.
    public String ozet() {
        return String.format("%-11s renk=%-9s alan=%9.3f  cevre=%9.3f",
                             ad, renk, alan(), cevre());
    }

    @Override
    public String toString() {
        return ozet();
    }
}
