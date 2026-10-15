/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 5: Sicaklik sinifinin kullanimi
 */
public class SicaklikDemo {

    public static void main(String[] args) {
        Sicaklik s = new Sicaklik(21.5);
        System.out.println("Baslangic     : " + s);

        System.out.println();
        System.out.println("--- Gecersiz deger ---");
        s.setCelsius(-300.0);
        System.out.println("Deger degismedi: " + s);

        System.out.println();
        System.out.println("--- Fahrenheit uzerinden yazma ---");
        s.setFahrenheit(212.0);
        System.out.println("Suyun kaynamasi: " + s);

        s.setCelsius(-40.0);
        System.out.println("Soguk gun      : " + s);

        System.out.println();
        System.out.printf("Gorulen en dusuk : %.2f C%n", s.getEnDusuk());
        System.out.printf("Gorulen en yuksek: %.2f C%n", s.getEnYuksek());
        System.out.println("Not: Fahrenheit ve Kelvin icin ALAN yok; her ikisi de");
        System.out.println("     celsius alanindan turetildigi icin celisemezler.");
    }
}
