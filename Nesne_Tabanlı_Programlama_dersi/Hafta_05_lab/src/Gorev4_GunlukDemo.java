public class Gorev4_GunlukDemo {
    public static void main(String[] args) {
        System.out.println("=== 1) Normal akis: blok bitince close() ===");
        try (GunlukDosyasi d1 = new GunlukDosyasi("islem.log")) {
            d1.kaydet("kullanici girisi");
            d1.kaydet("rapor uretildi");
        }

        System.out.println("\n=== 2) Hata olsa bile close() cagrilir ===");
        GunlukDosyasi disKopya = null;
        try (GunlukDosyasi d2 = new GunlukDosyasi("hata.log")) {
            disKopya = d2;
            d2.kaydet("islem basladi");
            throw new RuntimeException("disk dolu");
        } catch (RuntimeException e) {
            System.out.println("HATA    : " + e.getMessage());
            System.out.println("Durum   : " + disKopya);
        }

        System.out.println("\n=== 3) Kapali kaynaga yazilamaz ===");
        disKopya.kaydet("kapali dosyaya deneme");

        System.out.println("\n=== 4) Iki kaynak: kapanma sirasi TERSTIR ===");
        try (GunlukDosyasi a = new GunlukDosyasi("a.log");
             GunlukDosyasi b = new GunlukDosyasi("b.log")) {
            a.kaydet("A satiri");
            b.kaydet("B satiri");
        }

        System.out.println("\n=== 5) close() birden cok kez cagrilirsa ===");
        try (GunlukDosyasi d3 = new GunlukDosyasi("tek.log")) {
            d3.close();
            d3.close(); // Ikincisi etkisiz olmali
            System.out.println("Sonuc   : ikinci close() etkisiz (idempotent)");
        }

        System.out.println("\n=== 6) Toplam sayaclar ===");
        System.out.println("Acilan kaynak sayisi : " + GunlukDosyasi.acilanSayisi());
        System.out.println("Kapanan kaynak sayisi: " + GunlukDosyasi.kapananSayisi());
    }
}
class GunlukDosyasi implements AutoCloseable{
    private static int acilanSayisi = 0;
    private static int kapananSayisi = 0;
    private int satirSayisi = 0;
    private boolean acik;
    private final String dosyaAdi;

    public GunlukDosyasi(String dosyaAdi){
        if(dosyaAdi == null || dosyaAdi.isBlank()) throw new IllegalArgumentException("Hata dosyanın ismi boş olamaz");
        this.dosyaAdi = dosyaAdi;
        acilanSayisi++;
        this.acik = true;
        System.out.println("ACILDI  : " + dosyaAdi);
    }
    public void kaydet(String satir){
        if (!this.acik){
            System.out.println("RED     : " + dosyaAdi + " kapali, yazilamaz");
            return ;
        }
        this.satirSayisi++;
        System.out.println("YAZILDI : " + dosyaAdi + " [" + satirSayisi + "] " + satir);
    }
    public void close(){
        if(!this.acik) return;
        this.acik = false;
        kapananSayisi++;
        System.out.println("KAPANDI : " + dosyaAdi + " (" + satirSayisi + " satir)");
    }
    public static int acilanSayisi(){
        return acilanSayisi;
    }
    public static int kapananSayisi(){
        return kapananSayisi;
    }
    @Override
    public String toString() {
        return String.format("GunlukDosyasi [%s, satir=%d, acik=%b]", 
                this.dosyaAdi, this.satirSayisi, this.acik);
    }
}