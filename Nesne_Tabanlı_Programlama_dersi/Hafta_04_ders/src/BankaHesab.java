/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 8: Is kurali setter'da degil, ANLAMLI METOTTA
 *
 * setBakiye() yazmak kapsullemeyi bozar: cagiran taraf istedigi degeri
 * verebilirdi. Bunun yerine bakiyeyi degistiren iki ANLAMLI islem sunulur:
 * yatir() ve cek(). Kural (pozitif tutar, yeterli bakiye, gunluk limit)
 * bu metotlarin icindedir.
 */
public class BankaHesab {

    public static final double GUNLUK_CEKIM_LIMITI = 2000.0;

    private final String iban;
    private final String sahip;
    private double bakiye;
    private double bugunCekilen;

    public BankaHesab(String iban, String sahip, double acilis) {
        this.iban = iban;
        this.sahip = sahip;
        this.bakiye = (acilis > 0) ? acilis : 0.0;
        this.bugunCekilen = 0.0;
    }

    public String getIban() {
        return iban;
    }

    public String getSahip() {
        return sahip;
    }

    /* Yalnizca OKUMA. setBakiye() BILEREK yazilmamistir. */
    public double getBakiye() {
        return bakiye;
    }

    public double getKalanGunlukHak() {
        return GUNLUK_CEKIM_LIMITI - bugunCekilen;
    }

    public boolean yatir(double tutar) {
        if (tutar <= 0) {
            System.out.println("  RED: yatirilan tutar pozitif olmalidir.");
            return false;
        }
        bakiye += tutar;
        System.out.printf("  OK : %.2f yatirildi. Bakiye = %.2f%n", tutar, bakiye);
        return true;
    }

    public boolean cek(double tutar) {
        if (tutar <= 0) {
            System.out.println("  RED: cekilen tutar pozitif olmalidir.");
            return false;
        }
        if (tutar > bakiye) {
            System.out.printf("  RED: yetersiz bakiye (%.2f > %.2f).%n", tutar, bakiye);
            return false;
        }
        if (bugunCekilen + tutar > GUNLUK_CEKIM_LIMITI) {
            System.out.printf("  RED: gunluk limit asilir (%.2f + %.2f > %.2f).%n",
                    tutar, bugunCekilen, GUNLUK_CEKIM_LIMITI);
            return false;
        }
        bakiye -= tutar;
        bugunCekilen += tutar;
        System.out.printf("  OK : %.2f cekildi. Bakiye = %.2f%n", tutar, bakiye);
        return true;
    }

    @Override
    public String toString() {
        return String.format("Hesap[%s, %s, bakiye=%.2f]", iban, sahip, bakiye);
    }
}
