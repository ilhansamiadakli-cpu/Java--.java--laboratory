/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 10a: Dort erisim duzeyinin ayni sinifta gosterimi
 *
 * Bu sinif "okul" paketindedir. Dort uyesi dort farkli erisim duzeyindedir.
 * Hangi uyeye nereden erisilebildigini Ornek 10b ve 10c gosterir.
 */
package okul;

public class Kayit {

    public    String kurum   = "KTU Of Teknoloji Fakultesi";  // her yerden
    protected String bolum   = "Yazilim Muhendisligi";        // paket + alt sinif
              String donem   = "2026-2027 Guz";               // yalnizca paket
    private   String gizliId = "K-000731";                    // yalnizca bu sinif

    public String getGizliId() {
        return gizliId;    // private alana ancak sinifin kendi metodu erisir
    }

    public void hepsiniYaz() {
        System.out.println("  [Kayit icinden] kurum   = " + kurum);
        System.out.println("  [Kayit icinden] bolum   = " + bolum);
        System.out.println("  [Kayit icinden] donem   = " + donem);
        System.out.println("  [Kayit icinden] gizliId = " + gizliId);
    }
}
