public class DVD extends Yayin {
    private final int dakika;

    public DVD(String baslik, int yil, int dakika) {
        super(baslik, yil);
        this.dakika = dakika;
    }

    public int getDakika() {
        return dakika;
    }

    // degistirme: ust sinifin varsayilan tur adi "DVD" ile degistiriliyor
    @Override
    public String tur() {
        return "DVD";
    }

    // degistirme: DVD odunc suresi 2 gun olarak degistiriliyor
    @Override
    public int oduncGunu() {
        return 2;
    }

    // degistirme: gecikme cezasi gunluk 7.50 TL uzerinden hesaplanacak sekilde degistiriliyor
    @Override
    public double gecikmeCezasi(int gun) {
        int gecikme = gun - oduncGunu();
        return gecikme <= 0 ? 0.0 : gecikme * 7.50;
    }
}