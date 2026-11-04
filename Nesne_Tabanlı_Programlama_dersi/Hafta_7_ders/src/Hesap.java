/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 7 : protected erisim ve genisletilmeye ACIK tasarim
 *
 * Amac : Hafta 4'te ogrendigimiz dort erisim duzeyinden henuz
 *        kullanmadigimiz "protected" duzeyini gerekcelendirmek.
 *        bakiye alani private kalirsa alt sinif ona dokunamaz;
 *        public olursa kapsulleme kirilir. protected tam ortadadir:
 *        SINIFIN KENDISI + ALT SINIFLARI + AYNI PAKET gorur.
 *
 * Tasarim notu : final metot (hesapNo()) ile ezilemez sozlesme,
 *        protected metot (kesintiOrani()) ile ezilmesi BEKLENEN
 *        uzatma noktasi ayni sinifta bir arada gosterilmistir.
 * ========================================================== */

public class Hesap {

    private final String hesapNo;          // disariya kapali, degismez
    protected double bakiye;               // alt siniflar dogrudan gorebilir
    protected final String sahip;

    private static int sonrakiNo = 1001;

    public Hesap(String sahip, double acilisBakiyesi) {
        if (acilisBakiyesi < 0) {
            throw new IllegalArgumentException("Acilis bakiyesi negatif olamaz.");
        }
        this.hesapNo = "TR" + sonrakiNo;
        sonrakiNo++;
        this.sahip   = sahip;
        this.bakiye  = acilisBakiyesi;
    }

    /** final : hesap numarasi bicimi hicbir alt sinifta degistirilemez. */
    public final String hesapNo() {
        return hesapNo;
    }

    public double getBakiye() {
        return bakiye;
    }

    public void yatir(double miktar) {
        if (miktar <= 0) {
            System.out.println("  UYARI: yatirilacak miktar pozitif olmalidir.");
            return;
        }
        bakiye += miktar;
    }

    /** Alt siniflar bu metodu ezerek ek kural koyabilir. */
    public boolean cek(double miktar) {
        if (miktar <= 0) {
            System.out.println("  UYARI: cekilecek miktar pozitif olmalidir.");
            return false;
        }
        if (miktar > bakiye) {
            System.out.println("  RET: yetersiz bakiye (" + bakiye + " < " + miktar + ")");
            return false;
        }
        bakiye -= miktar;
        return true;
    }

    /** Uzatma noktasi: alt siniflar yalnizca ORANI degistirir. */
    protected double kesintiOrani() {
        return 0.0;
    }

    public double aylikKesinti() {
        return bakiye * kesintiOrani();
    }

    public String ozet() {
        return hesapNo + " (" + sahip + ") bakiye=" + String.format("%.2f", bakiye);
    }

    @Override
    public String toString() {
        return "Hesap[" + hesapNo + "]";
    }
}
