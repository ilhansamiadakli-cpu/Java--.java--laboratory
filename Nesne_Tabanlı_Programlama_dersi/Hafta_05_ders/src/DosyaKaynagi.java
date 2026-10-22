/*
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 05 - Yapici ve Yikici Yontemler
 * Ders ornegi 6
 *
 * Amac: Java'da YIKICI (destructor) yoktur. Bellegi cop toplayici geri alir;
 *       ancak dosya, ag baglantisi, veritabani oturumu gibi DIS kaynaklar
 *       cop toplayicinin sorumlulugunda degildir.
 *
 *       Cozum: AutoCloseable arayuzunu gerceklemek ve nesneyi
 *       try-with-resources bloguyla kullanmak.
 */
public class DosyaKaynagi implements AutoCloseable {

    private final String ad;
    private boolean acik;

    public DosyaKaynagi(String ad) {
        this.ad = ad;
        this.acik = true;
        System.out.println("    ACILDI  : " + ad);
    }

    public void yaz(String satir) {
        if (!acik) {
            System.out.println("    RED     : " + ad + " kapali, yazilamaz");
            return;
        }
        System.out.println("    YAZILDI : " + ad + " <- \"" + satir + "\"");
    }

    public boolean acikMi() {
        return acik;
    }

    /* close() metodu AutoCloseable'dan gelir; try-with-resources onu
       blok bittiginde OTOMATIK ve GARANTILI olarak cagirir. */
    @Override
    public void close() {
        if (acik) {
            acik = false;
            System.out.println("    KAPANDI : " + ad);
        }
    }

    @Override
    public String toString() {
        return "DosyaKaynagi[" + ad + ", acik=" + acik + "]";
    }
}
