import java.util.Locale;

public class Urun {
    private final String barkod;
    private String ad = "ISIMSIZ";
    private double fiyat;
    private int stok;

    public Urun(String barkod, String ad, double fiyat, int stok) {
        this.barkod = barkod;
        this.ad = "ISIMSIZ";
        this.fiyat = 0.0;
        this.stok = 0;
        setAd(ad);
        setFiyat(fiyat);
        if (stok > 0) {
            stokGir(stok);
        }
    }

    public String getBarkod() {
        return this.barkod;
    }

    public String getAd() {
        return this.ad;
    }

    public void setAd(String yeniAd) {
        if (yeniAd == null || yeniAd.trim().isEmpty()) {
            System.out.println("  RED: urun adi bos olamaz.");
            return;
        }
        this.ad = yeniAd.trim();
    }

    public double getFiyat() {
        return this.fiyat;
    }

    public void setFiyat(double yeniFiyat) {
        if (yeniFiyat < 0) {
            System.out.printf(Locale.US, "  RED: fiyat negatif olamaz (%.2f).%n", yeniFiyat);
            return;
        }
        this.fiyat = yeniFiyat;
    }

    public int getStok() {
        return this.stok;
    }

    public void stokGir(int adet) {
        if (adet <= 0) {
            System.out.println("  RED: giris adedi pozitif olmalidir (" + adet + ").");
            return;
        }
        this.stok += adet;
    }

    public void stokCik(int adet) {
        if (adet <= 0) {
            System.out.println("  RED: cikis adedi pozitif olmalidir (" + adet + ").");
            return;
        }
        if (adet > this.stok) {
            System.out.println("  RED: stok yetersiz (" + adet + " > " + this.stok + ").");
            return;
        }
        this.stok -= adet;
    }

    public double getStokDegeri() {
        return this.fiyat * this.stok;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Urun[%s, %s, %.2f TL, stok=%d]",
                this.barkod, this.ad, this.fiyat, this.stok);
    }
}