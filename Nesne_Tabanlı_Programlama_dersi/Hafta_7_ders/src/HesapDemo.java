/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 9 : protected, super.metot() ve final metot uygulamasi
 * ========================================================== */

public class HesapDemo {

    public static void main(String[] args) {
        System.out.println("=== 1) Ust sinif : Hesap ===");
        Hesap h = new Hesap("Ayse Yildiz", 5000.0);
        System.out.println("  " + h.ozet());
        h.yatir(1500.0);
        System.out.println("  1500 yatirildi -> " + h.ozet());
        System.out.println("  cek(2000) -> " + h.cek(2000.0) + " | " + h.ozet());
        System.out.println("  cek(99999) -> " + h.cek(99999.0));
        System.out.printf("  aylikKesinti() = %.2f  (oran %.3f)%n",
                          h.aylikKesinti(), 0.0);

        System.out.println();
        System.out.println("=== 2) Alt sinif : VadeliHesap ===");
        VadeliHesap v = new VadeliHesap("Elif Demir", 20000.0, 32);
        System.out.println("  " + v.ozet());
        System.out.println("  vade dolmadan cek(1000):");
        System.out.println("  sonuc -> " + v.cek(1000.0));

        System.out.println();
        System.out.println("=== 3) Vade doldu, ust sinif kurallari yine gecerli ===");
        v.gunIlerlet(40);
        System.out.println("  " + v.ozet());
        System.out.printf("  faizIsle() = %.2f%n", v.faizIsle());
        System.out.println("  " + v.ozet());
        System.out.println("  cek(1000) -> " + v.cek(1000.0) + " | " + v.ozet());
        System.out.println("  cek(999999) -> " + v.cek(999999.0)
                           + "   (ust siniftan gelen bakiye kontrolu)");

        System.out.println();
        System.out.println("=== 4) Ezilen uzatma noktasinin etkisi ===");
        System.out.printf("  Hesap.aylikKesinti()       = %.2f%n", h.aylikKesinti());
        System.out.printf("  VadeliHesap.aylikKesinti() = %.2f%n", v.aylikKesinti());
        System.out.println("  aylikKesinti() metodu VadeliHesap'ta YAZILMADI;");
        System.out.println("  yalnizca kesintiOrani() ezildi.");

        System.out.println();
        System.out.println("=== 5) final metot : hesapNo() ===");
        System.out.println("  h.hesapNo() -> " + h.hesapNo());
        System.out.println("  v.hesapNo() -> " + v.hesapNo());
        System.out.println("  Bu metot final oldugundan hicbir alt sinif");
        System.out.println("  numara bicimini degistiremez.");

        System.out.println();
        System.out.println("=== 6) Ust tur referansi ile calisma ===");
        Hesap[] hesaplar = { h, v };
        double toplam = 0.0;
        for (Hesap x : hesaplar) {
            toplam += x.getBakiye();
            System.out.println("  " + x.getClass().getSimpleName() + " -> " + x.ozet());
        }
        System.out.printf("  toplam bakiye = %.2f%n", toplam);
    }
}
