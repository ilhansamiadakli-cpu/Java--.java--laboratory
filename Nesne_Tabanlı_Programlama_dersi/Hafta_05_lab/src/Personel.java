import java.util.Locale;

public class Personel {
    private final String sicil;
    private String ad;
    private double maas;
    private String[] gorevler;
    private int gorevAdedi;

    
    public Personel(String sicil, String ad, double maas) {
        if (sicil == null || sicil.isBlank()) {
            throw new IllegalArgumentException("RED: sicil bos olamaz");
        }
        if (ad == null || ad.isBlank()) {
            throw new IllegalArgumentException("RED: ad bos olamaz");
        }
        if (maas < 0) {
            throw new IllegalArgumentException("RED: maas negatif olamaz");
        }

        this.sicil = sicil;
        this.ad = ad;
        this.maas = maas;
        this.gorevler = new String[8];
        this.gorevAdedi = 0;
    }

    
    public Personel(String sicil, String ad) {
        this(sicil, ad, 30000.0);
    }

    public Personel(Personel kaynak, boolean sigKopya) {
        // İlk deyim kuralı: Ana yapıcı çağrılır
        this(kaynak.sicil, kaynak.ad, kaynak.maas);
        this.gorevAdedi = kaynak.gorevAdedi;

        if (sigKopya) {
            // Sığ kopya: Bellekteki aynı dizi referansı paylaşılır
            this.gorevler = kaynak.gorevler;
        } else {
            // Derin kopya: Bağımsız yeni bir dizi klonlanır
            this.gorevler = kaynak.gorevler.clone();
        }
    }

   
    public Personel(Personel kaynak) {
        this(kaynak, false);
    }

    public void setAd(String ad) {
        if (ad == null || ad.isBlank()) {
            System.out.println("RED: ad bos olamaz");
            return;
        }
        this.ad = ad;
    }

    public void zamYap(double yuzde) {
        if (yuzde < 0 || yuzde > 100) {
            System.out.println("RED: zam yuzdesi 0-100 araliginda olmali");
            return;
        }
        this.maas += this.maas * (yuzde / 100.0);
    }

    public void gorevEkle(String gorev) {
        if (gorev == null || gorev.isBlank()) {
            System.out.println("RED: gorev adi bos olamaz");
            return;
        }
        if (this.gorevAdedi >= 8) {
            System.out.println("RED: gorev listesi dolu");
            return;
        }
        this.gorevler[this.gorevAdedi] = gorev;
        this.gorevAdedi++;
    }

    
    public void gorevDegistir(int no, String yeniGorev) {
        if (no < 0 || no >= this.gorevAdedi) {
            System.out.println("RED: gecersiz gorev sirasi");
            return;
        }
        if (yeniGorev == null || yeniGorev.isBlank()) {
            System.out.println("RED: gorev adi bos olamaz");
            return;
        }
        this.gorevler[no] = yeniGorev;
    }

    
    public String gorevleriYaz() {
        if (this.gorevAdedi == 0) {
            return "(gorev yok)";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.gorevAdedi; i++) {
            sb.append(this.gorevler[i]);
            if (i < this.gorevAdedi - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public String getSicil() { return this.sicil; }
    public String getAd() { return this.ad; }
    public double getMaas() { return this.maas; }
    public int getGorevAdedi() { return this.gorevAdedi; }

    @Override
    public String toString() {
        return String.format(Locale.US, "Personel [%s, %s, %.2f TL, gorev={%s}]",
                this.sicil, this.ad, this.maas, gorevleriYaz());
    }
}
/*
? Sığ kopyada sig.gorevDegistir(0, "UZAKTAN teori dersi") çağrısı neden kaynağın görev listesini de değiştirdi? Bellekte kaç dizi var?
!CEVAP: Sığ kopyada (sigKopya == true) yeni bir dizi oluşturulmamış, yalnızca kaynak nesnenin dizi referansı kopyalanmıştır (this.gorevler = kaynak.gorevler). Bu nedenle iki personel nesnesi de yığında (heap) aynı tek diziyi işaret eder. Bellekte yalnızca 1 adet dizi vardır; bu dizi üzerinden yapılan herhangi bir değişiklik her iki nesneye de yansır.

? ad alanı String türündedir ve kopya yapıcıda clone() çağrılmamıştır. Bu bir hata mıdır? Yanıtınızı gerekçelendirin
!CEVAP: Hayır, bir hata değildir. Java'da String sınıfı değişmezdir (immutable). Bir nesnenin adı değiştirilmek istendiğinde var olan String metni güncellenmez; bellekte yeni bir String nesnesi oluşturulup referansı atanır. Dolayısıyla aynı String referansının paylaşılması yan etki veya veri bozulması riski oluşturmaz, güvenlidir.

? maas alanı için sığ/derin ayrımı neden anlamsızdır?
!CEVAP: maas alanı bir nesne/referans türü değil, ilkel (primitive - double) bir veri türüdür. İlkel türler Java'da her zaman doğrudan değer kopyalaması (pass-by-value) ile kopyalanır ve referans tutmazlar. Dolayısıyla her nesne kendi maas değerini bağımsız olarak saklar; sığ veya derin kopyalama kavramı yalnızca dizi ve nesne gibi referans türleri için geçerlidir.
*/