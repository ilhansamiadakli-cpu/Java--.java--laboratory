public class Kitap2 {
    private String baslik;
    private String yazar;
    private int sayfaSayisi;
    private int basimYili;
    private double fiyat;

    public Kitap2(String baslik, String yazar, int sayfaSayisi, int basimYili, double fiyat) {
        if (baslik == null || baslik.isBlank()) {
            this.baslik = "İsimsiz";
        } else {
            this.baslik = baslik;
        }
        if (yazar == null || yazar.isBlank()) {
            this.yazar = "İsimsiz";
        } else {
            this.yazar = yazar;
        }
        if (sayfaSayisi <= 0) {
            this.sayfaSayisi = 1;
        } else {
            this.sayfaSayisi = sayfaSayisi;
        }
        if (basimYili < 1450 || basimYili > 2100) {
            this.basimYili = 2026;
        } else {
            this.basimYili = basimYili;
        }
        if (fiyat < 0) {
            this.fiyat = 0.0;
        } else {
            this.fiyat = fiyat;
        }
    }
    String getBaslik() {
        return baslik;
    }
    String getYazar() {
        return yazar;
    }
    int getSayfaSayisi() {
        return sayfaSayisi;
    }
    int getBasimYili() {
        return basimYili;
    }
    double getFiyat() {
        return fiyat;
    }
    @Override
    public String toString() {
        return baslik + "  "+ yazar + "    " + sayfaSayisi + "  "
                + basimYili + "  " + fiyat + "";
    }
}
