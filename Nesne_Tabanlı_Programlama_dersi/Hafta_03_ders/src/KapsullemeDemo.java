/*
 * Nesne Yonelimli Programlama - Hafta 03
 * Ornek 6: Kapsulleme bir nesneyi neye karsi korur?
 */
public class KapsullemeDemo {

    public static void main(String[] args) {
        BankaHesab h = new BankaHesab("TR12 0006 1005", "Ayse Yilmaz", 3000.0);
        System.out.println("Baslangic : " + h);

        System.out.println();
        System.out.println("--- Gecerli islemler ---");
        System.out.println("  yatir(500)  -> " + h.yatir(500.0));
        System.out.println("  cek(300)    -> " + h.cek(300.0));
        System.out.println("  Durum: " + h);

        System.out.println();
        System.out.println("--- Kurallari zorlayan islemler ---");
        System.out.println("  yatir(-100) -> " + h.yatir(-100.0));
        System.out.println("  cek(99999)  -> " + h.cek(99999.0));
        System.out.println("  cek(1800)   -> " + h.cek(1800.0));
        System.out.println("  Durum: " + h);

        System.out.println();
        System.out.println("--- Gun degisince ayni istek onaylanir ---");
        //h.gunuKapat();
        System.out.println("  cek(1800)   -> " + h.cek(1800.0));
        System.out.println("  Durum: " + h);

        System.out.println();
        System.out.println("--- Kapsulleme neyi ENGELLEDI? ---");
        System.out.println("  h.bakiye = 1000000;  satiri DERLENMEZ (private alan).");
        System.out.println("  setBakiye() metodu YOKTUR; bakiye yalnizca is kurallari");
        System.out.println("  uygulanarak degisir. Kural TEK yerde: BankaHesabi sinifi.");
        System.out.printf("  Son bakiye (yalnizca getter ile okunur): %.2f TL%n",
                          h.getBakiye());
    }
}
