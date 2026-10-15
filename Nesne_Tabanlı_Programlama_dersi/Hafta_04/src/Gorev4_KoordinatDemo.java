import java.util.Locale;

public class Gorev4_KoordinatDemo {

    private static void bozmayaCalis(Koordinat k) {
        k = k.otele(100.0, 100.0);
        System.out.println("  metot icinde uretilen: " + k);
    }

    public static void main(String[] args) {
        Koordinat p = new Koordinat(3.0, 4.0);
        System.out.println("p                 : " + p);
        System.out.printf(Locale.US, "baslangica uzaklik: %.2f%n", p.baslangictanUzaklik());

        System.out.println();
        System.out.println("--- Donusumler YENI nesne uretir ---");
        Koordinat q = p.otele(2.0, -1.0);
        Koordinat r = q.olcekle(3.0);
        System.out.println("q = p.otele(2,-1) : " + q);
        System.out.println("r = q.olcekle(3)  : " + r);
        System.out.println("p (hala)          : " + p + "  <- DEGISMEDI");
        System.out.println("q (hala)          : " + q + "  <- DEGISMEDI");

        System.out.println();
        System.out.println("--- Zincirleme kullanim ---");
        Koordinat z = new Koordinat(1, 1).otele(1, 2).olcekle(2).otele(-1, 0);
        System.out.println("z                 : " + z);
        System.out.printf(Locale.US, "uzaklik(p, z)     : %.4f%n", p.uzaklik(z));

        System.out.println();
        System.out.println("--- Paylasim guvenli ---");
        bozmayaCalis(Koordinat.BASLANGIC);
        System.out.println("BASLANGIC (hala)  : " + Koordinat.BASLANGIC);
    }

    @Override
    public String toString() {
        return "Gorev4_KoordinatDemo[]";
    }
}