/*
 * Nesne Yonelimli Programlama - Hafta 09
 * Ders Ornegi 9: Odeme yontemleri hiyerarsisi.
 *
 * Amac: Hafta 03'te "soyutlama" ornegi olarak tanittigimiz Odeme yapisini
 *       bu hafta cok bicimlilikle CALISIR hale getirmek. Kasa, hangi odeme
 *       yontemi geldigini bilmeden komisyon ve toplam hesaplayabiliyor.
 */
public class Odeme {

    private final String aciklama;
    protected final double tutar;

    public Odeme(String aciklama, double tutar) {
        this.aciklama = aciklama;
        this.tutar = tutar;
    }

    public String getAciklama() {
        return aciklama;
    }

    public double getTutar() {
        return tutar;
    }

    public double komisyon() {
        return 0.0;
    }

    public String onayMesaji() {
        return "Odeme alindi.";
    }

    public double odenecek() {
        return tutar + komisyon();
    }

    @Override
    public String toString() {
        return String.format("%-14s tutar=%8.2f  komisyon=%6.2f  odenecek=%8.2f",
                             aciklama, tutar, komisyon(), odenecek());
    }
}

class KrediKarti extends Odeme {

    private final int taksit;

    KrediKarti(double tutar, int taksit) {
        super("Kredi karti", tutar);
        this.taksit = taksit;
    }

    @Override
    public double komisyon() {
        return tutar * 0.015 * taksit;      // her taksit icin binde 15
    }

    @Override
    public String onayMesaji() {
        return "Karttan " + taksit + " taksit cekildi.";
    }
}

class Havale extends Odeme {

    Havale(double tutar) {
        super("Havale/EFT", tutar);
    }

    @Override
    public double komisyon() {
        return tutar > 5000 ? 0.0 : 12.50;  // buyuk tutarda komisyon yok
    }

    @Override
    public String onayMesaji() {
        return "Havale dekontu bekleniyor.";
    }
}

class KapidaOdeme extends Odeme {

    KapidaOdeme(double tutar) {
        super("Kapida odeme", tutar);
    }

    @Override
    public double komisyon() {
        return 29.90;                       // sabit hizmet bedeli
    }

    @Override
    public String onayMesaji() {
        return "Tutar kuryeye odenecek.";
    }
}
