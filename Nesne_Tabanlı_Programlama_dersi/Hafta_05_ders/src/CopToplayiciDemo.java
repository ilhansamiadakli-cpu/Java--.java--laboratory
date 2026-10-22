/*
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 05 - Yapici ve Yikici Yontemler
 * Ders ornegi 8
 *
 * Amac: C'deki free() ile Java'daki cop toplayici (garbage collector)
 *       arasindaki farki gostermek. Bir nesne, kendisine ULASILABILIR
 *       hicbir referans kalmadiginda "cop" olur; bellegi ne zaman geri
 *       alinacagi ise programcinin denetiminde DEGILDIR.
 */
public class CopToplayiciDemo {

    static class Blok {
        private final String ad;
        private final byte[] veri;

        Blok(String ad, int boyutKb) {
            this.ad = ad;
            this.veri = new byte[boyutKb * 1024];
            System.out.println("  URETILDI : " + ad + " (" + boyutKb + " KB)");
        }

        @Override
        public String toString() {
            return "Blok[" + ad + ", " + (veri.length / 1024) + " KB]";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1) Referans sayisi ve ulasilabilirlik ===");
        Blok a = new Blok("A", 64);
        Blok b = a;                      // ayni nesneye ikinci referans
        System.out.println("  a == b ? " + (a == b) + "  -> tek nesne, iki referans");
        a = null;
        System.out.println("  a = null yapildi; nesne HALA yasiyor: " + b);
        b = null;
        System.out.println("  b = null yapildi; nesne artik ULASILAMAZ -> cop");

        System.out.println();
        System.out.println("=== 2) Kapsam disina cikan nesne otomatik cop olur ===");
        {
            Blok gecici = new Blok("Gecici", 32);
            System.out.println("  Blok icinde  : " + gecici);
        }
        System.out.println("  Blok bitti; 'gecici' adi artik yok -> nesne cop");

        System.out.println();
        System.out.println("=== 3) Cop toplama ZAMANI programcinin elinde degildir ===");
        for (int i = 0; i < 200; i++) {
            new Blok2(i);                // hicbir referans tutulmuyor
        }
        System.out.println("  200 gecici nesne uretildi, hicbiri tutulmadi.");
        System.gc();                     // yalnizca bir ONERIDIR
        System.out.println("  System.gc() cagrildi - bu bir EMIR degil, ONERIDIR.");
        System.out.println("  JVM cop toplamayi hic yapmayabilir de.");

        System.out.println();
        System.out.println("=== 4) C ile karsilastirma ===");
        System.out.println("  C   : p = malloc(n);  ...  free(p);   <- programci sorumlu");
        System.out.println("  Java: p = new T();    ...  (yok)      <- JVM sorumlu");
        System.out.println("  Java'da free() yoktur; cift free ve sarkan isaretci de yoktur.");
        System.out.println("  Buna karsilik DOSYA gibi dis kaynaklar icin close() gerekir.");
    }

    static class Blok2 {
        private final byte[] veri = new byte[8 * 1024];
        Blok2(int no) {
            veri[0] = (byte) no;
        }
    }
}
