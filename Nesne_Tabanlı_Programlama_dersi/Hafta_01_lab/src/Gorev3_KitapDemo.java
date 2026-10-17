

public class Gorev3_KitapDemo {
    public static void main(String[] args) {
        Kitap inceMemet = new Kitap();
        inceMemet.baslik = "İnce Memet";
        inceMemet.yazar = "Yaşar Kemal";
        inceMemet.sayfaSayisi = 400;
        inceMemet.basimYili = 1955;
        inceMemet.fiyat = 120.7;

        Kitap HaryPoter = new Kitap();
        HaryPoter.baslik = "Harry Potter";
        HaryPoter.yazar = "J.K. Rowling";
        HaryPoter.sayfaSayisi = 300;
        HaryPoter.basimYili = 1997;
        HaryPoter.fiyat = 150.8;

        inceMemet.bilgiYaz();
        System.out.println(" ");
        HaryPoter.bilgiYaz();
        System.out.println("-------[%20 indirim uygulandı]-------");
        inceMemet.indirimUygula(20);
        HaryPoter.indirimUygula(20);
        inceMemet.bilgiYaz();
        System.out.println(" ");
        HaryPoter.bilgiYaz();
        System.out.println(" ");
        double toplam = inceMemet.fiyat + HaryPoter.fiyat;
        System.out.println("Kitabların rafta toplam fiyatı: " + toplam);
    }
}
