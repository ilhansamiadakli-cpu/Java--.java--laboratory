public class Dergi extends Yayin {
    private final int sayiNo;

    public Dergi(String baslik, int yil, int sayiNo) {
        super(baslik, yil);
        this.sayiNo = sayiNo;
    }

    public int getSayiNo() {
        return sayiNo;
    }

    // degistirme: ust sinifin varsayilan tur adi "Dergi" ile degistiriliyor
    @Override
    public String tur() {
        return "Dergi";
    }

    // degistirme: dergi odunc suresi 3 gun olarak degistiriliyor
    @Override
    public int oduncGunu() {
        return 3;
    }

    // degistirme: gecikme cezasi gunluk 4.00 TL uzerinden hesaplanacak sekilde degistiriliyor
    @Override
    public double gecikmeCezasi(int gun) {
        int gecikme = gun - oduncGunu();
        return gecikme <= 0 ? 0.0 : gecikme * 4.00;
    }
}