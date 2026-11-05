// "AkademikDergi bir Dergidir" -> IS-A -> extends
public class AkademikDergi extends Dergi {
    public static final int AKADEMIK_GUN = 3;

    private final String indeks;

    public AkademikDergi(String baslik, int yil, int sayiNo, String periyot, String indeks) {
        super(baslik, yil, sayiNo, periyot);
        if (indeks == null || indeks.isBlank()) {
            throw new IllegalArgumentException("Indeks bos olamaz");
        }
        this.indeks = indeks;
    }

    @Override
    protected int odunctenGunSayisi() {
        return AKADEMIK_GUN;
    }

    @Override
    public boolean oduncVer(String kime) {
        if (kime == null || (!kime.startsWith("Dr.") && !kime.startsWith("Prof."))) {
            System.out.println("RET: " + demirbasNo() + " yalnizca akademik personele verilir (" + kime + ")");
            return false;
        }
        
        return super.oduncVer(kime);
    }

    @Override
    public String kunye() {
        return super.kunye() + " | indeks: " + indeks;
    }

    public String getIndeks() { return indeks; }
}
