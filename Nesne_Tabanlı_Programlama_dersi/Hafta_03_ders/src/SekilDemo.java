/*
 * Nesne Yonelimli Programlama - Hafta 03
 * Ornek 4: DORT ILKE tek ekranda
 *
 * Bu program SekilKotu.java'daki uc sorunun da cozuldugunu gosterir:
 *   1) switch yok            -> COK BICIMLILIK
 *   2) olu alan yok          -> her alt sinif yalnizca kendi verisini tutar
 *   3) yeni tur eklemek icin var olan kod degismiyor -> SOYUTLAMA + MIRAS
 */
public class SekilDemo {

    // Bu metot Daire, Kare, Ucgen adlarini HIC BILMEZ. Yalnizca Sekil'i bilir.
    private static void rapor(Sekil[] sekiller) {
        double toplamAlan = 0.0;
        for (Sekil s : sekiller) {
            System.out.println("  " + s.ozet());   // dinamik baglama
            toplamAlan += s.alan();
        }
        System.out.printf("  %-11s%21s%9.3f%n", "TOPLAM", "", toplamAlan);
    }

    public static void main(String[] args) {
        // Ust tur referansi, alt tur nesnesi: cok bicimliligin can damari.
        Sekil[] sekiller = {
            new Daire(3.0, "kirmizi"),
            new Kare(4.0, "mavi"),
            new Ucgen(6.0, 4.0, "yesil")
        };

        System.out.println("--- 1) COK BICIMLILIK: tek dongu, uc farkli davranis ---");
        rapor(sekiller);

        System.out.println();
        System.out.println("--- 2) KAPSULLEME: renk yalnizca setter'dan degisir ---");
        sekiller[0].setRenk("mor");
        sekiller[1].setRenk("   ");            // reddedilir
        System.out.println("  daire.renk = " + sekiller[0].getRenk());
        System.out.println("  kare.renk  = " + sekiller[1].getRenk());

        System.out.println();
        System.out.println("--- 3) SOYUTLAMA: cagiran taraf formulu bilmiyor ---");
        System.out.printf("  En buyuk alanli sekil: %s%n", enBuyuk(sekiller).getAd());

        System.out.println();
        System.out.println("--- 4) MIRAS: ozet() hicbir alt sinifta yazilmadi ---");
        System.out.println("  Ucgen bir Sekil mi? " + (sekiller[2] instanceof Sekil));
        System.out.println("  Sekil bir Ucgen mi? " + (sekiller[0] instanceof Ucgen));

        System.out.println();
        System.out.println("--- 5) Gecersiz olcu: nesne yine de gecerli dogar ---");
        Sekil bozuk = new Kare(-5.0, "sari");
        System.out.println("  " + bozuk.ozet());
    }

    private static Sekil enBuyuk(Sekil[] sekiller) {
        Sekil enIyi = sekiller[0];
        for (Sekil s : sekiller) {
            if (s.alan() > enIyi.alan()) {
                enIyi = s;
            }
        }
        return enIyi;
    }
}
