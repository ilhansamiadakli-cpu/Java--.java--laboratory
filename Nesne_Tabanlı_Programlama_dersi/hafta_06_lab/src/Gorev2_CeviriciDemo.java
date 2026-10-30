import java.util.Locale;

public class Gorev2_CeviriciDemo {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        System.out.println("=== 1) Nesne URETMEDEN dogrudan cagri ===");
        System.out.printf(Locale.US, "100 km    = %.3f mil%n", BirimCevirici.kmToMil(100));
        System.out.printf(Locale.US, "68.2 mil  = %.3f km%n", BirimCevirici.milToKm(68.2));
        System.out.printf(Locale.US, "75 kg     = %.3f libre%n", BirimCevirici.kgToLibre(75));
        System.out.printf(Locale.US, "50 litre  = %.3f galon%n", BirimCevirici.litreToGalon(50));

        System.out.println("\n=== 2) Sicaklik donusum tablosu ===");
        System.out.printf("%8s %10s %10s%n", "Celsius", "Fahrenheit", "Kelvin");
        double[] sicakliklar = {-40.0, 0.0, 25.0, 37.0, 100.0};
        for (double c : sicakliklar) {
            System.out.printf(Locale.US, "%8.1f %10.1f %10.2f%n",
                    c,
                    BirimCevirici.celsiusToFahrenheit(c),
                    BirimCevirici.celsiusToKelvin(c));
        }

        System.out.println("\n=== 3) Sabitlere dogrudan erisim ===");
        System.out.printf(Locale.US, "KM_MIL         = %f%n", BirimCevirici.KM_MIL);
        System.out.printf(Locale.US, "KG_LIBRE       = %f%n", BirimCevirici.KG_LIBRE);
        System.out.printf(Locale.US, "MUTLAK_SIFIR_C = %.2f%n", BirimCevirici.MUTLAK_SIFIR_C);

        System.out.println("\n=== 4) Gecersiz girdiler reddediliyor ===");
        try {
            BirimCevirici.kmToMil(-5.0);
        } catch (IllegalArgumentException e) {
            System.out.println("RET: " + e.getMessage());
        }

        try {
            BirimCevirici.celsiusToKelvin(-300.0);
        } catch (IllegalArgumentException e) {
            System.out.println("RET: " + e.getMessage());
        }

        try {
            BirimCevirici.yuvarla(12.345, 9);
        } catch (IllegalArgumentException e) {
            System.out.println("RET: " + e.getMessage());
        }

        System.out.println("\n=== 5) Static sayac: kac basarili donusum yapildi ===");
        System.out.println("cagriSayisi = " + BirimCevirici.cagriSayisi());
        System.out.println("Ret edilen cagrilar sayaca ISLENMEDI: dogrulama");
        System.out.println("say() cagrisindan ONCE yapilmaktadir.");

        System.out.println("\n=== 6) Sinifin nesnesi uretilemez ===");
        System.out.println("new BirimCevirici(); // yazilirsa derleme hatasi:");
        System.out.println("The constructor BirimCevirici() is not visible");
    }
}