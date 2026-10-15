/*
 * Nesne Yonelimli Programlama - Hafta 02
 * Ornek 8: Nesne yasam dongusu ve cop toplayici
 *
 * C++'ta yikici (destructor) nesne yok edilirken CALISMASI GARANTI
 * edilerek cagrilirdi. Java'da yikici YOKTUR. Bellek, cop toplayici
 * (garbage collector) tarafindan, ZAMANI BELIRSIZ bicimde geri alinir.
 * Bu nedenle kaynak kapatma isi try-with-resources'a birakilir.
 */
public class YasamDongusu {

    /** Yonetilmesi gereken bir kaynagi temsil eder. */
    static class Kaynak implements AutoCloseable {
        private final String ad;

        Kaynak(String ad) {
            this.ad = ad;
            System.out.println("  [ACILDI]  " + ad);
        }

        void kullan() {
            System.out.println("  [KULLANIM] " + ad);
        }

        @Override
        public void close() {
            System.out.println("  [KAPANDI] " + ad);
        }
    }

    public static void main(String[] args) {

        System.out.println("1) Nesne dogar (new) ve referansla erisilir:");
        Ogrenci o = new Ogrenci("Ali", "Yilmaz", 331001, 65, 78);
        System.out.println("   " + o);

        System.out.println("\n2) Referans baska nesneye yonlendirilir:");
        o = new Ogrenci("Ayse", "Demir", 331002, 70, 80);
        System.out.println("   " + o);
        System.out.println("   Ilk nesneye artik ERISILEMEZ -> cop toplayici adayi.");

        System.out.println("\n3) Referans null yapilir:");
        o = null;
        System.out.println("   Ikinci nesne de erisilemez duruma geldi.");

        System.out.println("\n4) Cop toplayici ONERISI (garanti degil):");
        System.gc();
        System.out.println("   System.gc() yalnizca bir ONERIDIR; JVM uymayabilir.");

        System.out.println("\n5) Kaynak yonetimi: try-with-resources");
        try (Kaynak k = new Kaynak("veri.txt")) {
            k.kullan();
        }   // close() burada OTOMATIK cagrilir
        System.out.println("   try blogu bitti; close() calisti.");

        System.out.println("\n6) Hata olsa bile close() calisir:");
        try (Kaynak k = new Kaynak("ayarlar.txt")) {
            k.kullan();
            if (k != null) {
                throw new RuntimeException("beklenmedik hata");
            }
        } catch (RuntimeException e) {
            System.out.println("   [YAKALANDI] " + e.getMessage());
        }
    }
}
