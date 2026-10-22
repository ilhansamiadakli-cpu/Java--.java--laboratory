public class Gorev3_BiletDemo {
    public static void main(String[] args) {
        System.out.println("=== 1) Adlandirilmis uretim yollari ===");
        Bilet b1 = Bilet.tam("Trabzon", "Of");
        Bilet b2 = Bilet.ogrenci("Trabzon", "Of");
        Bilet b3 = Bilet.yasli("Trabzon", "Of");
        
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
        System.out.println("Uc yapicinin da imzasi (String, String) olurdu -> DERLENMEZDI.");

        System.out.println("\n=== 2) Kural iceren fabrika: yasaGore ===");
        System.out.println("yas= 12 -> " + Bilet.yasaGore("Of", "Rize", 12));
        System.out.println("yas= 21 -> " + Bilet.yasaGore("Of", "Rize", 21));
        System.out.println("yas= 40 -> " + Bilet.yasaGore("Of", "Rize", 40));
        System.out.println("yas= 70 -> " + Bilet.yasaGore("Of", "Rize", 70));

        System.out.println("\n=== 3) Gecersiz istek ===");
        try {
            Bilet.yasaGore("Of", "Rize", -1);
        } catch (IllegalArgumentException e) {
            System.out.println("RET: " + e.getMessage());
        }

        System.out.println("\n=== 4) Yapici disaridan cagrilamaz ===");
        System.out.println("// new Bilet(\"Of\", \"Rize\", \"TAM\", 400.0);");
        System.out.println("// -> The constructor Bilet(String, String, String, double) is not visible (derleme hatasi)");

        System.out.println("\n=== 5) Bilet numarasi sinifca uretilir ===");
        System.out.println("Uretilen toplam bilet: " + Bilet.uretilenBiletSayisi());
        System.out.println("Numaralar disaridan atanamaz; salt-okunur kimliktir.");
    }
}