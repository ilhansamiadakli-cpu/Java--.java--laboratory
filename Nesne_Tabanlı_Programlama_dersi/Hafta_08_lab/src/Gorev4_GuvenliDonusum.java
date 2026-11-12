public class Gorev4_GuvenliDonusum {

    static String korumasiz(Yayin y) {
        Kitap6 k = (Kitap6) y;
        return "sayfa=" + k.getSayfa();
    }

    static String klasik(Yayin y) {
        if (y instanceof Kitap6) {
            Kitap6 k = (Kitap6) y;
            return "sayfa=" + k.getSayfa();
        } else if (y instanceof Dergi) {
            Dergi d = (Dergi) y;
            return "sayi=" + d.getSayiNo();
        } else if (y instanceof DVD) {
            DVD dvd = (DVD) y;
            return "sure=" + dvd.getDakika() + " dk";
        }
        return "ek bilgi yok";
    }

    static String desenEsleme(Yayin y) {
        if (y instanceof Kitap6 k) return "sayfa=" + k.getSayfa();
        else if (y instanceof Dergi d) return "sayi=" + d.getSayiNo();
        else if (y instanceof DVD dvd) return "sure=" + dvd.getDakika() + " dk";
        return "ek bilgi yok";
    }

    public static void main(String[] args) {
        Yayin[] raf = {
            new Kitap6("Yapay Zeka", 2021, "Russell", 1152),
            new Dergi("Bilim Teknik", 2024, 690),
            new DVD("Kosmos", 2014, 780),
            new Yayin("Katalog", 2020)
        };

        System.out.println("=== Gorev 4: Guvenli asagi donusum ===");
        System.out.println();

        System.out.println("[1] Korumasiz downcast");
        for (Yayin y : raf) {
            try {
                System.out.printf("  %-8s -> %s%n", y.tur(), korumasiz(y));
            } catch (ClassCastException e) {
                System.out.printf("  %-8s -> HATA: ClassCastException%n", y.tur());
            }
        }
        System.out.println("  Uc satirda istisna firladi. Derleyici uyarmadi.");
        System.out.println();

        System.out.println("[2] Klasik instanceof + cast");
        for (Yayin y : raf) {
            System.out.printf("  %-8s -> %s%n", y.tur(), klasik(y));
        }
        System.out.println();

        System.out.println("[3] Desen eslemeli instanceof (Java 16+)");
        for (Yayin y : raf) {
            System.out.printf("  %-8s -> %s%n", y.tur(), desenEsleme(y));
        }
        System.out.println();

        boolean esit = true;
        for (Yayin y : raf) {
            if (!klasik(y).equals(desenEsleme(y))) {
                esit = false;
            }
        }

        System.out.println("[4] Iki yol ayni sonucu veriyor mu?");
        System.out.println("  Sonuc: " + (esit ? "EVET, birebir ayni" : "HAYIR"));
        System.out.println();

        System.out.println("[5] Ama asil soru: bu instanceof zinciri gerekli mi?");
        System.out.println("  Gerekli degil. Alt siniflara ortak bir ekBilgi()");
        System.out.println("  metodu ezdirilirse zincir tamamen kaybolur.");
        System.out.println("  instanceof zinciri, cogu zaman EKSIK bir sozlesmenin");
        System.out.println("  belirtisidir. Gorev 5'te bunu duzeltecegiz.");
    }
}

/*
 * KONTROL SORULARI (Gorev 4)
 
 ? Soru 1: korumasiz() metodundaki donusum neden derleme hatasi vermiyor? Derleyici hangi bilgiye sahip degil?
 ! Cevap: Kitap6 sinifi Yayin sinifinin bir alt sinifi oldugu icin derleyici bir Yayin referansinin calisma zamaninda
 !        bir Kitap6 nesnesini gosterebilme ihtimalini gecerli kabul eder. Derleyici yalnizca referansin statik turunu
 !        (Yayin) bilir; referansin bellekte gercekte hangi alt sinif nesnesini gosterdigi bilgisine (dinamik ture)
 !        sahip degildir, bunu ancak JVM calisma aninda denetleyebilir.
 
 ? Soru 2: Desen eslemeli bicimde bildirilen degiskenin (Kitap6 k) kapsami nedir? if blogunun disinda kullanmaya calisirsaniz ne olur?
 ! Cevap: Desen degiskeni k'nin kapsami yalnizca eslesmenin dogru (true) oldugu ilgili if daliyla sinirlidir.
 !        if blogunun disinda k degiskenini kullanmaya calisirsaniz derleyici degiskeni taniyamaz ve
 !        "cannot find symbol" derleme hatasi verir.
 
 ? Soru 3: [5] bolumunde onerilen ekBilgi() cozumunu uygulasaydiniz klasik() ve desenEsleme() metotlarina ne olurdu?
 ?         Bu, hangi tasarim ilkesinin uygulanmasidir?
 ! Cevap: Her iki metottaki instanceof zincirlerine ve asagi donusumlere (cast) hic gerek kalmazdi; tamamini tek bir
 !        y.ekBilgi() polimorfik metot cagrisi alirdi. Bu yaklasim "Sorgulama, Soyle" (Tell, Don't Ask) ve
 !        Acik-Kapali Ilkesi (Open-Closed Principle / OCP) tasarim ilkelerinin uygulanmasidir.
 */