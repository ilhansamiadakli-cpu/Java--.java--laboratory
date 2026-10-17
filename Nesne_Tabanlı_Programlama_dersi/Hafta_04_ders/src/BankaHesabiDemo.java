/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 9: Kapsullenmis hesabin sinir durumlari
 */
public class BankaHesabiDemo {

    public static void main(String[] args) {
        BankaHesab h = new BankaHesab("TR33 0006 1005", "Ayse Yilmaz", 1200.0);
        System.out.println("Acilis: " + h);

        System.out.println();
        System.out.println("--- Gecerli islemler ---");
        h.yatir(500.0);
        h.cek(300.0);

        System.out.println();
        System.out.println("--- Reddedilen islemler ---");
        h.yatir(-50.0);
        h.cek(0.0);
        h.cek(99999.0);

        System.out.println();
        System.out.println("--- Gunluk limit ---");
        h.yatir(5000.0);
        h.cek(1500.0);
        h.cek(300.0);
        System.out.printf("Kalan gunluk hak: %.2f%n", h.getKalanGunlukHak());

        System.out.println();
        System.out.println("Son durum: " + h);
        System.out.println("Bakiye ancak yatir()/cek() ile degisti; dogrudan atama yolu yok.");
    }
}
