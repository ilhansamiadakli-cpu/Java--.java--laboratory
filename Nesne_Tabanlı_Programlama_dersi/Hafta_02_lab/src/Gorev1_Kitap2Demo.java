public class Gorev1_Kitap2Demo {
    public static void main(String[] args) {
        // 1. Temel nesne ve kopya yapici testi
        Kitap2 k1 = new Kitap2("Suç ve Ceza", "Dostoyevski", 687, 1866, 240.50);
        Kitap2 kopya = new Kitap2(k1);

        System.out.println("Kopya yapici testi");
        System.out.println("k1 == kopya -> " + (k1 == kopya)); // Heap adresleri farkli oldugu icin false basar

        // 2. Farkli yapicilarla baslatilmis kitap dizisi (raf)
        Kitap2[] raf = {
            k1,
            kopya,
            new Kitap2("1984", "George Orwell", 328, 1949),           // 4 parametreli (fiyat: 0.0)
            new Kitap2("Dönüşüm", "Franz Kafka"),                       // 2 parametreli (varsayilan degerler)
            new Kitap2("Nutuk", "Mustafa Kemal Atatürk", 600, 1927, 350.00) // Tam yapici
        };

        // 3. Toplam raf degeri ve en pahali kitabi bulma dongusu
        double toplamDeger = 0.0;
        Kitap2 enPahali = raf[0]; // Baslangic adayi olarak ilk kitabi seciyoruz

        for (Kitap2 k : raf) {
            toplamDeger += k.getFiyat();

            if (k.getFiyat() > enPahali.getFiyat()) {
                enPahali = k;
            }
        }

        // 4. Sonuclari yazdirma
        System.out.println("\n--- Raf Bilgileri ---");
        System.out.printf("Raf toplam degeri : %.2f TL\n", toplamDeger);
        System.out.println("En pahali kitap   : " + enPahali.getBaslik());
    }
}