/*
 * Nesne Yonelimli Programlama - Hafta 03
 * Ornek 8: Ayni sozlesme, uc farkli gerceklemesi
 *
 * Kasa sinifi hicbir odeme turunu ADIYLA bilmez; yalnizca Odeme sozlesmesini
 * bilir. Yeni bir odeme yontemi eklendiginde Kasa DEGISMEZ.
 */
public class OdemeDemo {

    public static void main(String[] args) {
        Odeme[] yontemler = {
            new KrediKarti("5312 **** **** 4471", 1200.0),
            new Havale("TR33 0001 2222", 600.0),
            new Kapora(250.0)
        };

        double[] sepet = {350.0, 500.0, 300.0};

        System.out.println("--- Kasa: tek dongu, uc farkli odeme yolu ---");
        for (int i = 0; i < yontemler.length; i++) {
            Kasa.tahsilEt(yontemler[i], sepet[i]);
        }

        System.out.println();
        System.out.println("--- Sozlesmeye uymayan tutar ---");
        Kasa.tahsilEt(yontemler[0], -50.0);

        System.out.println();
        System.out.println("Kasa sinifi KrediKarti, Havale, Kapora adlarini hic gecirmez.");
    }
}

/** Odeme sozlesmesini bilen; gerceklemeleri bilmeyen cagiran taraf. */
class Kasa {
    static void tahsilEt(Odeme yontem, double tutar) {
        boolean sonuc = yontem.ode(tutar);
        System.out.printf("  %-14s %8.2f TL -> %s%n",
                          yontem.saglayici(), tutar, sonuc ? "ONAY" : "RED");
    }
}

class KrediKarti implements Odeme {
    private final String numara;
    private double limit;

    KrediKarti(String numara, double limit) {
        this.numara = numara;
        this.limit = limit;
    }

    @Override
    public boolean ode(double tutar) {
        if (tutar <= 0 || tutar > limit) {
            return false;
        }
        limit -= tutar;
        return true;
    }

    @Override
    public String saglayici() {
        return "Kredi karti";
    }
}

class Havale implements Odeme {
    private final String iban;
    private double bakiye;

    Havale(String iban, double bakiye) {
        this.iban = iban;
        this.bakiye = bakiye;
    }

    @Override
    public boolean ode(double tutar) {
        if (tutar <= 0 || tutar > bakiye) {
            return false;
        }
        bakiye -= tutar;
        return true;
    }

    @Override
    public String saglayici() {
        return "Havale";
    }
}

class Kapora implements Odeme {
    private double kalan;

    Kapora(double kalan) {
        this.kalan = kalan;
    }

    @Override
    public boolean ode(double tutar) {
        if (tutar <= 0 || tutar > kalan) {
            return false;
        }
        kalan -= tutar;
        return true;
    }

    @Override
    public String saglayici() {
        return "Kapora";
    }
}
