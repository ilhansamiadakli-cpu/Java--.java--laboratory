/*
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 05 - Yapici ve Yikici Yontemler
 * Ders ornegi 4
 *
 * Amac: Statik fabrika metodu deseni. Yapici private yapilir; nesne uretimi
 *       ADLANDIRILMIS metotlar uzerinden yapilir. Boylece:
 *         - her uretim yolunun bir adi olur (yapicilar adsizdir),
 *         - ayni imzayla farkli anlam uretilebilir,
 *         - gerektiginde nesne uretmeden var olan nesne dondurulebilir,
 *         - gecersiz istekte null yerine denetimli bir yol izlenebilir.
 */
public class Kayit {

    public static final Kayit BOS = new Kayit("-", "BOS", 0);

    private final String ogrenciNo;
    private final String dersKodu;
    private final int kredi;

    /* Yapici PRIVATE: disaridan new Kayit(...) yazilamaz. */
    private Kayit(String ogrenciNo, String dersKodu, int kredi) {
        this.ogrenciNo = ogrenciNo;
        this.dersKodu = dersKodu;
        this.kredi = kredi;
    }

    /* --- Fabrika 1: normal kayit --- */
    public static Kayit olustur(String ogrenciNo, String dersKodu, int kredi) {
        if (ogrenciNo == null || ogrenciNo.isBlank()) {
            System.out.println("  RED: ogrenci numarasi bos olamaz -> BOS dondu");
            return BOS;
        }
        if (kredi < 1 || kredi > 10) {
            System.out.println("  RED: kredi 1-10 araliginda olmali -> BOS dondu");
            return BOS;
        }
        return new Kayit(ogrenciNo.trim(), dersKodu.toUpperCase(), kredi);
    }

    /* --- Fabrika 2: ayni imza, farkli anlam (yapici ile MUMKUN DEGIL) --- */
    public static Kayit dinleyici(String ogrenciNo, String dersKodu) {
        return new Kayit(ogrenciNo, dersKodu.toUpperCase(), 0);
    }

    /* --- Fabrika 3: metin ayristirarak uretim --- */
    public static Kayit metinden(String satir) {
        String[] p = satir.split(";");
        if (p.length != 3) {
            System.out.println("  RED: bicim 'no;ders;kredi' olmali -> BOS dondu");
            return BOS;
        }
        return olustur(p[0], p[1], Integer.parseInt(p[2]));
    }

    /* --- Fabrika 4: kopya (kopya yapicinin fabrika karsiligi) --- */
    public static Kayit kopyasi(Kayit kaynak) {
        if (kaynak == BOS) {
            return BOS;                 // yeni nesne uretmeye gerek yok
        }
        return new Kayit(kaynak.ogrenciNo, kaynak.dersKodu, kaynak.kredi);
    }

    public String getOgrenciNo() { return ogrenciNo; }
    public String getDersKodu()  { return dersKodu; }
    public int getKredi()        { return kredi; }
    public boolean bosMu()       { return this == BOS; }

    @Override
    public String toString() {
        return "Kayit[" + ogrenciNo + ", " + dersKodu + ", " + kredi + " kredi]";
    }
}
