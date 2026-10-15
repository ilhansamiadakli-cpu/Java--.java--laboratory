/*
 * Nesne Yonelimli Programlama - Hafta 02
 * Ornek 3: Yapicili sinifin kullanimi
 */
public class OgrenciDemo {

    public static void main(String[] args) {

        // Tam yapici: nesne tek satirda ve gecerli durumda dogar
        Ogrenci o1 = new Ogrenci("Ali", "Yilmaz", 331001, 65, 78);

        // Kisa yapici: notlar sonra girilecek
        Ogrenci o2 = new Ogrenci("Ayse", "Demir", 331002);

        // toString() sayesinde nesne dogrudan yazdirilabiliyor
        System.out.println("--- Baslangic ---");
        System.out.println(o1);
        System.out.println(o2);

        // Notlar sonradan, denetimli bicimde guncelleniyor
        o2.notlariGuncelle(40, 52);
        System.out.println("\n--- Ayse'nin notlari girildi ---");
        System.out.println(o2);

        // Gecersiz degerler yapici tarafindan duzeltiliyor
        Ogrenci o3 = new Ogrenci("Mehmet", "Kaya", 331003, 250, -40);
        System.out.println("\n--- Gecersiz not denemesi (250 ve -40) ---");
        System.out.println(o3);

        // Kopya yapici: bagimsiz bir kopya
        Ogrenci kopya = new Ogrenci(o1);
        kopya.notlariGuncelle(95, 95);
        System.out.println("\n--- Kopya yapici deneyi ---");
        System.out.println("Ozgun : " + o1);
        System.out.println("Kopya : " + kopya);
        System.out.println("o1 == kopya -> " + (o1 == kopya));

        // Nesne dizisi
        Ogrenci[] sinif = { o1, o2, o3 };
        double toplam = 0;
        for (Ogrenci o : sinif) {
            toplam += o.ortalamaHesapla();
        }
        System.out.printf("%nSinif ortalamasi: %.2f%n", toplam / sinif.length);
    }
}
