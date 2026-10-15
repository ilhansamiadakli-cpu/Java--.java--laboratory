/*
 * Nesne Yonelimli Programlama - Hafta 02
 * Ornek 2: Yapici metotlarla yeniden yazilmis Ogrenci sinifi
 *
 * Yapici metot (constructor), nesnenin GECERLI BIR DURUMDA dogmasini
 * garanti eder. Alanlar private oldugu icin disaridan bozulamaz.
 */
public class Ogrenci {

    // --- Ozellikler: artik private ---
    private String ad;
    private String soyad;
    private int numara;
    private double vizeNotu;
    private double finalNotu;

    /**
     * Tam yapici: tum bilgileri alir ve dogrular.
     * Nesne olusturulur olusturulmaz gecerli bir duruma sahip olur.
     */
    public Ogrenci(String ad, String soyad, int numara,
                   double vizeNotu, double finalNotu) {
        this.ad = ad;
        this.soyad = soyad;
        this.numara = numara;
        this.vizeNotu = notuDuzelt(vizeNotu);
        this.finalNotu = notuDuzelt(finalNotu);
    }

    /**
     * Kisa yapici: notlar henuz girilmemis ogrenciler icin.
     * this(...) ile tam yapiciyi cagirir; kod tekrari olmaz.
     */
    public Ogrenci(String ad, String soyad, int numara) {
        this(ad, soyad, numara, 0.0, 0.0);
    }

    /**
     * Kopya yapici: var olan bir ogrencinin bagimsiz kopyasini uretir.
     */
    public Ogrenci(Ogrenci kaynak) {
        this(kaynak.ad, kaynak.soyad, kaynak.numara,
             kaynak.vizeNotu, kaynak.finalNotu);
    }

    /** Not 0-100 araliginda degilse sinira cekilir. */
    private static double notuDuzelt(double not) {
        if (not < 0) {
            return 0.0;
        }
        if (not > 100) {
            return 100.0;
        }
        return not;
    }

    // --- Davranislar ---

    public double ortalamaHesapla() {
        return vizeNotu * 0.4 + finalNotu * 0.6;
    }

    public boolean gectiMi() {
        return ortalamaHesapla() >= 50.0;
    }

    public String tamAd() {
        return ad + " " + soyad;
    }

    public int getNumara() {
        return numara;
    }

    public void notlariGuncelle(double vize, double bitirme) {
        this.vizeNotu = notuDuzelt(vize);
        this.finalNotu = notuDuzelt(bitirme);
    }

    /**
     * Nesnenin metin gosterimi. System.out.println(nesne) cagrildiginda
     * Java bu metodu otomatik olarak calistirir.
     */
    @Override
    public String toString() {
        return String.format("%d | %-14s | vize:%5.1f final:%5.1f ort:%6.2f | %s",
                numara, tamAd(), vizeNotu, finalNotu,
                ortalamaHesapla(), gectiMi() ? "GECTI" : "KALDI");
    }
}
