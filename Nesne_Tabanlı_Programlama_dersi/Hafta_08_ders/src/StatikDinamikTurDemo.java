/*
 * Nesne Yonelimli Programlama - Hafta 09
 * Ders Ornegi 4: Statik tur - dinamik tur ayrimi.
 *
 * Amac: Bir referansin IKI turu vardir:
 *         statik tur  = degiskenin bildirildigi tur   (derleyici bunu gorur)
 *         dinamik tur = new ile uretilen gercek nesne (JVM bunu gorur)
 *       METOTLAR dinamik ture gore, ALANLAR statik ture gore cozulur.
 *       Bu asimetri, alan gizlemenin neden tehlikeli oldugunu aciklar.
 */
public class StatikDinamikTurDemo {

    public static void main(String[] args) {
        System.out.println("== 1. Metot cagrisi: DINAMIK tur kazanir ==");
        Arac a = new Otomobil();
        System.out.println("  statik tur : Arac");
        System.out.println("  dinamik tur: " + a.getClass().getSimpleName());
        System.out.println("  a.tanit()  : " + a.tanit());

        System.out.println();
        System.out.println("== 2. Alan erisimi: STATIK tur kazanir ==");
        Otomobil o = new Otomobil();
        Arac ustReferans = o;                 // ayni nesne, farkli statik tur
        System.out.println("  o.etiket           = " + o.etiket);
        System.out.println("  ustReferans.etiket = " + ustReferans.etiket);
        System.out.println("  Iki satir AYNI nesneyi gosteriyor ama farkli deger dondu.");
        System.out.println("  Sebep: alanlar ezilmez, GIZLENIR (field hiding).");

        System.out.println();
        System.out.println("== 3. Derleyici neyi gorur? ==");
        // ustReferans.kornaCal();  <-- DERLENMEZ: Arac'ta boyle bir metot yok.
        System.out.println("  ustReferans.kornaCal() satiri DERLENMEZ.");
        System.out.println("  Cunku derleyici yalnizca STATIK turu (Arac) bilir.");
        System.out.println("  o.kornaCal() : " + o.kornaCal());

        System.out.println();
        System.out.println("== 4. Statik metotlar cok bicimli DEGILDIR ==");
        System.out.println("  Arac.sinifAdi()     : " + Arac.sinifAdi());
        System.out.println("  Otomobil.sinifAdi() : " + Otomobil.sinifAdi());
        Arac gizli = new Otomobil();
        System.out.println("  gizli.sinifAdi()    : " + gizli.sinifAdi()
                           + "   <-- statik ture gore secildi");
    }
}

class Arac {
    String etiket = "ARAC";

    String tanit() {
        return "Ben genel bir aracim.";
    }

    static String sinifAdi() {
        return "Arac (statik)";
    }
}

class Otomobil extends Arac {
    String etiket = "OTOMOBIL";      // ezmez, GIZLER

    @Override
    String tanit() {                 // gercek ezme
        return "Ben bir otomobilim.";
    }

    static String sinifAdi() {       // gizleme, ezme degil
        return "Otomobil (statik)";
    }

    String kornaCal() {
        return "Diit!";
    }
}
