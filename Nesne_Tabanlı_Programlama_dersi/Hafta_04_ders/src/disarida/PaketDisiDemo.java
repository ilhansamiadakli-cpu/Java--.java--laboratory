/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 10c: FARKLI PAKETTEN erisim
 *
 * Bu sinif "disarida" paketindedir ve okul.Kayit sinifini genisletir.
 *  - public    : erisilir
 *  - protected : yalnizca MIRAS YOLUYLA (this uzerinden) erisilir
 *  - varsayilan: erisilemez
 *  - private   : erisilemez
 */
package disarida;

import okul.Kayit;

public class PaketDisiDemo extends Kayit {

    public void kendiUzerindenOku() {
        System.out.println("=== Farkli paket, ALT SINIF: disarida.PaketDisiDemo ===");
        System.out.println("  public    kurum = " + this.kurum);
        System.out.println("  protected bolum = " + this.bolum + "   <- miras yoluyla");
        // System.out.println(this.donem);     // DERLEME HATASI: varsayilan erisim
        // System.out.println(this.gizliId);   // DERLEME HATASI: private
        System.out.println("  varsayilan donem -> ERISILEMEZ (baska paket)");
        System.out.println("  private   gizliId -> ERISILEMEZ");
    }

    public static void main(String[] args) {
        new PaketDisiDemo().kendiUzerindenOku();

        System.out.println();
        System.out.println("=== Farkli paket, ILGISIZ NESNE uzerinden ===");
        Kayit baskasi = new Kayit();
        System.out.println("  public    kurum = " + baskasi.kurum);
        // System.out.println(baskasi.bolum);  // DERLEME HATASI: protected, farkli paket
        System.out.println("  protected bolum -> ERISILEMEZ (baska nesne, baska paket)");
        System.out.println("  Yalnizca public uyeler disari acik kalir.");
    }
}
