/*
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 05 - Yapici ve Yikici Yontemler
 * Ders ornegi 1
 *
 * Amac: Asiri yuklenmis yapicilar, this(...) zinciri ve kopya yapici.
 *       Tum yapicilar tek bir "ana yapici" uzerinde birlesir; dogrulama
 *       kurali yalnizca orada yazilir.
 */
public class Nokta {

    private final double x;
    private final double y;
    private final String etiket;

    /* --- 1. Parametresiz yapici: her seyi varsayilana birakir --- */
    public Nokta() {
        this(0.0, 0.0, "Baslangic");
        System.out.println("  [1] Parametresiz yapici govdesi calisti");
    }

    /* --- 2. Tek parametreli: kosegen uzerinde bir nokta --- */
    public Nokta(double kosegen) {
        this(kosegen, kosegen, "Kosegen");
        System.out.println("  [2] Tek parametreli yapici govdesi calisti");
    }

    /* --- 3. Iki parametreli: en sik kullanilan bicim --- */
    public Nokta(double x, double y) {
        this(x, y, "Adsiz");
        System.out.println("  [3] Iki parametreli yapici govdesi calisti");
    }

    /* --- 4. ANA YAPICI: dogrulama yalnizca burada --- */
    public Nokta(double x, double y, String etiket) {
        if (Double.isNaN(x) || Double.isNaN(y)) {
            throw new IllegalArgumentException("Koordinat NaN olamaz");
        }
        this.x = x;
        this.y = y;
        this.etiket = (etiket == null || etiket.isBlank()) ? "Adsiz" : etiket.trim();
        System.out.println("  [4] ANA yapici calisti -> " + this.etiket);
    }

    /* --- 5. KOPYA YAPICI: ayni turden bir nesneden yeni nesne uretir --- */
    public Nokta(Nokta kaynak) {
        this(kaynak.x, kaynak.y, kaynak.etiket + " (kopya)");
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public String getEtiket() { return etiket; }

    public double baslangictanUzaklik() {
        return Math.sqrt(x * x + y * y);
    }

    public Nokta otele(double dx, double dy) {
        return new Nokta(x + dx, y + dy, etiket);
    }

    @Override
    public String toString() {
        return String.format("%s(%.2f, %.2f)", etiket, x, y);
    }
}
