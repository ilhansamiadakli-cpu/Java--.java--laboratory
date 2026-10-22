import java.time.LocalDate;

public class Kitap3 {
    private final String isbn;
    private String baslik;
    private String yazar;
    private int sayfaSayisi;
    private int basimYili;

    private static final int buYil = LocalDate.now().getYear();
    public static final  int EN_AZ_SAYFA = 1;
    public static final  int EN_COK_SAYFA = 5000;
    public static final String BILINMIYOR = "anonim";

    public Kitap3(String isbn, String baslik, String yazar, int sayfaSayisi, int basimYili){

        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("ISBN bos");
        if (baslik == null || baslik.isBlank()) throw new IllegalArgumentException("Baslik bos");
        if(sayfaSayisi > EN_COK_SAYFA || sayfaSayisi < EN_AZ_SAYFA) throw new IllegalArgumentException("sayfa sayısı geçerli değil max 5000 min 1");
        if(buYil - basimYili < 0) throw new IllegalArgumentException("Hata: lütfen geçerli bir basım yılını giriniz");

        this.isbn = isbn;
        this.baslik=baslik;
        this.yazar = (yazar == null || yazar.isBlank()) ? BILINMIYOR : yazar;
        this.sayfaSayisi = sayfaSayisi;
        this.basimYili = basimYili;
    }
    public Kitap3(String isbn, String baslik){
        this(isbn,baslik,BILINMIYOR);
    }
    public Kitap3(String isbn,String baslik, String yazar){
        this(isbn,baslik,yazar,100);
    }
    public Kitap3(String isbn,String baslik,String yazar,int  sayfa){
        this(isbn,baslik,yazar,sayfa,2026);
    }
    public Kitap3(Kitap3 kaynak){
        this(kaynak.isbn,kaynak.baslik,kaynak.yazar,kaynak.sayfaSayisi,kaynak.basimYili);
    }
    public void setBaslik(String baslik){
        this.baslik = baslik;
    }

    public int getYas(){return buYil - this.basimYili;}
    public String getIsbn() { return this.isbn; }
    public String getBaslik() { return this.baslik; }
    public String getYazar() { return this.yazar; }
    public int getSayfaSayisi() { return this.sayfaSayisi; }
    public int getBasimYili() { return this.basimYili; }
    @Override
    public String toString() {
        return String.format("Kitap [%s, \"%s\", %s, %d s., %d]",
                isbn, baslik, yazar, sayfaSayisi, basimYili);
    }
}
/*
? Verilen sorunlu sürümde üçüncü yapıcıda hangi üç denetim unutulmuştu? Bu unutmanın yapısal nedeni nedir?
!CEVAP: Başlık, sayfa sayısı ve basım yılı denetimi yapılmamış bu da yapıcının içine mantıken saçma bir değer girildiği zaman çıktı da mantıksal hatalara sebebp olur
? Sürücü programda new Kitap("978-975-05", "Derleyiciler", "M. Ak", 0, 2020) çağrısı hangi yapıcıdan ret aldı? Neden?
!CEVAP: "sayfa sayısı geçerli değil max 5000 min 1" hatası alır çünkü sayfa sayısı max 5000 ve min 1 aralığında olması gerekli
? Varsayılan yazar değerini "Bilinmiyor" yerine "Anonim" yapmak isteseniz kaç satır değiştirirdiniz? Sorunlu sürümde kaç satır değiştirmek gerekirdi?
!CEVAP: maximum 1 satır o da 13. satırdaki "public static final String BILINMIYOR" değişkenine "anonim" değeri atamak yeterlidir


*/
