public class Yayin {
    private final String baslik;
    private final int yil;

    public Yayin(String baslik, int yil) {
        this.baslik = baslik;
        this.yil = yil;
    }

    public String getBaslik() {
        return baslik;
    }

    public int getYil() {
        return yil;
    }

    public String tur() {
        return "Yayin";
    }

    public int oduncGunu() {
        return 7;
    }

    public double gecikmeCezasi(int gun) {
        int gecikme = gun - oduncGunu();
        return gecikme <= 0 ? 0.0 : gecikme * 1.00;
    }

    // degistirme: Object.toString() davranisi ortak Yayin bicimiyle degistiriliyor
    @Override
    public String toString() {
        return String.format("%-5s | %-12s | %d | odunc %2d gun", tur(), baslik, yil, oduncGunu());
    }

    // degistirme: Object.equals(Object) referans esitligi yerine baslik ve yil deger esitligiyle degistiriliyor
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Yayin diger = (Yayin) o;
        return yil == diger.yil && baslik.equals(diger.baslik);
    }

    // degistirme: Object.hashCode() equals ile ayni alanlardan (baslik ve yil) uretilecek sekilde degistiriliyor
    @Override
    public int hashCode() {
        return 31 * baslik.hashCode() + yil;
    }
}