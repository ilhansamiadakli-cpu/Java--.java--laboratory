import java.util.Locale;

public final class Koordinat {
    public static final Koordinat BASLANGIC = new Koordinat(0.0, 0.0);

    private final double x;
    private final double y;

    public Koordinat(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Koordinat otele(double dx, double dy) {
        return new Koordinat(this.x + dx, this.y + dy);
    }

    public Koordinat olcekle(double k) {
        return new Koordinat(this.x * k, this.y * k);
    }

    public double uzaklik(Koordinat digeri) {
        double dx = this.x - digeri.x;
        double dy = this.y - digeri.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double baslangictanUzaklik() {
        return uzaklik(BASLANGIC);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%.2f, %.2f)", this.x, this.y);
    }
}