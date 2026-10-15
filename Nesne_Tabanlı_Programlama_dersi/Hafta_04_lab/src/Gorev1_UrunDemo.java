import java.util.Locale;

public class Gorev1_UrunDemo {

    public static void main(String[] args) {
        Urun u = new Urun("8690001", "Defter A5", 42.50, 30);
        System.out.println("Baslangic : " + u);

        System.out.println();
        System.out.println("--- Gecersiz istekler ---");
        u.setFiyat(-10.0);
        u.setAd("");
        u.stokCik(500);
        System.out.println("Degismedi : " + u);

        System.out.println();
        System.out.println("--- Gecerli islemler ---");
        u.setFiyat(45.00);
        u.stokCik(7);
        System.out.println("Son durum : " + u);
        System.out.printf(Locale.US, "Stok degeri: %.2f TL%n", u.getStokDegeri());
    }

    @Override
    public String toString() {
        return "Gorev1_UrunDemo[]";
    }
}