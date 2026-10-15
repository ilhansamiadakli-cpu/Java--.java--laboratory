public class Gorev0_Kontrol {

    public static void main(String[] args) {
        System.out.println("Java surumu : " + System.getProperty("java.version"));
        System.out.println("Calisma dizini hazir.");

        Sayac sayac = new Sayac("lab04");
        sayac.artir();
        sayac.artir();
        sayac.artir();

        System.out.println(sayac);
        System.out.println("Deger yalnizca artir() ile degisti: " + sayac.getDeger());
        System.out.println("ORTAM HAZIR.");
    }

    @Override
    public String toString() {
        return "Gorev0_Kontrol[]";
    }
}

class Sayac {
    private final String etiket;
    private int deger;

    public Sayac(String etiket) {
        this.etiket = etiket;
        this.deger = 0;
    }

    public void artir() {
        this.deger++;
    }

    public String getEtiket() {
        return this.etiket;
    }

    public int getDeger() {
        return this.deger;
    }

    @Override
    public String toString() {
        return "Sayac[" + this.etiket + "=" + this.deger + "]";
    }
}