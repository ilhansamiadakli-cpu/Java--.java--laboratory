/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 5 : Hiyerarsinin calistirilmasi
 *
 * Amac : (a) Alt sinif nesnesinin ust siniftan gelen uyeleri
 *            kendi uyesi gibi kullandigini,
 *        (b) Yapici zincirinin en tepeden asagiya calistigini,
 *        (c) instanceof ile IS-A iliskisinin sinandigini,
 *        (d) Ezilen metodun super ile geriye baglandigini gostermek.
 * ========================================================== */

public class KisiDemo {

    public static void main(String[] args) {
        System.out.println("=== 1) Ust sinif nesnesi ===");
        Kisi k = new Kisi("Ayse Yildiz", "12345678901", 1994);
        System.out.println("  " + k.kimlikKarti());
        System.out.println("  yas=" + k.yas() + "  resit=" + k.resitMi());

        System.out.println();
        System.out.println("=== 2) Alt sinif nesnesi : yapici zinciri ===");
        Ogrenci o = new Ogrenci("Elif Demir", "22345678902", 2005,
                                "YZM376014", "Yazilim Muhendisligi");
        System.out.println("  " + o.kimlikKarti());

        System.out.println();
        System.out.println("=== 3) Miras alinan uyeler dogrudan kullanilir ===");
        // getAd(), yas(), resitMi(), maskeliTcNo() Ogrenci'de YAZILMADI.
        System.out.println("  o.getAd()       -> " + o.getAd());
        System.out.println("  o.yas()         -> " + o.yas());
        System.out.println("  o.resitMi()     -> " + o.resitMi());
        System.out.println("  o.maskeliTcNo() -> " + o.maskeliTcNo());
        System.out.println("  Kisi.SIMDIKI_YIL -> " + Kisi.SIMDIKI_YIL);

        System.out.println();
        System.out.println("=== 4) Ogrenci'ye ozgu uyeler ===");
        o.gnoGuncelle(3.24);
        System.out.println("  gno=" + o.getGno() + "  basarili=" + o.basariliMi());
        o.gnoGuncelle(4.6);          // gecersiz -> uyari

        System.out.println();
        System.out.println("=== 5) Iki seviyeli hiyerarsi ===");
        Yonetici y = new Yonetici("Murat Kaya", "32345678903", 1978,
                                  "P-1042", 68000.0, "Bilgi Islem", 7);
        System.out.println("  " + y.kimlikKarti());
        System.out.printf("  brut=%.2f  net=%.2f  tazminat=%.2f%n",
                          y.getAylikBrut(), y.aylikNet(), y.yonetimTazminati());
        System.out.println("  kidemYili() (Personel'den) -> " + y.kidemYili());
        System.out.println("  yas() (Kisi'den)           -> " + y.yas());

        System.out.println();
        System.out.println("=== 6) IS-A iliskisinin sinanmasi ===");
        System.out.println("  o instanceof Ogrenci  -> " + (o instanceof Ogrenci));
        System.out.println("  o instanceof Kisi     -> " + (o instanceof Kisi));
        System.out.println("  o instanceof Object   -> " + (o instanceof Object));
        System.out.println("  y instanceof Personel -> " + (y instanceof Personel));
        System.out.println("  y instanceof Kisi     -> " + (y instanceof Kisi));
        System.out.println("  k instanceof Ogrenci  -> " + (k instanceof Ogrenci)
                           + "   (Kisi her zaman Ogrenci DEGILDIR)");

        System.out.println();
        System.out.println("=== 7) Ust tur referansi alt tur nesnesini tutabilir ===");
        Kisi ref = o;                       // yukari donusum (upcast) : guvenli
        System.out.println("  ref.kimlikKarti() -> " + ref.kimlikKarti());
        System.out.println("  ref.getClass()    -> " + ref.getClass().getSimpleName());
        // ref.getGno();  // DERLEME HATASI: Kisi turunde getGno() yok.

        System.out.println();
        System.out.println("=== 8) Uretilen ogrenci sayisi (Hafta 6 sayaci) ===");
        System.out.println("  Ogrenci.uretilen() -> " + Ogrenci.uretilen());
    }
}
