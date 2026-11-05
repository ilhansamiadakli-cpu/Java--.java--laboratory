public class Gorev2_ZincirDemo {
    public static void main(String[] args) {
        System.out.println("=== GOREV 2 Yayin -> Dergi -> AkademikDergi ===");

        System.out.println("1) Dergi (guncel sayi) olusturuluyor:");
        Dergi d1 = new Dergi("Bilim ve Teknik", 2026, 703, "aylik");
        System.out.println(d1.kunye());
        System.out.println("oduncVerilebilirMi() -> " + d1.oduncVerilebilirMi());
        d1.oduncVer("Elif Demir");

        System.out.println("\n2) Arsive alinca kural degisiyor:");
        d1.arsiveAl();
        System.out.println("oduncVerilebilirMi() -> " + d1.oduncVerilebilirMi());
        d1.oduncVer("Elif Demir");
        System.out.println("(7 gun: Dergi.DERGI_GUN)");

        System.out.println("\n3) Ust sinif kontrolu KAYBOLMADI:");
        d1.oduncVer("Elif Demir");

        System.out.println("\n4) Uc seviyeli hiyerarsi AkademikDergi:");
        AkademikDergi a1 = new AkademikDergi("Journal of Software Engineering", 2025, 12, "uc aylik", "SCI-E");
        a1.arsiveAl(); // Arsive aliniyor
        System.out.println(a1.kunye());

        System.out.println("\n5) Uc seviyenin kurallari SIRAYLA isliyor:");
        System.out.println("(a) akademik olmayan kisi:");
        a1.oduncVer("Elif Demir");

        System.out.println("(b) akademik personel:");
        a1.oduncVer("Dr. Ayse Yildiz");

        System.out.println("(c) ayni anda ikinci istek:");
        a1.oduncVer("Dr. Ayse Yildiz");

        System.out.println("\n6) Guncel akademik dergi iki kural birlikte:");
        AkademikDergi a2 = new AkademikDergi("IEEE Software", 2026, 4, "uc aylik", "SCI");
        System.out.println(a2.kunye());
        a2.oduncVer("Dr. Ayse Yildiz");
        a2.arsiveAl();
        a2.oduncVer("Dr. Ayse Yildiz");

        System.out.println("\n7) Miras zinciri:");
        Class<?> mevcut = a1.getClass();
        StringBuilder zincir = new StringBuilder();
        while (mevcut != null) {
            zincir.append(mevcut.getSimpleName());
            mevcut = mevcut.getSuperclass();
            if (mevcut != null) zincir.append(" -> ");
        }
        System.out.println(zincir);

        System.out.println("\n8) IS-A zinciri:");
        System.out.println("a1 instanceof AkademikDergi -> " + (a1 instanceof AkademikDergi));
        System.out.println("a1 instanceof Dergi         -> " + (a1 instanceof Dergi));
        System.out.println("a1 instanceof Yayin         -> " + (a1 instanceof Yayin));
        System.out.println("d1 instanceof AkademikDergi -> " + (d1 instanceof AkademikDergi));

        System.out.println("\n9) Odunc sureleri (ezilen uzatma noktasi):");
        Yayin genel = new Yayin("Test", 2020);
        Kitap5 kitap = new Kitap5("Test", 2020, "Yazar", 100, "123");
        System.out.println("Yayin         : " + genel.odunctenGunSayisi() + " gun");
        System.out.println("Kitap         : " + kitap.odunctenGunSayisi() + " gun");
        System.out.println("Dergi         : " + d1.odunctenGunSayisi() + " gun");
        System.out.println("AkademikDergi : " + a1.odunctenGunSayisi() + " gun");
    }
}
/*
? 1. Dergi.oduncVerilebilirMi() metodunda super.oduncVerilebilirMi() çağrısını silip yalnızca return !guncelSayi; yazsanız hangi hata ortaya çıkar? Çıktıda hangi satır kaybolur?
!CEVAP: Bu durumda üst sınıf olan Yayin'ın ödünç durumu denetimi (materyalin zaten ödünçte olup olmadığı kontrolü) devre dışı kalır; dergi arşivde olduğu sürece, ödünçte olsa bile sistem onu tekrar "ödünç verilebilir" görür. Çıktıda üst sınıfın koruduğu ve ikinci kez aynı dergi istendiğinde basılan "RET: D-0001 zaten Elif Demir tarafindan alinmis." kuralı ve tutarlılığı kaybolur.

? 2. AkademikDergi sınıfında guncelSayi alanına doğrudan erişebiliyorsunuz. Bu hangi erişim düzeyi sayesinde mümkün oluyor? Alan private olsaydı ne yapmanız gerekirdi?
!CEVAP: Bu, guncelSayi alanının Dergi sınıfında protected erişim düzeyiyle bildirilmiş olması sayesinde mümkündür; protected üyeler alt sınıflardan doğrudan okunabilir ve değiştirilebilir. Eğer bu alan private olsaydı, alt sınıf olan AkademikDergi doğrudan erişemezdi; bu durumda Dergi sınıfına boolean isGuncelSayi() gibi bir protected/public getter metodu eklenmesi gerekirdi.

? 3. Üç sınıfın odunctenGunSayisi() metotları birbirini çağırmıyor. Bu neden doğru bir tercih? Hangi durumda super.odunctenGunSayisi() çağırmak anlamlı olurdu?
!CEVAP: Doğru bir tercihtir çünkü bu metot bir genişletme (üzerine ekleme) işlemi değil, her sınıf için bağımsız ve sabit bir kuralı (15, 7, 3 gün) belirleyen bir "uzatma noktası"dır (hook method). super.odunctenGunSayisi() çağrısı ancak alt sınıfın süresi üst sınıfın süresine bağımlı bir formülle hesaplansaydı anlamlı olurdu (örneğin "üst sınıfın süresine ek 5 gün ver" veya "üst sınıfın süresinin yarısını kullan" gibi bir iş kuralı bulunsaydı).
*/
