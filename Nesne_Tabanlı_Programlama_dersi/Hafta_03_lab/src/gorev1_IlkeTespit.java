/*
 * Nesne Yonelimli Programlama - Hafta 03 / Laboratuvar
 * Gorev 1: Verilen kodda hangi ilke kullanilmis? (ilke tespiti)
 *
 * Asagidaki dort bolgenin her biri dort temel ilkeden BIRINI ornekler.
 * Ogrenci once foydeki tabloyu doldurur, sonra bu programi calistirip
 * kendi yanitiyla karsilastirir.
 */
public class gorev1_IlkeTespit {

    public static void main(String[] args) {
        System.out.println("=== ILKE TESPITI ===");

        // --- BOLGE A ---
        Karakter k = new Karakter("Gezgin", 100);
        k.hasarAl(30);
        k.hasarAl(-10);                       // reddedilir
        System.out.println("A) can = " + k.getCan() + "  (dogrudan yazilamaz)");

        // --- BOLGE B ---
        Savasabilir s = new Savasci("Kaan", 120, 25);
        System.out.println("B) sozlesme uzerinden: " + s.saldiriGucu()
                           + " (sinif adi kullanilmadi)");

        // --- BOLGE C ---
        Savasci sv = new Savasci("Kaan", 120, 25);
        sv.hasarAl(20);                       // metot Karakter'den GELDI
        System.out.println("C) Savasci hasarAl() yazmadi, can = " + sv.getCan());

        // --- BOLGE D ---
        Karakter[] takim = {
            new Karakter("Gezgin", 100),
            new Savasci("Kaan", 120, 25),
            new Buyucu("Elif", 80, 35)
        };
        System.out.println("D) tek dongu, uc farkli davranis:");
        for (Karakter x : takim) {
            System.out.println("   " + x.tanit());
        }
    }
}

/** Temel karakter: kapsulleme ornegi. */
class Karakter {
    private final String ad;
    private int can;

    Karakter(String ad, int can) {
        this.ad = ad;
        this.can = (can > 0) ? can : 1;
    }

    String getAd() {
        return ad;
    }

    int getCan() {
        return can;
    }

    void hasarAl(int miktar) {
        if (miktar <= 0) {
            System.out.println("   Uyari: hasar pozitif olmalidir; islem yapilmadi.");
            return;
        }
        can = Math.max(0, can - miktar);
    }

    String tanit() {
        return String.format("%-8s can=%3d  tur=Karakter", ad, can);
    }
}

/** Soyutlama: "savasabilen her sey" sozlesmesi. */
interface Savasabilir {
    int saldiriGucu();
}

class Savasci extends Karakter implements Savasabilir {
    private final int kilic;

    Savasci(String ad, int can, int kilic) {
        super(ad, can);
        this.kilic = kilic;
    }

    @Override
    public int saldiriGucu() {
        return kilic * 2;
    }

    @Override
    String tanit() {
        return String.format("%-8s can=%3d  tur=Savasci   guc=%d",
                             getAd(), getCan(), saldiriGucu());
    }
}

class Buyucu extends Karakter implements Savasabilir {
    private final int mana;

    Buyucu(String ad, int can, int mana) {
        super(ad, can);
        this.mana = mana;
    }

    @Override
    public int saldiriGucu() {
        return mana + 10;
    }

    @Override
    String tanit() {
        return String.format("%-8s can=%3d  tur=Buyucu    guc=%d",
                             getAd(), getCan(), saldiriGucu());
    }
}