public class Gorev1_CokBicimliDizi {

    static double toplamCeza(Yayin[] raf, int gun) {
        double toplam = 0.0;
        for (Yayin y : raf) {
            toplam += y.gecikmeCezasi(gun);
        }
        return toplam;
    }

    static Yayin enUzunOdunc(Yayin[] raf) {
        Yayin enUzun = raf[0];
        for (Yayin y : raf) {
            if (y.oduncGunu() > enUzun.oduncGunu()) {
                enUzun = y;
            }
        }
        return enUzun;
    }

    static int cezasizSayisi(Yayin[] raf, int gun) {
        int sayac = 0;
        for (Yayin y : raf) {
            // Ceza tam olarak 0.0 sabitiyle donduruldugu icin == 0.0 kullanilabilir;
            // genel kural olarak double degerler == ile degil Math.abs(a - b) < 0.0001 ile karsilastirilir.
            if (y.gecikmeCezasi(gun) == 0.0) {
                sayac++;
            }
        }
        return sayac;
    }

    public static void main(String[] args) {
        Yayin[] raf = {
            new Kitap6("Yapay Zeka", 2021, "Russell", 1152),
            new Kitap6("Kucuk Prens", 1943, "Exupery", 96),
            new Dergi("Bilim Teknik", 2024, 690),
            new DVD("Kosmos", 2014, 780),
            new Yayin("Katalog", 2020)
        };

        System.out.println("=== Gorev 1: Cok bicimli dizi ===");
        System.out.println();
        for (Yayin y : raf) {
            System.out.println("  " + y);
        }
        System.out.println();

        int gun = 25;
        System.out.println("Odunc suresi: " + gun + " gun sonunda iade");
        System.out.println("-------------------------------------------------");
        for (Yayin y : raf) {
            System.out.printf("  %-8s %-16s ceza = %7.2f TL%n", y.tur(), y.getBaslik(), y.gecikmeCezasi(gun));
        }
        System.out.println("-------------------------------------------------");
        System.out.printf("  %-25s %14.2f TL%n", "TOPLAM CEZA", toplamCeza(raf, gun));
        System.out.println("  Cezasiz yayin sayisi    : " + cezasizSayisi(raf, gun));
        Yayin enUzun = enUzunOdunc(raf);
        System.out.println("  En uzun odunc verilebilen: " + enUzun.getBaslik() + " (" + enUzun.oduncGunu() + " gun)");
        System.out.println();
        System.out.println("Not: toplamCeza, enUzunOdunc ve cezasizSayisi metotlarinda");
        System.out.println("Kitap, Dergi veya DVD sozcugu GECMIYOR.");
    }
}

/*
 * KONTROL SORULARI (Gorev 1)
 
 ? Soru 1: Diziye altinci bir tur (SesliKitap) eklemek isteseniz toplamCeza, enUzunOdunc ve cezasizSayisi
 ?         metotlarinda kac satir degisirdi? Bu sayinin adi nedir?
 ! Cevap: 0 (sifir) satir degisirdi. Yeni alt sinif Yayin sozlesmesini uyguladigi surece mevcut metotlar hic
 !        degismeden calisir (Acik-Kapali Ilkesi / Open-Closed Principle - sifir degisiklik maliyeti).
 
 ? Soru 2: Yayin[] raf dizisini Kitap6[] raf olarak bildirseydiniz hangi satir derlenmezdi? Hata iletisi ne olurdu?
 ! Cevap: Diziye Dergi, DVD ve Yayin nesnelerinin eklendigi satirlar derlenmezdi. Derleyici "incompatible types:
 !        Dergi cannot be converted to Kitap6" (DVD ve Yayin icin de ayni sekilde) derleme hatasi verirdi.
 
 ? Soru 3: toplamCeza() metodunda hangi cagrinin hangi govdeye gidecegi ne zaman belirlenir: derleme zamaninda mi,
 ?         calisma zamaninda mi? Yanitinizi gerekcelendiriniz.
 ! Cevap: Calisma zamaninda (runtime) belirlenir. Derleme zamaninda derleyici y referansinin yalnizca Yayin turunde
 !        oldugunu bilir ve Yayin sinifindaki metot imzasini denetler; ancak y.gecikmeCezasi(gun) cagrisinin hangi alt
 !        sinifin govdesini calistiracagi JVM tarafindan calisma aninda nesnenin gercek turune bakilarak secilir
 !        (dinamik baglama / dynamic binding).
 */