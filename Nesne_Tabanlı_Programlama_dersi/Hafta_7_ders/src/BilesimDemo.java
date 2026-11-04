/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 10 : IS-A / HAS-A ayrimi - kalitim mi bilesim mi?
 *
 * Amac : Mirasin en sik kotuye kullanildigi noktayi gostermek.
 *        "extends yazinca ust sinifin metotlarini bedava aliyorum"
 *        dusuncesi, ARABA BIR MOTORDUR gibi anlamsiz bir tur
 *        iliskisi kurar. Dogru soru "X bir Y midir?" sorusudur;
 *        yanit hayirsa aranan sey BILESIM (composition) yani
 *        "X'in bir Y'si vardir" iliskisidir.
 *
 * Icerik : (1) yanlis tasarim: KotuAraba extends Motor
 *          (2) dogru tasarim  : Araba HAS-A Motor
 *          (3) dogru kalitim  : ElektrikliAraba IS-A Araba
 * ========================================================== */

class Motor {
    private final int gucHp;
    private boolean cizisiyor;

    Motor(int gucHp) { this.gucHp = gucHp; }

    int gucHp() { return gucHp; }

    void calistir() {
        cizisiyor = true;
        System.out.println("    Motor calisti (" + gucHp + " HP)");
    }

    void durdur() {
        cizisiyor = false;
        System.out.println("    Motor durdu");
    }

    boolean cizisiyorMu() { return cizisiyor; }

    /* Motor'un ic ayrintisi: disariya sizmasi istenmez. */
    void yakitPompasiTest() {
        System.out.println("    [servis] yakit pompasi testi");
    }
}

/* YANLIS TASARIM -------------------------------------------------
 * "Arabanin motoru var" demek yerine "araba bir motordur" dedik.
 * Sonuc: yakitPompasiTest() gibi SERVIS ici bir ayrinti arabanin
 * genel arayuzune sizdi ve araba iki motorlu olamaz hale geldi. */
class KotuAraba extends Motor {
    private final String plaka;

    KotuAraba(String plaka, int gucHp) {
        super(gucHp);
        this.plaka = plaka;
    }

    String plaka() { return plaka; }
}

/* DOGRU TASARIM -------------------------------------------------
 * Araba, Motor'u bir ALAN olarak tutar (HAS-A). Motor'un hangi
 * yeteneklerinin disariya acilacagini Araba secer (delegasyon). */
class Araba {
    private final String plaka;
    private final Motor motor;          // HAS-A
    protected int kmSayaci;

    Araba(String plaka, Motor motor) {
        this.plaka = plaka;
        this.motor = motor;
    }

    String plaka()   { return plaka; }
    int gucHp()      { return motor.gucHp(); }        // delegasyon

    void kontagiAc() {
        System.out.println("  " + plaka + ": kontak acildi");
        motor.calistir();                              // delegasyon
    }

    void kontagiKapat() {
        motor.durdur();
        System.out.println("  " + plaka + ": kontak kapandi");
    }

    void surus(int km) {
        if (!motor.cizisiyorMu()) {
            System.out.println("  " + plaka + ": motor kapali, surulemez");
            return;
        }
        kmSayaci += km;
        System.out.println("  " + plaka + ": " + km + " km surudu (toplam "
                           + kmSayaci + " km)");
    }

    String bilgi() {
        return plaka + " | " + gucHp() + " HP | " + kmSayaci + " km";
    }
}

/* DOGRU KALITIM ------------------------------------------------
 * "Elektrikli araba BIR arabadir" cumlesi anlamlidir; IS-A gecerli. */
class ElektrikliAraba extends Araba {
    private int sarjYuzdesi;

    ElektrikliAraba(String plaka, Motor motor, int sarjYuzdesi) {
        super(plaka, motor);
        this.sarjYuzdesi = Math.min(100, Math.max(0, sarjYuzdesi));
    }

    @Override
    void surus(int km) {
        int gereken = Math.max(1, km / 5);
        if (sarjYuzdesi < gereken) {
            System.out.println("  " + plaka() + ": sarj yetersiz ("
                               + sarjYuzdesi + "%)");
            return;
        }
        super.surus(km);                 // ust sinifin isini tekrar yazmiyoruz
        sarjYuzdesi -= gereken;
        System.out.println("  " + plaka() + ": kalan sarj " + sarjYuzdesi + "%");
    }

    @Override
    String bilgi() {
        return super.bilgi() + " | sarj " + sarjYuzdesi + "%";
    }
}

public class BilesimDemo {

    public static void main(String[] args) {
        System.out.println("=== 1) YANLIS: KotuAraba extends Motor ===");
        KotuAraba kotu = new KotuAraba("61 AB 001", 130);
        kotu.calistir();
        System.out.println("  Sorun: asagidaki iki satir DERLENIYOR ve calisiyor.");
        kotu.yakitPompasiTest();          // servis ayrintisi disariya sizdi
        System.out.println("  kotu instanceof Motor -> " + (kotu instanceof Motor)
                           + "   ('Araba bir motordur' anlamsizdir)");
        System.out.println("  Ayrica bu araba IKI motorlu olamaz;");
        System.out.println("  cunku motoru bir ALAN degil, KENDISIDIR.");

        System.out.println();
        System.out.println("=== 2) DOGRU: Araba HAS-A Motor ===");
        Araba a = new Araba("61 CD 002", new Motor(130));
        a.kontagiAc();
        a.surus(120);
        a.kontagiKapat();
        a.surus(50);
        System.out.println("  " + a.bilgi());
        System.out.println("  Motor'un yakitPompasiTest() metodu ARABA'nin");
        System.out.println("  arayuzunde YOKTUR; Araba neyi acacagini secti.");

        System.out.println();
        System.out.println("=== 3) DOGRU KALITIM: ElektrikliAraba IS-A Araba ===");
        ElektrikliAraba e = new ElektrikliAraba("61 EF 003", new Motor(210), 30);
        e.kontagiAc();
        e.surus(100);
        e.surus(400);
        System.out.println("  " + e.bilgi());

        System.out.println();
        System.out.println("=== 4) Karar kurali ===");
        String[][] sorular = {
            {"Ogrenci",         "Kisi",   "IS-A", "extends"},
            {"Yonetici",        "Personel", "IS-A", "extends"},
            {"Araba",           "Motor",  "HAS-A", "alan (bilesim)"},
            {"Kutuphane",       "Kitap",  "HAS-A", "alan/liste"},
            {"VadeliHesap",     "Hesap",  "IS-A", "extends"},
            {"Ogrenci",         "Adres",  "HAS-A", "alan (bilesim)"},
        };
        System.out.printf("  %-14s %-10s %-6s %s%n", "X", "Y", "Iliski", "Karsiligi");
        for (String[] r : sorular) {
            System.out.printf("  %-14s %-10s %-6s %s%n", r[0], r[1], r[2], r[3]);
        }
    }
}
