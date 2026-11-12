/*
 * Nesne Yonelimli Programlama - Hafta 09
 * Ders Ornegi 2: Cok bicimli dizi ve dinamik baglama.
 *
 * Amac: TEK bir Sekil[] dizisinde farkli alt sinif nesneleri tutmak;
 *       s.alan() cagrisinin, s'nin BILDIRILEN turune degil, dizideki
 *       GERCEK nesneye gore cozulmesini gostermek.
 */
public class SekilDemo {

    // Dikkat: parametre turu Sekil. Bu metot Daire, Kare, Ucgen sinifllarinin
    // adini bile bilmiyor; yine de hepsiyle dogru calisiyor.
    static double toplamAlan(Sekil[] sekiller) {
        double toplam = 0;
        for (Sekil s : sekiller) {
            toplam += s.alan();       // <-- dinamik baglama burada olur
        }
        return toplam;
    }

    static Sekil enBuyuk(Sekil[] sekiller) {
        Sekil enBuyuk = sekiller[0];
        for (Sekil s : sekiller) {
            if (s.alan() > enBuyuk.alan()) {
                enBuyuk = s;
            }
        }
        return enBuyuk;
    }

    public static void main(String[] args) {
        Sekil[] sekiller = {
            new Daire(3.0),
            new Kare(4.0),
            new Ucgen(3.0, 4.0, 5.0),
            new Daire(1.5),
            new Kare(2.5)
        };

        System.out.println("== Cok bicimli dizi ==");
        for (Sekil s : sekiller) {
            System.out.println("  " + s);      // toString de cok bicimli
        }

        System.out.printf("%nToplam alan : %.2f%n", toplamAlan(sekiller));
        System.out.println("En buyuk    : " + enBuyuk(sekiller));

        System.out.println();
        System.out.println("== Ayni cagri, farkli davranis ==");
        Sekil s = new Daire(2.0);
        System.out.printf("Sekil s = new Daire(2.0)  ->  s.alan() = %.4f%n", s.alan());
        s = new Kare(2.0);
        System.out.printf("Sekil s = new Kare(2.0)   ->  s.alan() = %.4f%n", s.alan());
        s = new Sekil("Bos");
        System.out.printf("Sekil s = new Sekil(...)  ->  s.alan() = %.4f%n", s.alan());
        System.out.println("Son satirdaki 0.0, Sekil'in kendi govdesidir.");
        System.out.println("Hafta 10'da bu govdeyi abstract ile kaldiracagiz.");
    }
}
