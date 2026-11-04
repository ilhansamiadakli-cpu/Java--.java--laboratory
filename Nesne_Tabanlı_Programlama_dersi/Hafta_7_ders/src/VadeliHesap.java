/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 8 : Ust sinif kuralini KISITLAYARAK genisletmek
 *
 * Amac : Alt sinifin ust sinif davranisini iki bicimde
 *        degistirebildigini gostermek:
 *          (a) super.cek(...) cagirip ONUNE ek kural koymak,
 *          (b) protected uzatma noktasini (kesintiOrani) ezmek.
 *
 * Uyari : Ezilen metot ust siniftaki sozlesmeyi BOZMAMALIDIR.
 *         Burada cek() hala "basarili mi" bilgisi dondurur ve
 *         basarisizlikta bakiyeyi degistirmez.
 * ========================================================== */

public class VadeliHesap extends Hesap {

    private final int vadeGunu;
    private int gecenGun;
    private static final double YILLIK_FAIZ = 0.42;

    public VadeliHesap(String sahip, double acilisBakiyesi, int vadeGunu) {
        super(sahip, acilisBakiyesi);
        if (vadeGunu < 32) {
            throw new IllegalArgumentException("Vade en az 32 gun olmalidir.");
        }
        this.vadeGunu = vadeGunu;
        this.gecenGun = 0;
    }

    public int getVadeGunu() { return vadeGunu; }
    public int getGecenGun() { return gecenGun; }

    public boolean vadeDoldumu() {
        return gecenGun >= vadeGunu;
    }

    public void gunIlerlet(int gun) {
        if (gun <= 0) {
            System.out.println("  UYARI: gun pozitif olmalidir.");
            return;
        }
        gecenGun += gun;
    }

    /** Vade dolmadan cekim yapilamaz: ust sinif kuralina EK kisit. */
    @Override
    public boolean cek(double miktar) {
        if (!vadeDoldumu()) {
            System.out.println("  RET: vade dolmadi (" + gecenGun + "/" + vadeGunu + " gun)");
            return false;
        }
        return super.cek(miktar);      // temel kontroller ust sinifta kalir
    }

    /** Uzatma noktasi eziliyor; aylikKesinti() metodu YENIDEN YAZILMIYOR. */
    @Override
    protected double kesintiOrani() {
        return 0.005;
    }

    /** VadeliHesap'a ozgu davranis: faiz isletme. */
    public double faizIsle() {
        double faiz = bakiye * YILLIK_FAIZ * gecenGun / 365.0;
        bakiye += faiz;                // protected alana DOGRUDAN erisim
        return faiz;
    }

    @Override
    public String ozet() {
        return super.ozet() + " [vadeli " + vadeGunu + " gun, gecen "
               + gecenGun + " gun]";
    }

    @Override
    public String toString() {
        return "VadeliHesap[" + hesapNo() + "]";
    }
}
