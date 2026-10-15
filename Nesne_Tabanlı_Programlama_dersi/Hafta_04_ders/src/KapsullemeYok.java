/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 1: KAPSULLEME YOK - motivasyon problemi
 *
 * Hafta 1-3'te alanlari sadelik adina "public" biraktik. Bu ornek, o kararin
 * bedelini somut olarak gosterir: sinif kendi verisini koruyamaz, bir nesne
 * ANLAMSIZ bir duruma dusebilir ve hatanin nerede olustugu belli olmaz.
 */
public class KapsullemeYok {

    public static void main(String[] args) {
        AcikOgrenci o = new AcikOgrenci();
        o.ad = "Elif Kaya";
        o.yas = 20;
        o.notOrtalamasi = 78.5;
        o.yaz();

        System.out.println();
        System.out.println("--- Farkli modullerden gelen atamalar ---");

        // (a) Kayit modulu: yasi yanlislikla eksi yaziyor
        o.yas = -3;
        // (b) Sinav modulu: 100 uzeri not yaziyor
        o.notOrtalamasi = 350.0;
        // (c) Arayuz modulu: adi bosaltiyor
        o.ad = "";

        o.yaz();

        System.out.println();
        System.out.println("Nesne gecersiz durumda; derleyici hic sikayet etmedi.");
        System.out.println("Hatanin kaynagi bu satirlar degil, alanlarin acik olmasidir.");
    }
}

/* Ayni dosyadaki paket-ozel yardimci sinif (public degil). */
class AcikOgrenci {
    public String ad;
    public int yas;
    public double notOrtalamasi;

    public void yaz() {
        System.out.printf("ad='%s' | yas=%d | ortalama=%.1f%n", ad, yas, notOrtalamasi);
    }
}
