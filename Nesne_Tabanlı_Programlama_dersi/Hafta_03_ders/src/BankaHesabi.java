/*
 * Nesne Yonelimli Programlama - Hafta 03
 * Ornek 5: KAPSULLEME ilkesinin saf hali
 *
 * Bakiye alani disaridan ne okunabilir sekilde yazilabilir ne de dogrudan
 * degistirilebilir. Para yalnizca IKI KAPIDAN girer ve cikar: yatir() ve cek().
 * Is kurallari (negatif tutar, yetersiz bakiye, gunluk limit) tek bir yerde.
 */
public class BankaHesabi {

    public static final double GUNLUK_CEKIM_LIMITI = 2000.0;

    private final String iban;          // dogumda belirlenir, degismez
    private final String sahip;
    private double bakiye;              // DISARIYA HIC ACILMAZ
    private double bugunCekilen;

    public BankaHesabi(String iban, String sahip, double acilisBakiyesi) {
        this.iban = iban;
        this.sahip = sahip;
        this.bakiye = (acilisBakiyesi > 0) ? acilisBakiyesi : 0.0;
        this.bugunCekilen = 0.0;
    }

    public String getIban() {
        return iban;
    }

    public String getSahip() {
        return sahip;
    }

    // Yalnizca OKUMA. setBakiye() BILEREK yazilmamistir.
    public double getBakiye() {
        return bakiye;
    }

    public boolean yatir(double tutar) {
        if (tutar <= 0) {
            System.out.println("  RED: yatirilan tutar pozitif olmalidir.");
            return false;
        }
        bakiye += tutar;
        return true;
    }

    public boolean cek(double tutar) {
        if (tutar <= 0) {
            System.out.println("  RED: cekilen tutar pozitif olmalidir.");
            return false;
        }
        if (tutar > bakiye) {
            System.out.printf("  RED: yetersiz bakiye (istenen %.2f, mevcut %.2f).%n",
                              tutar, bakiye);
            return false;
        }
        if (bugunCekilen + tutar > GUNLUK_CEKIM_LIMITI) {
            System.out.printf("  RED: gunluk limit asilir (%.2f + %.2f > %.2f).%n",
                              bugunCekilen, tutar, GUNLUK_CEKIM_LIMITI);
            return false;
        }
        bakiye -= tutar;
        bugunCekilen += tutar;
        return true;
    }

    public void gunuKapat() {
        bugunCekilen = 0.0;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) bakiye=%.2f TL", sahip, iban, bakiye);
    }
}
