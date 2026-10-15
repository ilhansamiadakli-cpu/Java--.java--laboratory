/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 10b: AYNI PAKETTEN erisim
 *
 * Bu sinif da "okul" paketindedir. Bu nedenle public, protected ve
 * varsayilan (paket) uyelerin ucune de erisebilir; private uyeye erisemez.
 */
package okul;

public class PaketIciDemo {

    public static void main(String[] args) {
        Kayit k = new Kayit();
        System.out.println("=== Ayni paket: okul.PaketIciDemo ===");
        System.out.println("  public    kurum = " + k.kurum);
        System.out.println("  protected bolum = " + k.bolum);
        System.out.println("  varsayilan donem = " + k.donem);
        // System.out.println(k.gizliId);   // DERLEME HATASI: private
        System.out.println("  private   gizliId -> yalnizca getGizliId(): "
                + k.getGizliId());
        System.out.println();
        k.hepsiniYaz();
    }
}
