// "Kitap5 bir Yayindir" -> IS-A -> extends
public class Kitap5 extends Yayin {
    public static final int KITAP_GUN = 21;

    private final String yazar;
    private final int sayfaSayisi;
    private final String isbn;

    public Kitap5(String baslik, int yil, String yazar, int sayfaSayisi, String isbn) {
        super(baslik, yil);

        if (yazar == null || yazar.isBlank()) {
            throw new IllegalArgumentException("Yazar bos olamaz");
        }
        if (sayfaSayisi <= 0) {
            throw new IllegalArgumentException("Sayfa sayisi pozitif olmalidir");
        }
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN bos olamaz");
        }

        this.yazar = yazar;
        this.sayfaSayisi = sayfaSayisi;
        this.isbn = isbn;
    }

    public boolean kalinMi() {
        return sayfaSayisi >= 500;
    }

    @Override
    protected int odunctenGunSayisi() {
        return KITAP_GUN;
    }

    @Override
    public String kunye() {
        return super.kunye() + String.format(" | %s, %d s., ISBN %s", yazar, sayfaSayisi, isbn);
    }
}
