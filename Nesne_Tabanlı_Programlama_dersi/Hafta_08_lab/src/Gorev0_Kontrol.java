public class Gorev0_Kontrol {
    public static void main(String[] args) {
        System.out.println("=== Hafta 09 Laboratuvari - Ortam Kontrolu ===");
        System.out.println("Java surumu   : " + System.getProperty("java.version"));
        System.out.println("Isletim sist. : " + System.getProperty("os.name"));
        System.out.println();

        Kitap6 k = new Kitap6("Yapay Zeka", 2021, "Russell", 1152);
        Yayin y = k;

        System.out.println("[1] Miras kuruldu mu?");
        System.out.println("    Kitap bir Yayin mi? " + (k instanceof Yayin));
        System.out.println();

        System.out.println("[2] Ezme calisiyor mu?");
        System.out.println("    y.tur()  = " + y.tur());
        System.out.println("    Beklenen = Kitap");
        System.out.println("    Sonuc    : " + ("Kitap".equals(y.tur()) ? "GECTI" : "KALDI"));
        System.out.println();

        System.out.println("[3] toString ezildi mi?");
        System.out.println("    " + y);
        System.out.println();

        Yayin[] raf = {
            k,
            new Dergi("Bilim Teknik", 2024, 690),
            new DVD("Kosmos", 2014, 780)
        };

        System.out.println("[4] Cok bicimli dizi kurulabiliyor mu?");
        System.out.println("    Raftaki yayin sayisi: " + raf.length);
        for (Yayin yayin : raf) {
            System.out.println("      - " + yayin.tur() + " / " + yayin.getBaslik());
        }
        System.out.println();
        System.out.println("Tum kontroller tamam. Goreve baslayabilirsiniz.");
    }
}

/*
 * KONTROL SORULARI (Gorev 0)
 
 ? Soru 1: Yayin sinifindaki tur() metodunu static yapsaydiniz ciktinin hangi satirlari degisirdi? Neden?
 ! Cevap: [2], [3] ve [4] numarali bolumlerdeki ciktilar degisirdi. [2]'de y.tur() "Kitap" yerine "Yayin" donerdi
 !        ve Sonuc "KALDI" olurdu; [3]'te toString() icindeki tur() cagrisi "Yayin" yazdirirdi; [4]'te ise dizideki
 !        tum elemanlarin turu "Yayin" olarak gorunurdu. Cunku static metotlar ezilemez (override edilemez),
 !        dinamik baglama yerine derleme zamaninda referansin statik turune (Yayin) gore baglanir.
 
 ? Soru 2: Yayin y = k; satirinda bir tur donusumu yazmadiniz. Bu donusumun adi nedir ve neden acik bir cast gerektirmiyor?
 ! Cevap: Bu donusumun adi yukari donusumdur (upcasting). Kitap6 sinifi Yayin sinifindan turedigi icin her Kitap6 nesnesi
 !        ayni zamanda bir Yayin nesnesidir ("is-a" iliskisi). Alt turden ust ture gecis her zaman guvenli oldugundan
 !        derleyici bunu otomatik yapar ve acik bir cast gerektirmez.
 */