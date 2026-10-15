/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 3: Kapsullenmis Ogrenci sinifinin davranisi
 *
 * Ayni "kotu" atamalar bu kez sinifin kapisinda durdurulur.
 */
public class OgrenciDemo {

    public static void main(String[] args) {
        Ogrenci o = new Ogrenci("376201", "Elif Kaya", 20);
        System.out.println("Baslangic : " + o);

        System.out.println();
        System.out.println("--- Ayni hatali atamalar deneniyor ---");
        o.setYas(-3);
        o.setAd("");
        o.notEkle(350.0);
        System.out.println("Sonra     : " + o);

        System.out.println();
        System.out.println("--- Gecerli islemler ---");
        o.notEkle(78.0);
        o.notEkle(64.0);
        o.setYas(21);
        System.out.printf("Ortalama  : %.2f (%d not)%n", o.getNotOrtalamasi(),
                o.getNotAdedi());
        System.out.println("Gecti mi  : " + o.gecerMi());
        System.out.println("Son durum : " + o);

        System.out.println();
        System.out.println("--- Salt-okunur alan ---");
        System.out.println("Ogrenci no okunabilir : " + o.getOgrenciNo());
        // o.setOgrenciNo("999");  // DERLEME HATASI: boyle bir metot yok
        // o.ogrenciNo = "999";    // DERLEME HATASI: ogrenciNo private
        System.out.println("Yazma yolu ise hic acilmamistir.");
    }
}
