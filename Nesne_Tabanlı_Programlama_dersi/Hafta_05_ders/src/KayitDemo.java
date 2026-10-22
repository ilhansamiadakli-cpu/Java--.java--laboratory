/*
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 05 - Yapici ve Yikici Yontemler
 * Ders ornegi 5
 *
 * Amac: Statik fabrika metotlarinin yapiciya gore uc ustunlugunu gostermek:
 *       adlandirma, nesne uretmeme ozgurlugu, gecersiz istekte denetimli yol.
 */
public class KayitDemo {

    public static void main(String[] args) {
        System.out.println("=== 1) Adlandirilmis uretim yollari ===");
        Kayit k1 = Kayit.olustur("376201", "yzm203", 4);
        Kayit k2 = Kayit.dinleyici("376202", "yzm203");
        System.out.println("  olustur   : " + k1);
        System.out.println("  dinleyici : " + k2);
        System.out.println("  Iki uretim yolunun da ADI var; new Kayit(...) adsizdi.");

        System.out.println();
        System.out.println("=== 2) Metinden uretim ===");
        Kayit k3 = Kayit.metinden("376203;yzm205;3");
        System.out.println("  metinden  : " + k3);

        System.out.println();
        System.out.println("=== 3) Gecersiz istekte BOS nesnesi doner ===");
        Kayit k4 = Kayit.olustur("", "yzm203", 4);
        Kayit k5 = Kayit.olustur("376204", "yzm203", 99);
        System.out.println("  k4 : " + k4 + " -> bosMu=" + k4.bosMu());
        System.out.println("  k5 : " + k5 + " -> bosMu=" + k5.bosMu());
        System.out.println("  Ayni nesne mi? " + (k4 == k5));

        System.out.println();
        System.out.println("=== 4) Fabrika, nesne uretmek ZORUNDA degildir ===");
        Kayit kopya1 = Kayit.kopyasi(k1);
        Kayit kopya2 = Kayit.kopyasi(Kayit.BOS);
        System.out.println("  kopya1 yeni nesne mi? " + (kopya1 != k1));
        System.out.println("  kopya2 == BOS ?       " + (kopya2 == Kayit.BOS));
        System.out.println("  Yapici her cagrida MUTLAKA yeni nesne uretirdi.");

        System.out.println();
        System.out.println("=== 5) Toplam kredi hesabi ===");
        Kayit[] liste = { k1, k2, k3, k4, k5 };
        int toplam = 0;
        for (Kayit k : liste) {
            toplam += k.getKredi();
        }
        System.out.println("  Listedeki kayit sayisi : " + liste.length);
        System.out.println("  Toplam kredi           : " + toplam);
    }
}
