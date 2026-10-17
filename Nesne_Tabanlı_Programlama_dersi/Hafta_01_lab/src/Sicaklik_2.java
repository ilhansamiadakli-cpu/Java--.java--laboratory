
public class Sicaklik_2 {
    public double celsius;

    public double fahrenheit(){
        return celsius * 9/5 + 32;
    }
    public double kelvin(){
        return celsius + 273.15;
    }
    public void isit(double artis) {
        this.celsius += artis;
    }
    public boolean donmaNoktasiAltindaMi(){
        if(celsius < 0){
            return true;
        }
        return false;
    }

    public void yaz(String etiket) {
    // Ekrana önce gönderilen etiketi, ardından kendi içindeki değerleri basar
    System.out.printf("%s -> Celsius: %.2f | Fahrenheit: %.2f | Kelvin: %.2f%n", etiket, this.celsius, fahrenheit(), kelvin());
    }
}
