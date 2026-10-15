public class Gorev3_UyeDemo {

    public static void main(String[] args) {
        Uye a = new Uye("2026", "Mert Aydin");
        Uye b = new Uye("2026", "Zeynep Sari");
        Uye c = new Uye("2026", "");

        System.out.println("a : " + a);
        System.out.println("b : " + b);
        System.out.println("c : " + c);
        System.out.println("Numaralar sinif tarafindan uretildi, disaridan alinmadi.");

        System.out.println();
        System.out.println("--- Puan islemleri ---");
        a.puanEkle(120);
        System.out.println("a : " + a);
        a.puanEkle(400);
        System.out.println("a : " + a);
        a.puanHarca(700);
        a.puanHarca(20);
        System.out.println("a : " + a);

        System.out.println();
        System.out.println("--- Salt-okunur alan denemesi ---");
        System.out.println("a.getUyeNo() = " + a.getUyeNo() + " (yalnizca okunabilir)");
        c.setAd("Deniz Ak");
        System.out.println("c : " + c);
    }

    @Override
    public String toString() {
        return "Gorev3_UyeDemo[]";
    }
}