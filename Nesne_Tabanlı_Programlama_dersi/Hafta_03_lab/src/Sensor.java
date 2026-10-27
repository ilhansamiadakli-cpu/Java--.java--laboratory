public class Sensor {
    private final String kod;            // herkes degistirebilir
    private final String birim;
    private double deger;          // hicbir aralik denetimi yok
    private int olcumSayisi = 0;       // disaridan istenen degere ayarlanabilir
    private int redSayisi = 0;

    public static final double EN_DUSUK = -50.0;
    public static final double EN_YUKSEK = 150.00;

    public Sensor(String kod, String birim) {
        this.kod = kod;
        this.birim = birim;
    }
    public boolean olc(double deger){
        this.deger = deger;
        if (deger < EN_DUSUK || deger > EN_YUKSEK) {
            this.redSayisi++;
            System.out.println("RED " + this.kod + ": " + deger + " " + this.birim + " araligin disinda [" + EN_DUSUK + ", " + EN_YUKSEK + "]");
            return false;
        }
        this.olcumSayisi++;
        return true;
    }
    public String getKod() { return this.kod; }
    public String getBirim() { return this.birim; }
    public double getDeger() { return this.deger; }
    public int getOlcumSayisi() { return this.olcumSayisi; }
    public int getRedSayisi() { return this.redSayisi; }


    @Override
    public String toString() {
        return String.format(java.util.Locale.US, "%s %.1f %s (kabul=%d, red=%d)",
                this.kod, this.deger, this.birim, this.olcumSayisi, this.redSayisi);
    }
   }