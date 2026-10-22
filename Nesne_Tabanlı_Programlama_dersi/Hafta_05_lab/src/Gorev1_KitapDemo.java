public class Gorev1_KitapDemo {
    public static void main(String[] args) {
        System.out.println("=== 1) Dort yapici, dort ayri eksiklik duzeyi ===");
        Kitap3 k1 = new Kitap3("978-975-01", "Nesne Yonelimli Programlama");
        Kitap3 k2 = new Kitap3("978-975-02", "Veri Yapilari", "H. Yilmaz");
        Kitap3 k3 = new Kitap3("978-975-03", "Algoritmalar", "S. Demir", 720);
        Kitap3 k4 = new Kitap3("978-975-04", "Isletim Sistemleri", "A. Kara", 640, 2019);

        System.out.println(k1);
        System.out.println(k2);
        System.out.println(k3);
        System.out.println(k4);

        System.out.println("=== 2) Varsayilanlarin nereden geldigi ===");
        System.out.println("k1 yazar      : " + k1.getYazar() + "\t(2. yapicidan)");
        System.out.println("k1 sayfa      : " + k1.getSayfaSayisi() + "\t(3. yapicidan)");
        System.out.println("k1 basim yili : " + k1.getBasimYili() + "\t(4. yapicidan)");

        System.out.println("=== 3) Dogrulama TEK yerde: ana yapici ===");
        denetle("Sayfa sayisi gecersiz", () -> new Kitap3("978-975-05", "Deneme", "Yazar", 0, 2020));
        denetle("ISBN bos olamaz", () -> new Kitap3("", "Deneme", "Yazar", 100, 2020));
        denetle("Baslik bos olamaz", () -> new Kitap3("978-975-06", "  ", "Yazar", 100, 2020));
        denetle("Basim yili gecersiz", () -> new Kitap3("978-975-07", "Deneme", "Yazar", 100, 2030));

        System.out.println("=== 4) Kopya yapici ===");
        Kitap3 kopya = new Kitap3(k4);
        System.out.println("Kaynak: " + k4);
        System.out.println("Kopya : " + kopya);
        System.out.println("Ayni nesne mi? " + (k4 == kopya));

        System.out.println("--- kopyanin basligi degistirildi ---");
        kopya.setBaslik("Isletim Sistemleri (2. baski)");
        System.out.println("Kaynak: " + k4.getBaslik());
        System.out.println("Kopya : " + kopya.getBaslik());

        System.out.println("=== 5) Turetilmis bilgi: getYas() ===");
        System.out.println(k4.getBaslik() + " -> " + k4.getYas() + " yasinda");
    }

    private static void denetle(String baslik, Runnable test) {
        try {
            test.run();
        } catch (IllegalArgumentException e) {
            System.out.println("RET: " + e.getMessage());
        }
    }
}