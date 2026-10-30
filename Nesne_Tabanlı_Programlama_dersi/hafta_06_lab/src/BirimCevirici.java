public final class BirimCevirici {

    
    public static final double KM_MIL = 0.621371;
    public static final double KG_LIBRE = 2.204623;
    public static final double LITRE_GALON = 0.264172;
    public static final double MUTLAK_SIFIR_C = -273.15;

    
    private static int cagriSayisi = 0;

    
    private BirimCevirici() {
        throw new AssertionError("Yardimci siniftan nesne uretilemez.");
    }

    private static void say() {
        cagriSayisi++;
    }

    public static double yuvarla(double deger, int basamak) {
        if (basamak < 0 || basamak > 8) {
            throw new IllegalArgumentException("Basamak 0-8 araliginda olmalidir.");
        }
        double carpan = Math.pow(10, basamak);
        return Math.round(deger * carpan) / carpan;
    }

    public static double kmToMil(double km) {
        if (km < 0) {
            throw new IllegalArgumentException("Mesafe negatif olamaz: " + km);
        }
        say();
        return yuvarla(km * KM_MIL, 3);
    }

    public static double milToKm(double mil) {
        if (mil < 0) {
            throw new IllegalArgumentException("Mesafe negatif olamaz: " + mil);
        }
        say();
        return yuvarla(mil / KM_MIL, 3);
    }

    public static double kgToLibre(double kg) {
        if (kg < 0) {
            throw new IllegalArgumentException("Kutle negatif olamaz: " + kg);
        }
        say();
        return yuvarla(kg * KG_LIBRE, 3);
    }

    public static double litreToGalon(double litre) {
        if (litre < 0) {
            throw new IllegalArgumentException("Hacim negatif olamaz: " + litre);
        }
        say();
        return yuvarla(litre * LITRE_GALON, 3);
    }

    public static double celsiusToFahrenheit(double c) {
        if (c < MUTLAK_SIFIR_C) {
            throw new IllegalArgumentException("Mutlak sifirin altinda sicaklik olamaz: " + c);
        }
        say();
        return (c * 9.0 / 5.0) + 32.0;
    }

    public static double celsiusToKelvin(double c) {
        if (c < MUTLAK_SIFIR_C) {
            throw new IllegalArgumentException("Mutlak sifirin altinda sicaklik olamaz: " + c);
        }
        say();
        return c - MUTLAK_SIFIR_C;
    }

    public static int cagriSayisi() {
        return cagriSayisi;
    }
}

/*

1. Yapiciyi private yapmasaydiniz derleyici ne yapardi? Bu neden bir sorun olurdu?
   YANIT: Derleyici parametresiz ve public bir varsayilan yapici (default constructor)
   eklerdi. Bu durum, hicbir ornek uyesi ve durumu olmayan yardimci siniftan bos yere
   "new BirimCevirici()" denilerek nesne uretilmesine izin verirdi; bu da tasarim hatasidir.

2. Sinifi final yapmanin gerekcesi nedir? private yapicili bir sinifin alt sinifi
   zaten yazilabilir mi?
   YANIT: private yapicisi olan bir sinifin alt sinifindaki yapici "super()" cagiramaz,
   dolayisiyla alt sinifi normal yollarla derlenemez. Ancak sinifi acikca "final"
   bildirmek niyeti hem derleyiciye hem de kodu okuyana kesin olarak bildirir,
   ic siniflar uzerinden kalitim yapilmasini da mutlak sekilde engeller.

3. cagriSayisi alani bir ornek alani olsaydi ne olurdu? Bu sinif baglaminda sorunun
   anlamli olup olmadigini tartisiniz.
   YANIT: Sınıftan nesne uretilemedigi icin (private yapici nedeniyle) cagriSayisi
   alanina hicbir zaman erisilemezdi. Butun metotlar static oldugundan bu metotlarin
   icinden de nesneye ait bir alana erisim yapilamaz (derleme hatasi verirdi).
   Bu sinif baglaminda anlamli tek sayac, sinif geneli olan "static" sayactir.

*/