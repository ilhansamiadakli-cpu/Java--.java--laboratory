public class Yayin {
    public static final int VARSAYILAN_GUN = 15;
    private static int sonrakiSira = 1;

    private final String demirbasNo;
    private final String baslik;
    private final int yil;

    // Alt siniflarin kendi ozel kurallarinda duruma erisebilmesi icin protected yapildi
    protected String oduncAlan;
    protected boolean oduncte;

    public Yayin(String baslik, int yil) {
        if (baslik == null || baslik.isBlank()) {
            throw new IllegalArgumentException("Baslik bos olamaz");
        }
        if (yil <= 0) {
            throw new IllegalArgumentException("Yil 0 veya negatif olamaz");
        }

        this.baslik = baslik;
        this.yil = yil;
        this.demirbasNo = String.format("D-%04d", sonrakiSira++);
        this.oduncte = false;
        this.oduncAlan = null;
    }

    // Uzatma noktasi: Alt siniflar yalnizca bu metodu ezerek sureyi degistirir
    protected int odunctenGunSayisi() {
        return VARSAYILAN_GUN;
    }

    public boolean oduncVerilebilirMi() {
        return !oduncte;
    }

    public boolean oduncVer(String kime) {
        if (oduncte) {
            System.out.println("RET: " + demirbasNo + " zaten " + oduncAlan + " tarafindan alinmis.");
            return false;
        }
        this.oduncte = true;
        this.oduncAlan = kime;
        System.out.println("OK: " + demirbasNo + " -> " + kime + " (" + odunctenGunSayisi() + " gun)");
        return true;
    }

    public boolean iadeAl() {
        if (!oduncte) {
            System.out.println("RET: " + demirbasNo + " zaten rafta.");
            return false;
        }
        System.out.println("OK: " + demirbasNo + " iade alindi (" + oduncAlan + ")");
        this.oduncte = false;
        this.oduncAlan = null;
        return true;
    }

    public String kunye() {
        return demirbasNo + " | " + baslik + " (" + yil + ")";
    }

    // Numara bicimi alt siniflarca degistirilemez
    public final String demirbasNo() {
        return demirbasNo;
    }

    public int getYil() {
        return yil;
    }

    public boolean oduncteMi() {
        return oduncte;
    }
}
