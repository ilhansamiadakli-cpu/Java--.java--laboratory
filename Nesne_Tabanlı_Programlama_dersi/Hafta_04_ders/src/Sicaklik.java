/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 4: Dogrulamali setter ve turetilmis getter
 *
 * Fiziksel bir kural vardir: -273.15 C'nin altina inilemez. Bu kural sinifin
 * ICINDE tutulur; sinifi kullanan hicbir kod bu kurali tekrar yazmak zorunda
 * kalmaz ve unutamaz.
 */
public class Sicaklik {

    public static final double MUTLAK_SIFIR = -273.15;

    private double celsius;
    private double enDusuk;
    private double enYuksek;

    public Sicaklik(double celsius) {
        this.celsius = MUTLAK_SIFIR;
        this.enDusuk = Double.MAX_VALUE;
        this.enYuksek = -Double.MAX_VALUE;
        setCelsius(celsius);
    }

    public double getCelsius() {
        return celsius;
    }

    public void setCelsius(double yeni) {
        if (yeni < MUTLAK_SIFIR) {
            System.out.printf("  RED: %.2f C mutlak sifirin altinda; deger degismedi.%n",
                    yeni);
            return;
        }
        this.celsius = yeni;
        if (yeni < enDusuk) {
            enDusuk = yeni;
        }
        if (yeni > enYuksek) {
            enYuksek = yeni;
        }
    }

    /* Fahrenheit ve Kelvin icin ALAN YOKTUR; ihtiyac aninda hesaplanir.
       Boylece iki alanin birbiriyle celismesi imkansiz hale gelir. */
    public double getFahrenheit() {
        return celsius * 9.0 / 5.0 + 32.0;
    }

    public double getKelvin() {
        return celsius - MUTLAK_SIFIR;
    }

    public void setFahrenheit(double f) {
        setCelsius((f - 32.0) * 5.0 / 9.0);
    }

    public double getEnDusuk() {
        return enDusuk;
    }

    public double getEnYuksek() {
        return enYuksek;
    }

    @Override
    public String toString() {
        return String.format("%.2f C = %.2f F = %.2f K", celsius, getFahrenheit(),
                getKelvin());
    }
}
