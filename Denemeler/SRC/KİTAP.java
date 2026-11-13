public class KİTAP {
    private final String isbn; 
    private String baslik;
    // Yapıcıları ve getter'ları tanımlıdır.
     public String getBaslik() { return baslik; }
}
class OduncKaydi{
    String kayitNo;
    String uyeNo;
    KİTAP kitap;
    public OduncKaydi(String kayitNo, String uyeNo, KİTAP kitap){
        if(kayitNo == null || kayitNo.isBlank()) {
            throw new IllegalArgumentException("Kayıt numarası boş olamaz");
        }
        if(uyeNo == null || uyeNo.isBlank()) {
            throw new IllegalArgumentException("Üye numarası boş olamaz");
        }
        if(kitap == null) {
            throw new IllegalArgumentException("Kitap nesnesi boş olamaz");
        }
        this.kayitNo = kayitNo;
        this.uyeNo = uyeNo;
        this.kitap = kitap;
    }
    public void bilgiYazdir(){
        System.out.println("Kayıt No: " + kayitNo);
        System.out.println("Üye No: " + uyeNo);
        System.out.println("Kitap Başlığı: " + kitap.getBaslik());
    }
}