import java.util.Locale;

public class Gorev1_KitapDemo {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        System.out.println("=== 1) Hic kitap yokken sinif durumu ===");
        System.out.println("uretilenSayisi = " + Kitap4.uretilenSayisi());
        System.out.println("sonrakiNumara  = " + Kitap4.sonrakiNumara());

        System.out.println("\n=== 2) Bes kitap uretiliyor ===");
        Kitap4 k1 = new Kitap4("Nesne Yonelimli Programlama", "Deitel");
        Kitap4 k2 = new Kitap4("Core Java Volume I", "Horstmann");
        Kitap4 k3 = new Kitap4("Effective Java", "Bloch");
        Kitap4 k4 = new Kitap4("Java: The Complete Reference", "Schildt");
        Kitap4 k5 = new Kitap4("Clean Code", "Martin");

        System.out.println(k1);
        System.out.println(k2);
        System.out.println(k3);
        System.out.println(k4);
        System.out.println(k5);
        System.out.println("Numaralar KESINTISIZ ve BENZERSIZ verildi.");

        System.out.println("\n=== 3) Odunc islemleri ===");
        k1.oduncVer();
        k4.oduncVer();
        k4.oduncVer(); // UYARI vermeli
        System.out.println("oduncteSayisi = " + Kitap4.oduncteSayisi());
        System.out.println("raftakiSayisi = " + Kitap4.raftakiSayisi());
        System.out.printf(Locale.US, "oduncOrani    = %.1f%%%n", Kitap4.oduncOrani());

        System.out.println("\n=== 4) Iade ===");
        k1.iadeAl();
        k1.iadeAl(); // UYARI vermeli
        System.out.println("oduncteSayisi = " + Kitap4.oduncteSayisi());
        System.out.printf(Locale.US, "oduncOrani    = %.1f%%%n", Kitap4.oduncOrani());

        System.out.println("\n=== 5) Sayaclar nesneden BAGIMSIZ okunabilir ===");
        System.out.println("Kitap4.uretilenSayisi() = " + Kitap4.uretilenSayisi());
        System.out.println("k3.uretilenSayisi()    = " + Kitap4.uretilenSayisi() + " <- ayni deger, ama bu yazim bicimi ONERILMEZ");
        System.out.println("Kitap4.sonrakiNumara()  = " + Kitap4.sonrakiNumara());

        System.out.println("\n=== 6) Gecersiz kitap sayaci artirmaz ===");
        try {
            new Kitap4("", "Anonim");
        } catch (IllegalArgumentException e) {
            System.out.println("RET: " + e.getMessage());
        }
        System.out.println("uretilenSayisi = " + Kitap4.uretilenSayisi() + " sonrakiNumara = " + Kitap4.sonrakiNumara());
    }
}