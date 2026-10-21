public class Kitap2 {
    private String baslik;
    private String yazar;
    private int sayfaSayisi;
    private int basimYili;
    private double fiyat;

    public Kitap2(String baslik, String yazar, int sayfaSayisi, int basimYili, double fiyat) {
        //System.out.println("tam  yapici calisti"); // sınama
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

    public Kitap2(String baslik, String yazar, int sayfaSayisi, int basimYili){
        this(baslik,yazar,sayfaSayisi,basimYili,0.0);
    }
    public Kitap2(String baslik, String yazar){
        this(baslik, yazar, 1,2026 ,0.0);
    }
    public Kitap2(Kitap2 kaynak){
        this(kaynak.baslik,kaynak.yazar,kaynak.sayfaSayisi,kaynak.basimYili,kaynak.fiyat);
    }

    public void setFiyat(double fiyat){
        if(fiyat < 0){
            System.out.println("Hata: fiyat negatif olamaz");
            return ;
        }
        this.fiyat = fiyat;
    }
    public boolean kalinMi(){
        if( this.sayfaSayisi > 400){
            return true;
        }
        return false;
    }
    public double sayfaBasinaFiyat(){
        return (double)this.fiyat / this.sayfaSayisi;// ne olur ne olmaz (double) dedim
    }
    public void indirimUygula(double yuzde){
        if(yuzde > 100 || yuzde < 0){
            System.out.println("Hata: indirim oranı sadece 0 ile 100 arasında olmalıdır");
            return ;
        }// hata kontrol
        this.fiyat =  this.fiyat - (this.fiyat*(yuzde/100));
    }
    public int yasi(int buguninYili){
        if(buguninYili - this.basimYili < 0){
            System.out.println("Hata bu günkü yılı doğru giriniz lütfen");
            return -1;// hata kodu
        }
        return buguninYili - this.basimYili;
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
