public class Gorev1_YayinDemo {
    public static void main(String[] args) {
        System.out.println("=== GOREV 1 Yayin -> Kitap hiyerarsisi ===");

        System.out.println("1) Ust sinif nesnesi (genel yayin):");
        Yayin y = new Yayin("KTU Of Teknoloji Bulteni", 2025);
        System.out.println(y.kunye());
        System.out.println("odunc suresi -> " + y.odunctenGunSayisi() + " gun");

        System.out.println("\n2) Alt sinif nesnesi (kitap):");
        Kitap5 k1 = new Kitap5("Java How to Program", 2018, "Deitel & Deitel", 1024, "978-0134743356");
        Kitap5 k2 = new Kitap5("Effective Java", 2018, "Joshua Bloch", 412, "978-0134685991");
        System.out.println(k1.kunye());
        System.out.println(k2.kunye());

        System.out.println("\n3) Miras alinan uyeler Kitap'ta YAZILMADI:");
        System.out.println("k1.demirbasNo() -> " + k1.demirbasNo());
        System.out.println("k1.getYil()       -> " + k1.getYil());
        System.out.println("k1.oduncteMi()    -> " + k1.oduncteMi());

        System.out.println("\n4) Kitap'a ozgu uyeler:");
        System.out.println("k1.kalinMi() -> " + k1.kalinMi() + " (1024 sayfa)");
        System.out.println("k2.kalinMi() -> " + k2.kalinMi() + " (412 sayfa)");

        System.out.println("\n5) Ezilen uzatma noktasinin etkisi:");
        System.out.println("Genel yayin oduncu:");
        y.oduncVer("Elif Demir");

        System.out.println("Kitap oduncu (21 gun, cunku odunctenGunSayisi ezildi):");
        k1.oduncVer("Elif Demir");

        System.out.println("Ayni kitap ikinci kez:");
        k1.oduncVer("Elif Demir");

        System.out.println("\n6) Iade ve yeniden odunc:");
        k1.iadeAl();
        k1.iadeAl();
        k1.oduncVer("Murat Kaya");
    }
}
/*
? 1. oduncte alanını private yapsaydınız hangi görevlerde sorun çıkardı? protected yapmanın bedeli nedir?
!CEVAP: oduncte alanı private olsaydı, alt sınıflar (özellikle Görev 2'deki Dergi sınıfı) bu duruma doğrudan erişemezdi; durum kontrolü yapabilmek için üst sınıfta bir getter veya durum değiştirici metoda bağımlı kalırlardı. protected yapmanın bedeli ise kapsüllemenin zayıflamasıdır: Aynı paketteki sınıflar ve tüm alt sınıflar bu alana doğrudan erişebilir hale gelir; böylece üst sınıfın iç durumunun alt sınıflar tarafından kurallara aykırı olarak doğrudan manipüle edilme riski doğar.

? 2. demirbasNo() metodunu final yapmanın gerekçesi nedir? Bu metot final olmasaydı hangi kötü senaryo mümkün olurdu?
!CEVAP: Metodun final yapılma gerekçesi, sistem genelinde demirbaş numarası biçiminin (D-XXXX standardının) ve kimlik üretim kuralının tüm alt sınıflar için tutarlı ve değiştirilemez kalmasını garanti etmektir. Eğer final olmasaydı, türeyen herhangi bir alt sınıf bu metodu @Override edip numarayı rastgele değiştirebilir, boş döndürebilir veya biçimini bozabilirdi; bu da kütüphane kataloğunun ve veri bütünlüğünün çökmesine yol açardı.

? 3. Kitap sınıfında getYil() metodunu yazmadınız, ama k1.getYil() çalışıyor. Bu nasıl mümkün oluyor?
!CEVAP: Bu durum nesne yönelimli programlamadaki kalıtım (inheritance) mekanizması sayesinde mümkündür. Kitap sınıfı Yayin sınıfından türediği (extends) için, Yayin sınıfında public olarak tanımlanmış olan getYil() metodunu otomatik olarak devralır (miras alır). Alt sınıfta bu metot yeniden yazılmasa bile çalışma zamanında üst sınıfın metodu çağrılır.
*/
