public class Gorev4_CokBicimlilikDemo {

    public static void listele(Kisi[] kayitlar) {
        for (Kisi k : kayitlar) {
            System.out.println(k.tanit()); // Dinamik baglama (Dynamic dispatch)
        }
    }

    public static void main(String[] args) {
        Kisi[] kayitlar = new Kisi[] {
            new Kisi("Zeynep Kaya", "12345678901", 1999),
            new Ogrenci("Emre Demir", "98765432109", 2005, 376512, 3.42),
            new Ogrenci("Sena Ak", "11122233344", 2004, 376498, 2.10),
            new Personel("Murat Ozturk", "55566677788", 1985, "Teknisyen", 12)
        };

        System.out.println("-- 1) Dinamik baglama --");
        listele(kayitlar);

        System.out.println("\n-- 2) Statik tur / dinamik tur --");
        Kisi ref = kayitlar[1];
        System.out.println("Bildirim turu (statik): " + Kisi.class.getSimpleName());
        System.out.println("Gercek tur (dinamik)  : " + ref.getClass().getSimpleName());

        System.out.println("\n-- 3) instanceof ile guvenli asagi donusum --");
        int onurSayisi = 0;
        for (Kisi k : kayitlar) {
            if (k instanceof Ogrenci) {
                Ogrenci o = (Ogrenci) k; // Guvenli asagi donusum
                if (o.onurListesinde()) {
                    onurSayisi++;
                }
            }
        }
        System.out.println("Onur listesindeki ogrenci sayisi: " + onurSayisi);

        System.out.println("\n-- 4) Ust tur referansi neyi GOREMEZ? --");
        System.out.println("// ref.getOrtalama(); DERLENMEZ -> Kisi'de boyle bir uye yok");
        System.out.println("Cozum: once instanceof, sonra donusum.");
    }
}