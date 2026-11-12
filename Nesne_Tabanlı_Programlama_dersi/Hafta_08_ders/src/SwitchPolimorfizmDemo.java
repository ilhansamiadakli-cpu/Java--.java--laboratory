/*
 * Nesne Yonelimli Programlama - Hafta 09
 * Ders Ornegi 10: switch yiginindan cok bicimlilige gecis.
 *
 * Amac: Ayni isi yapan IKI kasa surumunu yan yana calistirmak:
 *         eskiKasa() -> tur koduna bakan switch (Hafta 03'teki tip kodu sorunu)
 *         yeniKasa() -> cok bicimli cagri
 *       Sonuclar ayni; bakim maliyeti degil.
 */
public class SwitchPolimorfizmDemo {

    // ---------- ESKI YOL: tur kodu + switch ----------
    static final int TUR_KART = 1;
    static final int TUR_HAVALE = 2;
    static final int TUR_KAPIDA = 3;

    static double eskiKomisyon(int tur, double tutar, int taksit) {
        switch (tur) {
            case TUR_KART:
                return tutar * 0.015 * taksit;
            case TUR_HAVALE:
                return tutar > 5000 ? 0.0 : 12.50;
            case TUR_KAPIDA:
                return 29.90;
            default:
                return 0.0;
        }
    }

    static String eskiOnay(int tur, int taksit) {
        switch (tur) {                       // AYNI switch, ikinci kez
            case TUR_KART:
                return "Karttan " + taksit + " taksit cekildi.";
            case TUR_HAVALE:
                return "Havale dekontu bekleniyor.";
            case TUR_KAPIDA:
                return "Tutar kuryeye odenecek.";
            default:
                return "Odeme alindi.";
        }
    }

    // ---------- YENI YOL: cok bicimlilik ----------
    static double yeniToplam(Odeme[] odemeler) {
        double toplam = 0;
        for (Odeme o : odemeler) {
            toplam += o.odenecek();          // switch yok
        }
        return toplam;
    }

    public static void main(String[] args) {
        System.out.println("== ESKI YOL: switch yigini ==");
        double[] tutarlar = { 1200.0, 8000.0, 350.0 };
        int[] turler = { TUR_KART, TUR_HAVALE, TUR_KAPIDA };
        int[] taksitler = { 6, 0, 0 };
        double eskiToplam = 0;
        for (int i = 0; i < turler.length; i++) {
            double k = eskiKomisyon(turler[i], tutarlar[i], taksitler[i]);
            eskiToplam += tutarlar[i] + k;
            System.out.printf("  tur=%d  komisyon=%6.2f  %s%n",
                              turler[i], k, eskiOnay(turler[i], taksitler[i]));
        }
        System.out.printf("  ESKI TOPLAM : %.2f%n", eskiToplam);

        System.out.println();
        System.out.println("== YENI YOL: cok bicimlilik ==");
        Odeme[] odemeler = {
            new KrediKarti(1200.0, 6),
            new Havale(8000.0),
            new KapidaOdeme(350.0)
        };
        for (Odeme o : odemeler) {
            System.out.println("  " + o);
            System.out.println("      " + o.onayMesaji());
        }
        System.out.printf("  YENI TOPLAM : %.2f%n", yeniToplam(odemeler));

        System.out.println();
        System.out.println("== Karsilastirma ==");
        System.out.printf("  Iki toplam esit mi? %s%n",
                          Math.abs(eskiToplam - yeniToplam(odemeler)) < 0.001);
        System.out.println("  Sonuc ayni. Fark: yeni bir odeme turu eklendiginde");
        System.out.println("  eski yolda IKI switch de duzeltilmeliydi;");
        System.out.println("  yeni yolda tek bir sinif eklemek yeterli.");

        System.out.println();
        System.out.println("== Yeni tur ekleme sinavi ==");
        Odeme[] genisletilmis = {
            new KrediKarti(1200.0, 6),
            new Havale(8000.0),
            new KapidaOdeme(350.0),
            new Odeme("Nakit", 500.0)        // hicbir metodu degistirmeden
        };
        System.out.printf("  Genisletilmis toplam : %.2f%n", yeniToplam(genisletilmis));
        System.out.println("  yeniToplam metodunda TEK satir degismedi.");
    }
}
