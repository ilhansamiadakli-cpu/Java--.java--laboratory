public class  Kitap4 {
    private static final String ON_EK = "KTP-";
    private static final int BASLANGIC_NO = 1;
    public static final int AZAMI_ODUNC_GUN = 21;

    private final String demirbasNo;
    private final String baslik;
    private final String yazar;
    private boolean odunctemi = false;

    private static int sonrakiNo = BASLANGIC_NO;
    private static int uretilen = 0;   
    private static int oduncteki = 0;

    public Kitap4(String baslik, String yazar){
        if(baslik == null || baslik.isBlank()) throw new IllegalArgumentException();
        if(yazar == null || yazar.isBlank()) throw new IllegalArgumentException();
        this.baslik = baslik;
        this.yazar = yazar;
        this.demirbasNo = numaraUret();

    }
    private static String numaraUret(){
        String metin = String.format("%s%05d", ON_EK, sonrakiNo);
        sonrakiNo++;
        uretilen++;
        return metin;
    }
    public void oduncVer(){
        if(this.odunctemi){ System.out.println("UYARI: " + this.demirbasNo + " zaten odunc verilmis."); return ;}

        this.odunctemi = true;
        Kitap4.oduncteki++;
    }
    public void iadeAl(){
        if(!this.odunctemi){System.out.println("UYARI: " + this.demirbasNo + " zaten raflarda."); return;}
        this.odunctemi = false;
        Kitap4.oduncteki--;
    }
    public static double oduncOrani(){
        if(uretilen == 0) return 0.0;

        return ((double) (Kitap4.oduncteki/uretilen)) * 100.0;
    }
    public static String sonrakiNumara(){
        return String.format("%s%05d", ON_EK, sonrakiNo);
    }

    public static int uretilenSayisi(){return uretilen;}
    public static int oduncteSayisi(){ return oduncteki; }
    public static int raftakiSayisi(){ return uretilen - oduncteki;}

    public String getDemirbasNo() { return this.demirbasNo; }
    public String getBaslik() { return this.baslik; }
    public String getYazar() { return this.yazar; }
    public boolean isOdunctemi() { return this.odunctemi; }

    @Override
    public String toString() {
        String durum = this.odunctemi ? "ODUCTE" : "RAFTA";
        return String.format("%s | %s / %s | %s", 
                this.demirbasNo, this.baslik, this.yazar, durum);
    }

}
/*
?1. sonrakiNo alanini static yapmasaydiniz ne olurdu? Uretilen kitaplarin numaralari ne olurdu?
!   YANIT: sonrakiNo bir ornek alani (instance field) olurdu ve her nesne uretildiginde
!   yeniden BASLANGIC_NO (1) degeriyle baslardi. Dolayisiyla uretilen her kitabin
!   numarasi ayni kalarak "KTP-00001" olurdu; benzersiz numara uretilemezdi.

?2. numaraUret() metodunu static yapmak zorunda miydiniz? Teknik olarak zorunlu
?  olmasa bile neden dogru tercih oldugunu aciklayiniz.
!   YANIT: Teknik olarak zorunlu degildir (ornek metotlari static alanlara erisebilir).
!   Ancak numaraUret() yalnizca sinif duzeyindeki static alanlarla (ON_EK, sonrakiNo,
!   uretilen) calisir; nesneye ait hicbir alana ("this") ihtiyac duymaz. Durumu olmayan
!   ve sadece sinif duzeyini ilgilendiren islemleri static tanimlamak tasarim acisindan
!   dogru ve tutarli yaklasimdir.

?3. Bir kitap nesnesi cop toplayici (Garbage Collector) tarafindan yok edilse
!   uretilen sayaci azalir mi? Bu davranis dogru mudur, gerekcelendiriniz.
!   YANIT: Azalmaz. Bu davranis tamamen dogrudur; cunku "uretilen" sayaci o ana kadar
!   kutuphane demirbasina kaydedilmis toplam kitap sayisini ve tarihsel uretimi temsil
!   eder. Bir kitabin yok edilmesi, onceden verilmis demirbas numarasini geri aldirmaz
!   veya bir baska kitaba tekrar verilmesine yol acmaz (numara cakismasini onler).
*/