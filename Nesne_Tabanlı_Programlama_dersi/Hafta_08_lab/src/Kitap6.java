public class Kitap6 extends Yayin {
    private final String yazar;
    private final int sayfa;

    public Kitap6(String baslik, int yil, String yazar, int sayfa) {
        super(baslik, yil);
        this.yazar = yazar;
        this.sayfa = sayfa;
    }

    public String getYazar() {
        return yazar;
    }

    public int getSayfa() {
        return sayfa;
    }

    // degistirme: ust sinifin varsayilan tur adi "Kitap" ile degistiriliyor
    @Override
    public String tur() {
        return "Kitap";
    }

    // degistirme: odunc suresi sayfa sayisina gore 30 veya 21 gun olarak degistiriliyor
    @Override
    public int oduncGunu() {
        return sayfa > 600 ? 30 : 21;
    }

    // degistirme: gecikme cezasi gunluk 2.50 TL uzerinden hesaplanacak sekilde degistiriliyor
    @Override
    public double gecikmeCezasi(int gun) {
        int gecikme = gun - oduncGunu();
        return gecikme <= 0 ? 0.0 : gecikme * 2.50;
    }
}