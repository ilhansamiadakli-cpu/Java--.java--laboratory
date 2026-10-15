/*
 * Nesne Yonelimli Programlama - Hafta 03
 * Ornek 7: SOYUTLAMA icin arayuz (interface)
 *
 * Arayuz, bir NE sozlesmesidir: "odeme yapilabilen her sey su iki metoda
 * sahiptir". NASIL yapildigi arayuzde yer almaz. Ayrintilar Hafta 10'da.
 */
public interface Odeme {

    /** Verilen tutari tahsil etmeye calisir; basarili ise true doner. */
    boolean ode(double tutar);

    /** Odeme saglayicisinin gorunen adi. */
    String saglayici();
}
