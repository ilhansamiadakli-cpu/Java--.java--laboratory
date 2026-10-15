public class KanBasinci {
    public static final int EN_DUSUK = 40;
    public static final int EN_YUKSEK = 250;

    private int buyuk;
    private int kucuk;

    public KanBasinci(int buyuk, int kucuk) {
        this.buyuk = 120;
        this.kucuk = 80;
        ayarla(buyuk, kucuk);
    }

    private boolean araliktaMi(int deger) {
        return deger >= EN_DUSUK && deger <= EN_YUKSEK;
    }

    public boolean ayarla(int yeniBuyuk, int yeniKucuk) {
        if (!araliktaMi(yeniBuyuk) || !araliktaMi(yeniKucuk)) {
            System.out.println("  RED: degerler " + EN_DUSUK + ".." + EN_YUKSEK + " araliginda olmalidir.");
            return false;
        }
        if (yeniBuyuk <= yeniKucuk) {
            System.out.println("  RED: buyuk tansiyon kucukten buyuk olmalidir ("
                    + yeniBuyuk + " <= " + yeniKucuk + ").");
            return false;
        }
        this.buyuk = yeniBuyuk;
        this.kucuk = yeniKucuk;
        return true;
    }

    public int getBuyuk() {
        return this.buyuk;
    }

    public int getKucuk() {
        return this.kucuk;
    }

    public int getNabizBasinci() {
        return this.buyuk - this.kucuk;
    }

    public String degerlendir() {
        if (this.buyuk < 120 && this.kucuk < 80) {
            return "NORMAL";
        } else if (this.buyuk < 130 && this.kucuk < 80) {
            return "YUKSEK-NORMAL";
        } else if (this.buyuk < 140 || this.kucuk < 90) {
            return "EVRE-1";
        } else {
            return "EVRE-2";
        }
    }

    @Override
    public String toString() {
        return this.buyuk + "/" + this.kucuk + " mmHg (" + degerlendir() + ")";
    }
}