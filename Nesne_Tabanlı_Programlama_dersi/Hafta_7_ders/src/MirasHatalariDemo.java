/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 12 : Mirasta sik yapilan uc hata
 *
 * Amac : Derleyicinin YAKALAMADIGI hatalari gostermek. Uc ornek de
 *        hatasiz derlenir; yalnizca calisma zamaninda yanlis sonuc
 *        uretir. Bu yuzden ezberden degil, gozlemden ogrenilmelidir.
 *
 *   (1) ALAN GOLGELEME (field hiding) : alt sinif ust siniftaki
 *       ayni adli alani yeniden bildirir; iki ayri alan olusur.
 *   (2) EZME YERINE ASIRI YUKLEME : imza degistigi icin metot
 *       ezilmez, yeni bir metot eklenir. @Override bunu yakalar.
 *   (3) YAPICIDAN EZILEBILIR METOT CAGIRMA : ust sinifin yapicisi
 *       alt sinifin ezdigi metodu cagirir; alt sinif alanlari
 *       HENUZ BASLATILMAMISTIR.
 * ========================================================== */

/* ---- (1) Alan golgeleme ---- */
class UstSayac {
    int deger = 10;
    int ustDegerOku() { return deger; }
}

class AltSayac extends UstSayac {
    int deger = 99;              // AYNI AD -> ust alan golgelenir, silinmez
    int altDegerOku() { return deger; }
    int ustuAcikOku() { return super.deger; }
}

/* ---- (2) Ezme yerine asiri yukleme ---- */
class Bicimleyici {
    String bicimle(Object o) { return "UST(Object): " + o; }
}

class SayiBicimleyici extends Bicimleyici {
    /* Parametre turu String; ust siniftaki imza Object.
     * Bu bir EZME degil, ASIRI YUKLEMEDIR. @Override yazilsa
     * derleyici "must override or implement a supertype method"
     * hatasi verirdi. */
    String bicimle(String s) { return "ALT(String): " + s; }
}

/* ---- (3) Yapicidan ezilebilir metot cagirma ---- */
class UstRapor {
    UstRapor() {
        System.out.println("    UstRapor yapicisi -> baslik(): " + baslik());
    }
    String baslik() { return "genel rapor"; }
}

class AltRapor extends UstRapor {
    private String konu = "donem sonu";      // super() BITTIKTEN sonra atanir

    AltRapor() {
        super();
        System.out.println("    AltRapor yapicisi -> baslik(): " + baslik());
    }

    @Override
    String baslik() {
        return "rapor: " + konu;             // ilk cagrida konu HENUZ null
    }
}

public class MirasHatalariDemo {

    public static void main(String[] args) {
        System.out.println("=== HATA 1 : alan golgeleme ===");
        AltSayac a = new AltSayac();
        System.out.println("  a.altDegerOku()  -> " + a.altDegerOku()
                           + "   (alt siniftaki deger)");
        System.out.println("  a.ustDegerOku()  -> " + a.ustDegerOku()
                           + "   (ust siniftaki deger, DEGISMEDI)");
        System.out.println("  a.ustuAcikOku()  -> " + a.ustuAcikOku()
                           + "   (super.deger)");
        UstSayac ustRef = a;                 // ayni nesne, farkli STATIK tur
        System.out.println("  ((UstSayac) a).deger -> " + ustRef.deger);
        System.out.println("  a.deger              -> " + a.deger);
        System.out.println("  Tek nesnede IKI ayri 'deger' alani vardir.");
        System.out.println("  Alanlara erisim STATIK TURE gore cozulur.");

        System.out.println();
        System.out.println("=== HATA 2 : ezme yerine asiri yukleme ===");
        SayiBicimleyici sb = new SayiBicimleyici();
        System.out.println("  sb.bicimle(\"42\")          -> " + sb.bicimle("42"));
        Bicimleyici ustB = sb;
        System.out.println("  ((Bicimleyici) sb).bicimle(\"42\") -> " + ustB.bicimle("42"));
        System.out.println("  Ayni nesne, ayni arguman, FARKLI sonuc.");
        System.out.println("  Metot ezilmedi; yeni bir metot eklendi.");
        System.out.println("  Cozum: her ezme denemesine @Override yaz.");

        System.out.println();
        System.out.println("=== HATA 3 : yapicidan ezilebilir metot cagirma ===");
        System.out.println("  new AltRapor() cagriliyor:");
        AltRapor r = new AltRapor();
        System.out.println("  nesne hazir  -> baslik(): " + r.baslik());
        System.out.println("  Ilk satirda konu alani null'di; cunku ust sinifin");
        System.out.println("  yapicisi, alt sinifin alanlari atanmadan once");
        System.out.println("  ezilmis metodu cagirdi.");
    }
}
