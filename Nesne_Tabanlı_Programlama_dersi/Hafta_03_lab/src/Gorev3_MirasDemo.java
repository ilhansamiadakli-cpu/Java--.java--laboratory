public class Gorev3_MirasDemo {
    public static void main(String[] args) {
        Kisi k = new Kisi("Zeynep Kaya", "12345678901", 1999);
        Ogrenci o = new Ogrenci("Emre Demir", "98765432109", 2005, 376512, 3.42);

        System.out.println("--- Devralinan uyeler ---");
        System.out.println(k.tanit());
        System.out.println(o.tanit());

        System.out.println("\nOgrenci, Kisi'den NELERI devraldi?");
        System.out.println("o.getAd()       = " + o.getAd());
        System.out.println("o.maskeliTc()   = " + o.maskeliTc());
        System.out.println("o.yas()         = " + o.yas());
        System.out.println("(bu uc uye Ogrenci.java icinde HIC yazilmadi)");

        System.out.println("\n--- IS-A iliskisi ---");
        System.out.println("o instanceof Ogrenci = " + (o instanceof Ogrenci));
        System.out.println("o instanceof Kisi    = " + (o instanceof Kisi));
        System.out.println("k instanceof Ogrenci = " + (k instanceof Ogrenci));

        System.out.println("\n--- Kapsulleme mirasi bozmuyor ---");
        o.setOrtalama(5.0);
        System.out.println("ortalama = " + o.getOrtalama());
        o.setOrtalama(3.75);
        System.out.println("ortalama = " + o.getOrtalama());
        System.out.println(o.tanit());
    }
}