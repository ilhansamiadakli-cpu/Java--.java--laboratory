/*
 * Nesne Yonelimli Programlama - Hafta 02
 * Ornek 1b: Yapici metot olmadan ortaya cikan sorun
 *
 * Bu program, yapici metodun neden gerekli oldugunu gosterir.
 */
public class SorunGosterimi {

    public static void main(String[] args) {

        // --- Dogru kullanim: tum alanlar atanmis ---
        OgrenciEski o1 = new OgrenciEski();
        o1.ad = "Ali";
        o1.soyad = "Yilmaz";
        o1.numara = 331001;
        o1.vizeNotu = 65;
        o1.finalNotu = 78;
        o1.bilgiYaz();

        // --- SORUN 1: alanlar atanmadan nesne kullanilabiliyor ---
        OgrenciEski o2 = new OgrenciEski();
        System.out.print("Hicbir alani atanmamis nesne: ");
        o2.bilgiYaz();          // ad ve soyad null, notlar 0.0

        // --- SORUN 2: bir alan unutulursa hicbir uyari yok ---
        OgrenciEski o3 = new OgrenciEski();
        o3.ad = "Ayse";
        o3.soyad = "Demir";
        o3.numara = 331002;
        o3.vizeNotu = 70;
        // o3.finalNotu = 85;   <-- UNUTULDU! Derleyici uyarmaz.
        System.out.print("Final notu unutulmus nesne : ");
        o3.bilgiYaz();          // ortalama yanlis hesaplanir

        // --- SORUN 3: gecersiz deger atanabiliyor ---
        OgrenciEski o4 = new OgrenciEski();
        o4.ad = "Mehmet";
        o4.soyad = "Kaya";
        o4.numara = -55;        // negatif numara!
        o4.vizeNotu = 250;      // 100'den buyuk not!
        o4.finalNotu = -40;     // negatif not!
        System.out.print("Gecersiz degerli nesne     : ");
        o4.bilgiYaz();
    }
}
