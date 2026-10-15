/*
 * Nesne Yonelimli Programlama - Hafta 03
 * Ornek 3b: Sekil'in ikinci alt sinifi
 */
public class Kare extends Sekil {

    private final double kenar;

    public Kare(double kenar, String renk) {
        super("Kare", renk);
        if (kenar <= 0) {
            System.out.println("Uyari: kenar pozitif olmalidir; 1.0 kullanildi.");
            this.kenar = 1.0;
        } else {
            this.kenar = kenar;
        }
    }

    public double getKenar() {
        return kenar;
    }

    @Override
    public double alan() {
        return kenar * kenar;
    }

    @Override
    public double cevre() {
        return 4 * kenar;
    }
}
