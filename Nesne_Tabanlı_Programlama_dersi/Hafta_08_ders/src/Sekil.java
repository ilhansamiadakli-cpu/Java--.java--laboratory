/*
 * Nesne Yonelimli Programlama - Hafta 09
 * Ders Ornegi 1: Cok bicimliligin tasiyicisi olan sekil hiyerarsisi.
 *
 * Amac: Ust sinif Sekil, ortak SOZLESMEyi tanimlar: her seklin bir alani,
 *       bir cevresi ve bir adi vardir. Alt siniflar bu sozlesmeyi kendi
 *       geometrilerine gore YENIDEN YAZAR (override). Cagiran taraf hangi
 *       alt sinifin geldigini bilmek zorunda kalmaz.
 *
 * NOT: Sekil sinifinin alan() govdesi burada 0.0 dondurur. Bu, dogru olmayan
 *      ama derlenebilen bir "varsayilan"dir. Hafta 10'da abstract sozcugunu
 *      ogrendigimizde bu govdeyi tamamen kaldiracagiz.
 */
public class Sekil {

    private final String ad;

    public Sekil(String ad) {
        this.ad = ad;
    }

    public String getAd() {
        return ad;
    }

    // Alt siniflarin EZMESI beklenen iki metot.
    public double alan() {
        return 0.0;
    }

    public double cevre() {
        return 0.0;
    }

    // Object'ten devralinan toString ezilir; her alt sinif bundan yararlanir.
    @Override
    public String toString() {
        return String.format("%-8s alan=%7.2f  cevre=%7.2f", ad, alan(), cevre());
    }
}

class Daire extends Sekil {

    private final double yaricap;

    Daire(double yaricap) {
        super("Daire");
        this.yaricap = yaricap;
    }

    @Override
    public double alan() {
        return Math.PI * yaricap * yaricap;
    }

    @Override
    public double cevre() {
        return 2 * Math.PI * yaricap;
    }
}

class Kare extends Sekil {

    private final double kenar;

    Kare(double kenar) {
        super("Kare");
        this.kenar = kenar;
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

class Ucgen extends Sekil {

    private final double a;
    private final double b;
    private final double c;

    Ucgen(double a, double b, double c) {
        super("Ucgen");
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double alan() {
        double u = cevre() / 2;                    // yari cevre
        return Math.sqrt(u * (u - a) * (u - b) * (u - c));   // Heron bagintisi
    }

    @Override
    public double cevre() {
        return a + b + c;
    }
}
