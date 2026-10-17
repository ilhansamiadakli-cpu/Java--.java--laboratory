public class Kitap {
    public  String baslik;
    public  String yazar;
    public  int sayfaSayisi;
    public  int basimYili;
    public  double fiyat;

    public  int yasi(int buguninYili){
        if(basimYili - buguninYili <= 0){
            System.out.println("Hata: Geçersiz yıl girdiniz.");
            return -1;// nasıl null yada eror dönerim, main de eğer yasi -1 dönerse hata derim
        }
        return buguninYili - basimYili;
    }
    public  boolean kalinMi(){
        if(sayfaSayisi > 400){
            return true;
        }
        return false;
    }
    public  double sayfaBasinaFiyat(){
        return (double)fiyat / sayfaSayisi;
    }
    public  void indirimUygula(double yuzde){
        if(yuzde < 0 || yuzde > 100){
            System.out.println("Hata: lütfen geçerli bir indirim oranı giriniz");
            return ;
        }
        fiyat = fiyat - (fiyat * yuzde / 100.0);
    }
    public   void bilgiYaz(){
        System.out.printf("Kitap: %s | Yazar: %s | Sayfa: %d | Yıl: %d | Fiyat: %.2f TL%n", baslik, yazar, sayfaSayisi, basimYili, fiyat);
    }
}
