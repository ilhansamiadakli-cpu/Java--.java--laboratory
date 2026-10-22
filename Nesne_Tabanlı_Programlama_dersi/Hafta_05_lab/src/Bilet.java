public class Bilet {
    public static final double TAM_UCRET = 400.0;
    public static final double YASLI_ORANI = 0.30;
    public static final double OGRENCI_ORANI = 0.50;

    private static int sonrakiSira = 0;

    private final String biletNo;
    private final String kalkis;
    private final String varis;
    private final String  tur;
    private final double ucret;

    private Bilet(String kalkis,String varis,String tur,double ucret){
        this.biletNo = String.format("B-%04d", sonrakiSira++);
        this.kalkis = kalkis;
        this.varis = varis;
        this.tur = tur;
        this.ucret = ucret;
    }
    public static Bilet tam(String kalkis, String varis){
        Bilet k1 = new Bilet(kalkis, varis, "TAM", TAM_UCRET);
        
        return k1;
    }

    public static Bilet ogrenci(String kalkis,String  varis){
        Bilet k1 = new Bilet(kalkis, varis, "OGRENCI", TAM_UCRET * (1 - OGRENCI_ORANI));
        
        return k1;
    }
    public static Bilet yasli(String kalkis,String varis){
        Bilet k1 = new Bilet(kalkis, varis, "YASLI", TAM_UCRET * (1 - YASLI_ORANI));
        return k1;
    } 
    public static  Bilet yasaGore(String kalkis,String varis,int  yas){
        if(yas < 0){
            throw new IllegalArgumentException("Gecersiz yas: " + yas);
        }
        else if(yas >= 18 && yas <= 25){
            return ogrenci(kalkis, varis);
        }
        else if(yas >= 65){
            return yasli(kalkis, varis);
        }
        return tam(kalkis, varis);
    }
    public String getBiletNo() { return this.biletNo; }
    public String getKalkis() { return this.kalkis; }
    public String getVaris() { return this.varis; }
    public String getTur() { return this.tur; }
    public double getUcret() { return this.ucret; }

    public static int uretilenBiletSayisi(){
        return sonrakiSira;
    }
    @Override
    public String toString() {
        return String.format(java.util.Locale.US, "Bilet [%s, %s->%s, %s, %.2f TL]",
                this.biletNo, this.kalkis, this.varis, this.tur, this.ucret);
    }
}
