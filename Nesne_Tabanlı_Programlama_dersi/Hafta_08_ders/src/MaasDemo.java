/*
 * Nesne Yonelimli Programlama - Hafta 09
 * Ders Ornegi 7: Cok bicimli bordro.
 *
 * Amac: bordroYazdir() ve toplamGider() metotlari YALNIZCA Calisan turunu
 *       bilir. Yeni bir calisan turu eklendiginde bu iki metot DEGISMEZ.
 *       Acik-Kapali ilkesinin (Open/Closed) somut gosterimi.
 */
public class MaasDemo {

    static void bordroYazdir(Calisan[] kadro) {
        System.out.println("SICIL  AD         UNVAN                MAAS");
        System.out.println("---------------------------------------------------");
        for (Calisan c : kadro) {
            System.out.println(c);           // toString + maasHesapla, ikisi de cok bicimli
        }
    }

    static double toplamGider(Calisan[] kadro) {
        double toplam = 0;
        for (Calisan c : kadro) {
            toplam += c.maasHesapla();
        }
        return toplam;
    }

    static Calisan enYuksekMaas(Calisan[] kadro) {
        Calisan enYuksek = kadro[0];
        for (Calisan c : kadro) {
            if (c.maasHesapla() > enYuksek.maasHesapla()) {
                enYuksek = c;
            }
        }
        return enYuksek;
    }

    public static void main(String[] args) {
        Calisan[] kadro = {
            new Muhendis("Ayse", "M-101", 45000, 3),
            new Yonetici("Kemal", "Y-201", 60000, 8),
            new Stajyer("Deniz", "S-301", 22),
            new Muhendis("Bora", "M-102", 42000, 1),
            new Calisan("Elif", "G-401", 38000)
        };

        bordroYazdir(kadro);
        System.out.println("---------------------------------------------------");
        System.out.printf("TOPLAM GIDER                        %10.2f TL%n",
                          toplamGider(kadro));
        System.out.println();
        System.out.println("En yuksek maas: " + enYuksekMaas(kadro).getAd());

        System.out.println();
        System.out.println("== Unvana gore sayim (getClass ile) ==");
        for (Calisan c : kadro) {
            System.out.printf("  %-10s -> sinif: %s%n",
                              c.getAd(), c.getClass().getSimpleName());
        }

        System.out.println();
        System.out.println("Dikkat: bordroYazdir ve toplamGider metotlarinda");
        System.out.println("Muhendis, Yonetici veya Stajyer sozcugu GECMIYOR.");
        System.out.println("Yeni bir tur eklense de bu metotlar degismez.");
    }
}
