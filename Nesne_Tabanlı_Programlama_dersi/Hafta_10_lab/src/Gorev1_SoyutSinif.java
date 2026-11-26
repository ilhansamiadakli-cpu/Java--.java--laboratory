/*
 * Ders: Nesne Yonelimli Programlama
 * Hafta: 10
 * Gorev: Gorev 1 - Soyut Sinif Tasarimi
 * Amac: Soyut ust sinif ve ara soyut sinif kullanarak ortak davranislari tek yerde toplamak,
 *       ture ozgu hesaplamalari alt siniflara zorunlu kilmak.
 */

abstract class Arac {
    public static final double LITRE_FIYATI = 47.85;

    private final String plaka;
    protected final double kmDeger;

    protected Arac(String plaka, double kmDeger) {
        if (kmDeger < 0) {
            throw new IllegalArgumentException("Mesafe negatif olamaz.");
        }
        this.plaka = plaka;
        this.kmDeger = kmDeger;
    }

    public abstract double yuzKmTuketim();
    public abstract String tur();

    public double toplamYakit() {
        return kmDeger * yuzKmTuketim() / 100.0;
    }

    public double maliyet() {
        return toplamYakit() * LITRE_FIYATI;
    }

    public String getPlaka() {
        return plaka;
    }
}

class Otomobil extends Arac {
    private final boolean dizel;

    public Otomobil(String plaka, double kmDeger, boolean dizel) {
        super(plaka, kmDeger);
        this.dizel = dizel;
    }

    @Override
    public double yuzKmTuketim() {
        return dizel ? 5.4 : 7.2;
    }

    @Override
    public String tur() {
        return dizel ? "Otomobil-D" : "Otomobil-B";
    }
}

class Kamyon extends Arac {
    private final double yukTon;

    public Kamyon(String plaka, double kmDeger, double yukTon) {
        super(plaka, kmDeger);
        this.yukTon = yukTon;
    }

    @Override
    public double yuzKmTuketim() {
        return 18.0 + yukTon * 1.35;
    }

    @Override
    public String tur() {
        return "Kamyon";
    }
}

abstract class IkiTekerlekli extends Arac {
    protected IkiTekerlekli(String plaka, double kmDeger) {
        super(plaka, kmDeger);
    }

    @Override
    public String tur() {
        return "2T-" + altTur();
    }

    protected abstract String altTur();
}

class Motosiklet extends IkiTekerlekli {
    private final int motorHacmi;

    public Motosiklet(String plaka, double kmDeger, int motorHacmi) {
        super(plaka, kmDeger);
        this.motorHacmi = motorHacmi;
    }

    @Override
    public double yuzKmTuketim() {
        return 2.2 + motorHacmi / 1000.0;
    }

    @Override
    protected String altTur() {
        return motorHacmi + "cc";
    }
}

public class Gorev1_SoyutSinif {
    public static Arac enCokYakan(Arac[] dizi) {
        if (dizi == null || dizi.length == 0) {
            return null;
        }

        Arac enCokYakan = dizi[0];
        for (int i = 1; i < dizi.length; i++) {
            if (dizi[i].toplamYakit() > enCokYakan.toplamYakit()) {
                enCokYakan = dizi[i];
            }
        }
        return enCokYakan;
    }

    public static void main(String[] args) {
        java.util.Locale.setDefault(java.util.Locale.US);

        Arac[] filo = {
            new Otomobil("61 AB 100", 420.0, true),
            new Otomobil("61 CD 220", 180.5, false),
            new Kamyon("61 EF 330", 1250.0, 12.0),
            new Motosiklet("61 GH 440", 96.0, 250)
        };

        double toplamLitre = 0.0;
        double toplamMaliyet = 0.0;

        System.out.println("=== FILO RAPORU ===");
        System.out.println("PLAKA     TUR                     MESAFE         YAKIT     MALIYET");
        for (Arac arac : filo) {
            System.out.printf("%-9s %-24s %7.1f km %8.2f L %9.2f TL%n",
                arac.getPlaka(), arac.tur(), arac.kmDeger,
                arac.toplamYakit(), arac.maliyet());
            toplamLitre += arac.toplamYakit();
            toplamMaliyet += arac.maliyet();
        }
        System.out.printf("TOPLAM                              %8.2f L %9.2f TL%n",
            toplamLitre, toplamMaliyet);

        System.out.println();
        System.out.println("=== SOYUTLUGUN KANITI ===");
        System.out.println("  // Arac a = new Arac(\"61 XX 000\", 100);");
        System.out.println("  //   -> Cannot instantiate the type Arac");
        System.out.println("  Baslangic durumunda bu satir DERLENIYORDU ve");
        System.out.println("  yakiti 0.00 L olan hayali bir arac uretiyordu.");
        
        System.out.println();
        System.out.println("=== EN COK YAKAN ARAC ===");
        Arac enCokYakan = enCokYakan(filo);
        System.out.printf("  %s (%s)%n", enCokYakan.getPlaka(), enCokYakan.tur());

        System.out.println();
        System.out.println("=== DOGRULAMA: negatif mesafe ===");
        try {
            new Otomobil("61 ZZ 999", -10.0, true);
        } catch (IllegalArgumentException e) {
            System.out.println("  Yakalandi -> " + e.getMessage());
        }
    }
}
// Soru 1: IkiTekerlekli sinifi abstract olmasaydi derleyici hangi iletiyi verirdi ve neden?
// Cevap 1: "IkiTekerlekli must implement inherited abstract method" benzeri bir hata verirdi; cunku Arac'tan gelen yuzKmTuketim() metodunun govdesini bilmedi ve somut sinif olarak soyut metodu uygulamasi gerekiyordu.
// Soru 2: Arac yapicisi protected yerine public olsaydi davranis degisir miydi ve protected secmenin gerekcesi nedir?
// Cevap 2: Davranis degismezdi; protected yapici, Arac'in yalnizca miras alan siniflardan olusturulmasini saglayarak soyut sinifin soyut tasarimini kontrollu tutar.
// Soru 3: Filoya elektrikli bir arac eklenirse toplamYakit() ve maliyet() metotlarinda kac satir degisirdi?
// Cevap 3: Hizmetci metotlarda degisiklik gerekmez; yalnizca yeni somut sinifin yuzKmTuketim() metodu yazilir. Bu tasarim acik-kapali ilkesine uygundur.