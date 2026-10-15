public class Uye {
    private static int sonrakiSira = 1;

    private final String uyeNo;
    private final String katilimYili;
    private String ad = "ISIMSIZ";
    private int puan;

    public Uye(String katilimYili, String ad) {
        this.katilimYili = (katilimYili == null || katilimYili.trim().isEmpty()) ? "2026" : katilimYili.trim();
        int sira = sonrakiSira++;
        this.uyeNo = String.format("U-%s-%04d", this.katilimYili, sira);
        this.ad = "ISIMSIZ";
        this.puan = 0;
        setAd(ad);
    }

    public Uye(String ad) {
        this("2026", ad);
    }

    public String getUyeNo() {
        return this.uyeNo;
    }

    public String getKatilimYili() {
        return this.katilimYili;
    }

    public String getAd() {
        return this.ad;
    }

    public void setAd(String yeniAd) {
        if (yeniAd == null || yeniAd.trim().isEmpty()) {
            System.out.println("  RED: uye adi bos olamaz.");
            return;
        }
        this.ad = yeniAd.trim();
    }

    public int getPuan() {
        return this.puan;
    }

    public void puanEkle(int miktar) {
        if (miktar <= 0) {
            System.out.println("  RED: eklenecek puan pozitif olmalidir (" + miktar + ").");
            return;
        }
        this.puan += miktar;
    }

    public void puanHarca(int miktar) {
        if (miktar <= 0) {
            System.out.println("  RED: harcanacak puan pozitif olmalidir (" + miktar + ").");
            return;
        }
        if (miktar > this.puan) {
            System.out.println("  RED: yetersiz puan (" + miktar + " > " + this.puan + ").");
            return;
        }
        this.puan -= miktar;
    }

    public String getSeviye() {
        if (this.puan >= 1000) {
            return "PLATIN";
        } else if (this.puan >= 500) {
            return "ALTIN";
        } else if (this.puan >= 100) {
            return "GUMUS";
        } else {
            return "STANDART";
        }
    }

    @Override
    public String toString() {
        return "Uye[" + this.uyeNo + ", " + this.ad + ", puan=" + this.puan + ", " + getSeviye() + "]";
    }
}