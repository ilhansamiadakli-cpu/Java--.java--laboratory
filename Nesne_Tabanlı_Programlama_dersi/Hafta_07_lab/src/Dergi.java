// "Dergi bir Yayindir" -> IS-A -> extends
public class Dergi extends Yayin {
    public static final int DERGI_GUN = 7;

    private final int sayiNo;
    private final String periyot;

    // Alt sinif AkademikDergi'nin erisebilmesi icin protected yapildi
    protected boolean guncelSayi;

    public Dergi(String baslik, int yil, int sayiNo, String periyot) {
        super(baslik, yil);
        if (sayiNo <= 0) {
            throw new IllegalArgumentException("Sayi numarasi pozitif olmalidir");
        }
        if (periyot == null || periyot.isBlank()) {
            throw new IllegalArgumentException("Periyot bos olamaz");
        }

        this.sayiNo = sayiNo;
        this.periyot = periyot;
        this.guncelSayi = true; 
    }

    public void arsiveAl() {
        this.guncelSayi = false;
        System.out.println(demirbasNo() + " arsive alindi, artik odunc verilebilir.");
    }

    @Override
    public boolean oduncVerilebilirMi() {
        return super.oduncVerilebilirMi() && !guncelSayi;
    }

    @Override
    protected int odunctenGunSayisi() {
        return DERGI_GUN;
    }

    @Override
    public boolean oduncVer(String kime) {
        if (guncelSayi) {
            System.out.println("RET: " + demirbasNo() + " guncel sayi, yalnizca icerde okunur.");
            return false;
        }
        return super.oduncVer(kime);
    }

    @Override
    public String kunye() {
        String durum = guncelSayi ? "GUNCEL" : "arsiv";
        return super.kunye() + String.format(" | sayi %d, %s, %s", sayiNo, periyot, durum);
    }

    public int getSayiNo() { return sayiNo; }
    public String getPeriyot() { return periyot; }
    public boolean isGuncelSayi() { return guncelSayi; }
}
