/*
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 05 - Yapici ve Yikici Yontemler
 * Ders ornegi 2
 *
 * Amac: this(...) zincirinin calisma sirasini ve kopya yapicinin
 *       davranisini ekranda gostermek.
 */
public class NoktaDemo {

    public static void main(String[] args) {
        System.out.println("=== 1) Parametresiz yapici: zincir 4 -> 1 ===");
        Nokta p1 = new Nokta();
        System.out.println("  Sonuc : " + p1);

        System.out.println();
        System.out.println("=== 2) Tek parametreli yapici: zincir 4 -> 2 ===");
        Nokta p2 = new Nokta(3.0);
        System.out.println("  Sonuc : " + p2);

        System.out.println();
        System.out.println("=== 3) Iki parametreli yapici: zincir 4 -> 3 ===");
        Nokta p3 = new Nokta(3.0, 4.0);
        System.out.println("  Sonuc : " + p3);
        System.out.printf("  Baslangictan uzaklik : %.2f%n", p3.baslangictanUzaklik());

        System.out.println();
        System.out.println("=== 4) Ana yapici dogrudan cagrilirsa ===");
        Nokta p4 = new Nokta(-2.5, 6.0, "Hedef");
        System.out.println("  Sonuc : " + p4);

        System.out.println();
        System.out.println("=== 5) Kopya yapici ===");
        Nokta kopya = new Nokta(p4);
        System.out.println("  Kaynak : " + p4);
        System.out.println("  Kopya  : " + kopya);
        System.out.println("  Ayni nesne mi? " + (p4 == kopya));

        System.out.println();
        System.out.println("=== 6) Gecersiz deger: ana yapici reddeder ===");
        try {
            new Nokta(Double.NaN, 1.0, "Bozuk");
        } catch (IllegalArgumentException e) {
            System.out.println("  Yakalandi -> " + e.getMessage());
            System.out.println("  Nesne HIC dogmadi.");
        }
    }
}
