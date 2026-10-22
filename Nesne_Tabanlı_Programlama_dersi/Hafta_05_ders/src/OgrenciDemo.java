/*
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 05 - Yapici ve Yikici Yontemler
 * Ders ornegi 10
 *
 * Amac: Ogrenci sinifinin Hafta 5 surumunu surucu programla gostermek:
 *       yapici zinciri, gecersiz istekte nesnenin HIC dogmamasi,
 *       sig kopya ile derin kopya farki.
 */
public class OgrenciDemo {

    public static void main(String[] args) {
        System.out.println("=== 1) Uc yapici, tek dogrulama noktasi ===");
        Ogrenci o1 = new Ogrenci("376201", "Elif Kaya");
        Ogrenci o2 = new Ogrenci("376202", "Mert Aydin", 22);
        Ogrenci o3 = new Ogrenci("376203", "Zeynep Sahin", 20, new int[5]);
        System.out.println("  " + o1);
        System.out.println("  " + o2);
        System.out.println("  " + o3);

        System.out.println();
        System.out.println("=== 2) Gecersiz istek: nesne HIC dogmuyor ===");
        dene("376204", "", 20);
        dene("376205", "Ali Vural", 12);
        dene("", "Ayse Demir", 25);

        System.out.println();
        System.out.println("=== 3) Not ekleme ve turetilmis ortalama ===");
        o1.notEkle(85);
        o1.notEkle(70);
        o1.notEkle(95);
        o1.notEkle(120);
        System.out.println("  " + o1);

        System.out.println();
        System.out.println("=== 4) KOPYA YAPICI: derin kopya ===");
        Ogrenci kopya = new Ogrenci(o1);
        System.out.println("  Kaynak : " + o1);
        System.out.println("  Kopya  : " + kopya);
        System.out.println("  Ayni nesne mi? " + (o1 == kopya));

        kopya.setAd("Elif Kaya (2. kayit)");
        kopya.notEkle(40);
        System.out.println("  --- kopya degistirildi ---");
        System.out.println("  Kaynak : " + o1);
        System.out.println("  Kopya  : " + kopya);
        System.out.println("  Kaynak etkilenmedi -> DERIN kopya");

        System.out.println();
        System.out.println("=== 5) Ana yapicida clone() olmasaydi ne olurdu? ===");
        int[] disDizi = { 50, 60, 70, 0, 0 };
        Ogrenci o4 = new Ogrenci("376206", "Can Ozturk", 21, disDizi);
        disDizi[0] = 999;
        System.out.println("  Disaridaki dizi degistirildi: disDizi[0]=999");
        o4.notEkle(50);
        System.out.println("  Nesne icindeki ilk not : " + o4.getNotlar()[0]);
        System.out.println("  Nesne etkilenmedi; cunku yapici diziyi KOPYALADI.");
    }

    private static void dene(String no, String ad, int yas) {
        try {
            Ogrenci o = new Ogrenci(no, ad, yas);
            System.out.println("  Uretildi : " + o);
        } catch (IllegalArgumentException e) {
            System.out.println("  RET      : " + e.getMessage());
        }
    }
}
