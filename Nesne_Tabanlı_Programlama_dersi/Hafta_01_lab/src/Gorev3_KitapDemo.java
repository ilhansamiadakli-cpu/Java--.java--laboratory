

public class Gorev3_KitapDemo {
    public static void main(String[] args) {
        int buYil = 2026;
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

        
        inceMemet.bilgiYaz(buYil); System.out.println(" ");

        HaryPoter.bilgiYaz(buYil);
        System.out.printf("%n%n[ yüzde 20 indirim uygulandı]%n%n");

        inceMemet.indirimUygula(20);
        HaryPoter.indirimUygula(20);
        inceMemet.bilgiYaz(buYil); System.out.println(" ");

        HaryPoter.bilgiYaz(buYil); System.out.println(" ");

        Kitap[] raf = {inceMemet, HaryPoter};
        Double toplam = 0.0;
        for(Kitap raflar : raf){
            toplam = toplam + raflar.fiyat;
        }


        System.out.println("\nKitabların rafta toplam fiyatı: " + toplam + " TL");
    }
}
