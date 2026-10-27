public class Kisi {

    private final String ad;
    private final String tcNo;
    private final int dogumYili;

    public Kisi(String ad,String tcNo,int dogumYili){
        if(ad == null ||  ad.isBlank()){ 
            this.ad = "isimsiz";
        }
        else{
            this.ad = ad;
        }
        if(tcNo == null ||tcNo.isBlank() || tcNo.length() != 11) {
            this.tcNo = "00000000000";
        }
        else{
            this.tcNo = tcNo;
        }

        this.dogumYili = dogumYili;


    }
    public String maskeliTc(){
        return this.tcNo.substring(0, 3) + "*****" + this.tcNo.substring(8);
    }
    public int yas(){
        return 2026 - this.dogumYili;
    }

    public String getAd() {
        return this.ad;
    }

    public String getTcNo() {
        return this.tcNo;
    }

    public int getDogumYili() {
        return this.dogumYili;
    }

    public String tanit() {
        return String.format("%-14s %s yas=%d [Kisi]", 
                this.ad, maskeliTc(), yas());
    }
}
class Ogrenci extends Kisi {
    private final int numara;
    private double ortalama;
    
    public Ogrenci(String ad,String tcNo,int dogumYili, int numara,double ortalama){
        super(ad,tcNo,dogumYili);
        this.numara = numara;
        setOrtalama(ortalama);
    }
    public void setOrtalama(double ortalama){
        if(ortalama < 0.0 || ortalama > 4.0){
            System.out.println("Uyari: ortalama 0.0-4.0 araliginda olmalidir; 0.0 kullanildi.");
            this.ortalama = 0.0;
            return;
        }
        this.ortalama = ortalama;
    }
    public boolean onurListesinde(){
        if(this.ortalama >= 3.0) return true;

        return false;
    }
    public int getNumara() {
        return this.numara;
    }

    public double getOrtalama() {
        return this.ortalama;
    }

    @Override
    public String tanit() {
        String onur = onurListesinde() ? " *onur*" : "";
        return String.format(java.util.Locale.US, "%-14s %s yas=%d [Ogrenci no=%d ort=%.2f%s]",
                getAd(), maskeliTc(), yas(), this.numara, this.ortalama, onur);
    }

}
class Personel extends Kisi {
    private final String unvan;
    private final int  hizmetYili;

    public Personel(String ad, String tcNo, int dogumYili, String unvan, int hizmetYili){
        super(ad,tcNo,dogumYili);
        this.unvan = unvan;
        this.hizmetYili = hizmetYili;
        
    }
    double kidemKatsayisi(){
        return 1.0 + this.hizmetYili * 0.05;
    }

    @Override
    public String tanit(){

    }

}