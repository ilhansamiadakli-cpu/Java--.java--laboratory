/*
 * Nesne Yonelimli Programlama - Hafta 02
 * Ornek 5: Asiri yuklenmis yapicilarin kullanimi
 */
public class NoktaDemo {

    public static void main(String[] args) {
        Nokta orijin = new Nokta();            // parametresiz
        Nokta kosegen = new Nokta(3.0);        // tek parametreli
        Nokta serbest = new Nokta(4.0, 3.0);   // iki parametreli
        Nokta kopya = new Nokta(serbest);      // kopya yapici

        System.out.println("orijin  = " + orijin);
        System.out.println("kosegen = " + kosegen);
        System.out.println("serbest = " + serbest);
        System.out.println("kopya   = " + kopya);

        System.out.printf("%nserbest'in orijine uzakligi: %.2f%n",
                serbest.orijineUzaklik());
        System.out.printf("kosegen ile serbest arasi   : %.2f%n",
                kosegen.uzaklik(serbest));

        // Kopya gercekten bagimsiz mi?
        kopya.tasi(10, 10);
        System.out.println("\n--- kopya.tasi(10, 10) sonrasi ---");
        System.out.println("serbest = " + serbest);   // degismedi
        System.out.println("kopya   = " + kopya);
        System.out.println("serbest == kopya -> " + (serbest == kopya));
    }
}
