/*
 * Nesne Yonelimli Programlama - Hafta 09
 * Ders Ornegi 6: Hafta 07'deki Calisan hiyerarsisinin COK BICIMLI surumu.
 *
 * Hafta 07'de Muhendis ve Yonetici siniflarini Calisan'dan turetmis, fakat
 * maas hesabini her sinifta AYRI AYRI cagirmistik. Bu hafta maasHesapla()
 * metodunu ust sinifta sozlesme olarak tanimliyor, her alt sinifta eziyoruz.
 * Boylece bordro dongusu alt siniflarin adini hic bilmeden calisiyor.
 */
public class Calisan {

    private final String ad;
    private final String sicil;
    protected double tabanMaas;

    public Calisan(String ad, String sicil, double tabanMaas) {
        this.ad = ad;
        this.sicil = sicil;
        this.tabanMaas = tabanMaas;
    }

    public String getAd() {
        return ad;
    }

    public String getSicil() {
        return sicil;
    }

    // Sozlesme: her calisan turu kendi maasini kendi kuralina gore hesaplar.
    public double maasHesapla() {
        return tabanMaas;
    }

    public String unvan() {
        return "Calisan";
    }

    @Override
    public String toString() {
        return String.format("%-6s %-10s %-12s %10.2f TL",
                             sicil, ad, unvan(), maasHesapla());
    }
}

class Muhendis extends Calisan {

    private final int projeSayisi;

    Muhendis(String ad, String sicil, double tabanMaas, int projeSayisi) {
        super(ad, sicil, tabanMaas);
        this.projeSayisi = projeSayisi;
    }

    @Override
    public double maasHesapla() {
        return tabanMaas + projeSayisi * 2500.0;      // proje primi
    }

    @Override
    public String unvan() {
        return "Muhendis";
    }
}

class Yonetici extends Calisan {

    private final int ekipBuyuklugu;

    Yonetici(String ad, String sicil, double tabanMaas, int ekipBuyuklugu) {
        super(ad, sicil, tabanMaas);
        this.ekipBuyuklugu = ekipBuyuklugu;
    }

    @Override
    public double maasHesapla() {
        // super ile ust sinifin surumu cagrilabilir; uzerine ekleme yapilir.
        return super.maasHesapla() * 1.30 + ekipBuyuklugu * 1200.0;
    }

    @Override
    public String unvan() {
        return "Yonetici";
    }
}

class Stajyer extends Calisan {

    private final int gunSayisi;

    Stajyer(String ad, String sicil, int gunSayisi) {
        super(ad, sicil, 0.0);
        this.gunSayisi = gunSayisi;
    }

    @Override
    public double maasHesapla() {
        return gunSayisi * 420.0;                      // gunluk harclik
    }

    @Override
    public String unvan() {
        return "Stajyer";
    }
}
