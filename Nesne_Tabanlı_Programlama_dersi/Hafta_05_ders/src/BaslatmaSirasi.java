/*
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 05 - Yapici ve Yikici Yontemler
 * Ders ornegi 3
 *
 * Amac: Bir nesne dogarken kodun HANGI SIRAYLA calistigini gostermek.
 *
 * Gozlenen sinif (Sunucu) main'den AYRI tutulmustur; boylece sinifin
 * ne zaman YUKLENDIGI de gozlenebilir.
 *
 * Sira:
 *   A) Sinif ilk kez kullanildiginda (bir kez):
 *        1. static alan baslaticilari + static bloklar (yazim sirasiyla)
 *   B) Her new cagrisinda:
 *        2. ornek alan baslaticilari + ornek bloklari (yazim sirasiyla)
 *        3. yapici govdesi
 *   this(...) varsa once zincirin ucundaki yapici tamamlanir.
 */
public class BaslatmaSirasi {

    public static void main(String[] args) {
        System.out.println("main basladi. Sunucu sinifi henuz KULLANILMADI.");
        System.out.println();

        System.out.println("--- 1. new: once SINIF yuklenir, sonra NESNE kurulur ---");
        Sunucu s1 = new Sunucu("web-01", 8080);
        System.out.println("  Sonuc: " + s1);

        System.out.println();
        System.out.println("--- 2. new: static bolum ARTIK calismaz ---");
        Sunucu s2 = new Sunucu("db-01");
        System.out.println("  Sonuc: " + s2);

        System.out.println();
        System.out.println("--- 3. new: this(...) zinciri iki adim ---");
        Sunucu s3 = new Sunucu();
        System.out.println("  Sonuc: " + s3);

        System.out.println();
        System.out.println("Uretilen sunucu sayisi (static sayac): " + Sunucu.uretilenSayisi());
        System.out.println("static bolum kac kez calisti? 1");
    }
}

class Sunucu {

    /* --- A) SINIF DUZEYI: yalnizca bir kez --- */
    static int uretilen = izle("[S1] static alan baslaticisi (uretilen)", 0);

    static {
        izle("[S2] static blok", 0);
    }

    /* --- B) NESNE DUZEYI: her new icin --- */
    int baglanti = izle("[O1] ornek alan baslaticisi (baglanti)", 0);

    {
        izle("[O2] ornek baslatma blogu", 0);
    }

    boolean calisiyor = izleB("[O3] ornek alan baslaticisi (calisiyor)");

    private final String ad;
    private final int port;

    /* --- Yapici 1: parametresiz --- */
    Sunucu() {
        this("yedek");
        izle("[Y3] parametresiz yapici govdesi (this'ten SONRA)", 0);
    }

    /* --- Yapici 2: tek parametreli --- */
    Sunucu(String ad) {
        this(ad, 80);
        izle("[Y2] tek parametreli yapici govdesi (this'ten SONRA)", 0);
    }

    /* --- Yapici 3: ANA yapici --- */
    Sunucu(String ad, int port) {
        izle("[Y1] ANA yapici govdesi", 0);
        this.ad = ad;
        this.port = port;
        uretilen++;
    }

    private static int izle(String mesaj, int deger) {
        System.out.println("      " + mesaj);
        return deger;
    }

    private static boolean izleB(String mesaj) {
        System.out.println("      " + mesaj);
        return true;
    }

    static int uretilenSayisi() {
        return uretilen;
    }

    @Override
    public String toString() {
        return "Sunucu[" + ad + ":" + port + ", baglanti=" + baglanti
                + ", calisiyor=" + calisiyor + "]";
    }
}
