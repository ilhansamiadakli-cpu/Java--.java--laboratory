public class Gorev4_HataAyiklama {
    public static void main(String[] args) {
        Urun u1 = new Urun("Klavye", 750.0, 3);
        Urun u2 = new Urun("Fare");
        Urun u3 = new Urun();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
    }
}

class Urun {
    private String ad;
    private double fiyat;
    private int stok;

    // HATA 1: yapıcı fonksiyon void,int gibi değerler ile tanımlanamaz
    // HATA 2: isimlendirme hatası, java büyük küçük harflere duyarlıdır
    public Urun(String ad, double fiyat, int stok) {
        this.ad = ad; // HATA 3: burda this. kullanılmamaış
        this.fiyat = fiyat;
        this.stok = stok;
    }
    // HATA 4: eksik yapicilar 
    public Urun(String ad){
        this(ad, 0.0, 0);
    }
    public  Urun(){
        this("İsimsiz", 0.0, 0);
    }
    public void setFiyat(double fiyat){
        if(fiyat < 0){ System.out.println("Hata: fiyat negatif olamaz");return ;} // nasılda { } içinden geçtim :)
        this.fiyat = fiyat;
    }
    public void stokEkle(int adet){
        if(this.stok + adet < 0) { System.out.println("Hata: girilen adet stokları negatif yapamaz"); return ;}
        this.stok += adet;
    }
    @Override
    public String toString() {
        return String.format("%-10s %9.2f TL stok: %3d", ad, fiyat, stok);
    }
}