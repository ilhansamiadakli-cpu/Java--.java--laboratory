import kutuphane.*;

public class Gorev5_KutuphaneDemo {

    public static void main(String[] args) {
        Katalog katalog = new Katalog(10);
        Kitap a = new Kitap("978-0", "Java: How to Program", "Deitel");
        Kitap b = new Kitap("978-1", "Core Java Vol. I", "Horstmann");
        Kitap c = new Kitap("978-2", "Effective Java", "Bloch");

        katalog.ekle(a);
        katalog.ekle(b);
        katalog.ekle(c);

        System.out.println("=== Baslangic katalogu ===");
        katalog.listele();

        System.out.println();
        System.out.println("=== Odunc islemleri ===");
        katalog.oduncVer("978-1");
        katalog.oduncVer("978-1");
        katalog.oduncVer("978-9");
        katalog.iadeAl("978-2");

        System.out.println();
        System.out.println("=== Son durum ===");
        katalog.listele();
        System.out.println("Raftaki kitap sayisi: " + katalog.getRaftakiSayisi());

        System.out.println();
        System.out.println("=== Paket disindan erisim sinirlari ===");
        System.out.println("a.isOduncte() = " + a.isOduncte() + "  (public getter)");
        System.out.println("a.oduncte alanina dogrudan yazma yolu KAPALI.");
    }

    @Override
    public String toString() {
        return "Gorev5_KutuphaneDemo[]";
    }
}