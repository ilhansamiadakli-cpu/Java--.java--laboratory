/*
 * Nesne Yonelimli Programlama - Hafta 02
 * Ornek 6: 'this' anahtar sozcugu ve alan golgeleme (shadowing)
 *
 * Parametre adi ile alan adi ayni oldugunda, metot icinde parametre
 * alani GOLGELER. Alana ulasmak icin 'this.' on eki gereklidir.
 */
public class GolgelemeOrnegi {

    private String ad;
    private int yas;

    /** YANLIS yaklasim: this yok, atama kendine yapiliyor. */
    public void yanlisAyarla(String ad, int yas) {
        ad = ad;        // parametre kendine atanir; alan degismez!
        yas = yas;      // ayni sorun
    }

    /** DOGRU yaklasim: this ile alan acikca belirtiliyor. */
    public void dogruAyarla(String ad, int yas) {
        this.ad = ad;
        this.yas = yas;
    }

    /** Alternatif: parametre adini farklilastirmak. */
    public void farkliAdlaAyarla(String yeniAd, int yeniYas) {
        ad = yeniAd;    // golgeleme yok, this gerekmez
        yas = yeniYas;
    }

    /** this, nesnenin kendisini dondurebilir (zincirleme kullanim). */
    public GolgelemeOrnegi yasiArtir() {
        this.yas++;
        return this;
    }

    @Override
    public String toString() {
        return "ad=" + ad + ", yas=" + yas;
    }

    public static void main(String[] args) {
        GolgelemeOrnegi k = new GolgelemeOrnegi();

        k.yanlisAyarla("Ali", 20);
        System.out.println("yanlisAyarla sonrasi : " + k);

        k.dogruAyarla("Ali", 20);
        System.out.println("dogruAyarla sonrasi  : " + k);

        k.farkliAdlaAyarla("Ayse", 22);
        System.out.println("farkliAdla sonrasi   : " + k);

        // this dondurmenin sagladigi zincirleme cagri
        k.yasiArtir().yasiArtir().yasiArtir();
        System.out.println("uc kez yasiArtir()   : " + k);
    }
}
