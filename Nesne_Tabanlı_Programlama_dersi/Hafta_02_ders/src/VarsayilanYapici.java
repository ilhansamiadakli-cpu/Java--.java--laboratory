/*
 * Nesne Yonelimli Programlama - Hafta 02
 * Ornek 7: Varsayilan yapicinin kaybolmasi
 *
 * Hicbir yapici yazmazsaniz, derleyici sizin icin parametresiz bir
 * "varsayilan yapici" (default constructor) uretir. Ancak KENDINIZ bir
 * yapici yazdiginiz anda derleyici artik varsayilan yapiciyi uretmez.
 */
public class VarsayilanYapici {

    // ---- Durum 1: hic yapici yok -> derleyici varsayilani uretir ----
    static class A {
        int deger;
        // Derleyici gizlice sunu ekler:  A() { }
    }

    // ---- Durum 2: parametreli yapici var -> varsayilan KAYBOLUR ----
    static class B {
        int deger;

        B(int deger) {
            this.deger = deger;
        }
        // new B();  <-- DERLENMEZ: parametresiz yapici artik yok
    }

    // ---- Durum 3: ikisini de acikca yaziyoruz -> ikisi de kullanilabilir ----
    static class C {
        int deger;

        C() {
            this(0);
        }

        C(int deger) {
            this.deger = deger;
        }
    }

    public static void main(String[] args) {
        A a = new A();                 // calisir
        System.out.println("A varsayilan yapici ile olustu, deger = " + a.deger);

        B b = new B(42);               // calisir
        System.out.println("B parametreli yapici ile olustu, deger = " + b.deger);
        // B b2 = new B();             // DERLENMEZ

        C c1 = new C();                // calisir
        C c2 = new C(7);               // calisir
        System.out.println("C: c1.deger = " + c1.deger + ", c2.deger = " + c2.deger);

        System.out.println();
        System.out.println("Alanlarin varsayilan degerleri:");
        Varsayilanlar v = new Varsayilanlar();
        v.yaz();
    }

    /** Ilkel ve referans alanlarin varsayilan degerleri. */
    static class Varsayilanlar {
        int tamSayi;
        double ondalik;
        boolean mantiksal;
        char karakter;
        String metin;
        int[] dizi;

        void yaz() {
            System.out.println("  int     -> " + tamSayi);
            System.out.println("  double  -> " + ondalik);
            System.out.println("  boolean -> " + mantiksal);
            String karakterGosterimi = (karakter == '\u0000')
                    ? "\\u0000 (bos karakter)" : String.valueOf(karakter);
            System.out.println("  char    -> " + karakterGosterimi);
            System.out.println("  String  -> " + metin);
            System.out.println("  int[]   -> " + dizi);
        }
    }
}
