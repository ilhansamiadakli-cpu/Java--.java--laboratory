/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 7: Degismez nesnenin davranisi
 */
public class ParaBirimiDemo {

    public static void main(String[] args) {
        ParaBirimi ucret = ParaBirimi.lira(1250.75);
        System.out.println("ucret          : " + ucret);

        ParaBirimi zam = ParaBirimi.lira(200.00);
        ParaBirimi yeni = ucret.topla(zam);

        System.out.println("zam            : " + zam);
        System.out.println("yeni           : " + yeni);
        System.out.println("ucret (hala)   : " + ucret + "   <- DEGISMEDI");

        System.out.println();
        System.out.println("--- Paylasim guvenli ---");
        ParaBirimi paylasilan = ucret;
        System.out.println("paylasilan     : " + paylasilan);
        yardimciIslem(paylasilan);
        System.out.println("cagri sonrasi  : " + paylasilan + "   <- DEGISMEDI");

        System.out.println();
        System.out.println("--- Kural ihlali yapiciyla engellenir ---");
        try {
            ParaBirimi.lira(10).topla(new ParaBirimi(500, "USD"));
        } catch (IllegalArgumentException e) {
            System.out.println("Yakalandi: " + e.getMessage());
        }
        try {
            new ParaBirimi(100, "TL");
        } catch (IllegalArgumentException e) {
            System.out.println("Yakalandi: " + e.getMessage());
        }
    }

    /* Bu metot nesneyi degistirmeye calissa bile degistiremez: setter yoktur. */
    static void yardimciIslem(ParaBirimi p) {
        ParaBirimi ikiKati = p.carp(2);
        System.out.println("  yardimci icinde uretilen: " + ikiKati);
    }
}
