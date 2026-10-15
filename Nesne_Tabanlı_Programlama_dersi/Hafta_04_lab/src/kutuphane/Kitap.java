package kutuphane;

public class Kitap {
    private final String isbn;
    private final String baslik;
    private final String yazar;
    boolean oduncte;

    public Kitap(String isbn, String baslik, String yazar) {
        this.isbn = isbn;
        this.baslik = baslik;
        this.yazar = yazar;
        this.oduncte = false;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getBaslik() {
        return this.baslik;
    }

    public String getYazar() {
        return this.yazar;
    }

    public boolean isOduncte() {
        return this.oduncte;
    }

    @Override
    public String toString() {
        return this.isbn + " - " + this.baslik + " (" + this.yazar + ") ["
                + (this.oduncte ? "ODUNCTE" : "RAFTA") + "]";
    }
}