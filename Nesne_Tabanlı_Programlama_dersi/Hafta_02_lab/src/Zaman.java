public class Zaman {

    private int saat;
    private int dakika;
    private int saniye;
    

    public Zaman(int saat, int dakika, int saniye){
        if(saat > 23 || saat < 0){ this.saat = 23;} else{this.saat= saat; }
        if(dakika > 59 || dakika < 0) {this.dakika = 59;} else { this.dakika = dakika;}
        if(saniye > 59 || saniye < 0) {this.saniye = 59;} else{ this.saniye = saniye;}
    }
    public Zaman(){
        this(0, 0, 0);
    }    
    public Zaman(int saat, int dakika){
        this(saat, dakika, 0);
    }    
    public Zaman(int toplamSaniye){
        this(toplamSaniye / 3600, (toplamSaniye % 3600) / 60, toplamSaniye % 60);
    }
    public Zaman(Zaman kaynak){
        this(kaynak.saat,kaynak.dakika,kaynak.saniye);
    }
    public int toplamSaniye(){
        return this.saat * 3600 + this.dakika * 60 + this.saniye;
    }
    public Zaman ilerlet(int saniye){
        int yeniToplam = (toplamSaniye() + saniye) % 86400;
        if (yeniToplam < 0) yeniToplam += 86400;
        this.saat = yeniToplam / 3600;
        this.dakika = (yeniToplam % 3600) / 60;
        this.saniye = yeniToplam % 60;
        return this;
    }
    public int farkSaniye(Zaman digeri){
        return Math.abs(this.toplamSaniye() - digeri.toplamSaniye());
    }
    public boolean ogledenOnceMi(){
        if(this.saat < 12) return true;
        return false;
    }
    @Override
    public String toString() {
            // TODO Auto-generated method stub
            return String.format("%02d:%02d:%02d", this.saat, this.dakika, this.saniye);
    }
}
