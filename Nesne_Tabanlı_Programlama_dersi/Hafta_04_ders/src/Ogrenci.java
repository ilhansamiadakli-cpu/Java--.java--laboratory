/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 2: TAM KAPSULLENMIS sinif
 *
 * Tum alanlar private. Disariya yalnizca gerekli kapilar acilir:
 *  - ogrenciNo : salt-okunur (final + yalnizca getter)
 *  - ad        : okunur/yazilir ama BOS OLAMAZ
 *  - yas       : okunur/yazilir ama 17..70 araliginda olmalidir
 *  - notOrt    : disaridan HIC yazilamaz; yalnizca notEkle() ile degisir
 */
public class Ogrenci {

    public static final int EN_KUCUK_YAS = 17;
    public static final int EN_BUYUK_YAS = 70;

    private final String ogrenciNo;   // dogumda belirlenir, bir daha degismez
    private String ad;
    private int yas;
    private double notToplami;
    private int notAdedi;

    public Ogrenci(String ogrenciNo, String ad, int yas) {
        this.ogrenciNo = ogrenciNo;
        setAd(ad);          // dogrulama tek yerde: setter'da
        setYas(yas);
        this.notToplami = 0.0;
        this.notAdedi = 0;
    }

    // ---- salt-okunur alan: setter YOK ----
    public String getOgrenciNo() {
        return ogrenciNo;
    }

    // ---- dogrulamali setter ----
    public String getAd() {
        return ad;
    }

    public void setAd(String yeniAd) {
        if (yeniAd == null || yeniAd.trim().isEmpty()) {
            System.out.println("  RED: ad bos birakilamaz; eski deger korundu.");
            if (this.ad == null) {
                this.ad = "ISIMSIZ";
            }
            return;
        }
        this.ad = yeniAd.trim();
    }

    public int getYas() {
        return yas;
    }

    public void setYas(int yeniYas) {
        if (yeniYas < EN_KUCUK_YAS || yeniYas > EN_BUYUK_YAS) {
            System.out.println("  RED: yas " + EN_KUCUK_YAS + ".." + EN_BUYUK_YAS
                    + " araliginda olmalidir (verilen: " + yeniYas + ").");
            return;
        }
        this.yas = yeniYas;
    }

    // ---- turetilmis (hesaplanan) getter: karsiligi olan alan YOK ----
    public double getNotOrtalamasi() {
        if (notAdedi == 0) {
            return 0.0;
        }
        return notToplami / notAdedi;
    }

    public int getNotAdedi() {
        return notAdedi;
    }

    /* Ortalama disaridan atanamaz; yalnizca gecerli not eklenerek degisir. */
    public boolean notEkle(double puan) {
        if (puan < 0.0 || puan > 100.0) {
            System.out.println("  RED: not 0..100 araliginda olmalidir (verilen: "
                    + puan + ").");
            return false;
        }
        notToplami += puan;
        notAdedi++;
        return true;
    }

    public boolean gecerMi() {
        return notAdedi > 0 && getNotOrtalamasi() >= 50.0;
    }

    @Override
    public String toString() {
        return String.format("Ogrenci[no=%s, ad=%s, yas=%d, ort=%.2f, notAdedi=%d]",
                ogrenciNo, ad, yas, getNotOrtalamasi(), notAdedi);
    }
}
