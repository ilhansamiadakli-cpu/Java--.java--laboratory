/*
 * Nesne Yonelimli Programlama - Hafta 02
 * Ornek 1a: Hafta 1'deki sinif (yapici metot YOK)
 *
 * Bu sinif, gecen hafta yazdigimiz surumdur. Nesne olusturulduktan
 * sonra alanlar tek tek atanmak zorundadir. Bir atama unutulursa
 * nesne GECERSIZ DURUMDA dogar ve bunu hicbir sey engellemez.
 */
public class OgrenciEski {

    public String ad;
    public String soyad;
    public int numara;
    public double vizeNotu;
    public double finalNotu;

    public double ortalamaHesapla() {
        return vizeNotu * 0.4 + finalNotu * 0.6;
    }

    public void bilgiYaz() {
        System.out.printf("%-8d %-8s %-8s  ort: %6.2f%n",
                numara, ad, soyad, ortalamaHesapla());
    }
}
