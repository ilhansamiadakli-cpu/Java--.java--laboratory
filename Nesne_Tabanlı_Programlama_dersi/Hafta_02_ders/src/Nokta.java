/*
 * Nesne Yonelimli Programlama - Hafta 02
 * Ornek 4: Yapici asiri yukleme (constructor overloading)
 *
 * Ayni sinifta birden cok yapici bulunabilir; ayirt edici olan
 * PARAMETRE LISTESIDIR (sayi, tur, sira). Donus turu yoktur.
 */
public class Nokta {

    private double x;
    private double y;

    /** Parametresiz yapici: orijin noktasi. */
    public Nokta() {
        this(0.0, 0.0);          // tam yapiciyi cagirir
    }

    /** Tek parametreli: kosegen uzerindeki nokta (x = y). */
    public Nokta(double kosegen) {
        this(kosegen, kosegen);
    }

    /** Tam yapici: gercek isi yapan tek yapici budur. */
    public Nokta(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Kopya yapici. */
    public Nokta(Nokta digeri) {
        this(digeri.x, digeri.y);
    }

    public double orijineUzaklik() {
        return Math.sqrt(x * x + y * y);
    }

    public double uzaklik(Nokta digeri) {
        double dx = this.x - digeri.x;
        double dy = this.y - digeri.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void tasi(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}
