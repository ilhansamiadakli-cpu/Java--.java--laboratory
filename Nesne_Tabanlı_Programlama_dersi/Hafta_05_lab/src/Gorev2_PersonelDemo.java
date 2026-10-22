import java.util.Locale;

public class Gorev2_PersonelDemo {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        System.out.println("=== 1) Kaynak nesne hazirlaniyor ===");
        Personel p1 = new Personel("P-1001", "Betul Mumcu", 42000.00);
        p1.gorevEkle("Laboratuvar");
        p1.gorevEkle("Odev degerlendirme");
        System.out.println(p1);

        System.out.println("\n=== 2) DERIN kopya: diziler AYRI ===");
        Personel derin = new Personel(p1); // false -> derin kopya
        derin.setAd("Betul Mumcu (yedek kayit)");
        derin.zamYap(10);
        derin.gorevDegistir(0, "Laboratuvar (2. grup)");
        System.out.println("Kaynak: " + p1);
        System.out.println("Kopya : " + derin);
        System.out.println("Kopyanin 0. gorevi degisti, KAYNAGINKI DEGISMEDI.");

        System.out.println("\n=== 3) SIG kopya: dizi PAYLASILIYOR ===");
        Personel p2 = new Personel("P-1002", "Ahmet Yilmaz", 35000.00);
        p2.gorevEkle("Teori dersi");
        p2.gorevEkle("Danismanlik");

        Personel sig = new Personel(p2, true); // true -> sığ kopya
        System.out.println("Kopyalama sonrasi:");
        System.out.println("Kaynak: " + p2.gorevleriYaz());
        System.out.println("Kopya : " + sig.gorevleriYaz());

        sig.gorevDegistir(0, "UZAKTAN teori dersi");
        System.out.println("Kopyanin 0. gorevi degistirildi:");
        System.out.println("Kaynak: " + p2.gorevleriYaz());
        System.out.println("Kopya : " + sig.gorevleriYaz());
        System.out.println("KAYNAK DA DEGISTI -> iki nesne AYNI diziyi gosteriyor.");

        System.out.println("\n=== 4) Ilkel alanlar her zaman kopyalanir ===");
        Personel a = new Personel("P-01", "A", 20000.00);
        Personel b = new Personel(a, true); 
        b.zamYap(50);
        System.out.printf("a maas: %.2f%n", a.getMaas());
        System.out.printf("b maas: %.2f%n", b.getMaas());
        System.out.println("double alan kopyalandi; sig/derin ayrimi ilkel turleri etkilemez.");

        System.out.println("\n=== 5) Gecersiz istekler ===");
        Personel p3 = new Personel("P-1003", "Deneme Kisi", 30000.00);
        p3.zamYap(-5);
        p3.gorevEkle("");
        p3.setAd(" ");
        System.out.println("Nesne durumu korundu : " + p3);
    }
}