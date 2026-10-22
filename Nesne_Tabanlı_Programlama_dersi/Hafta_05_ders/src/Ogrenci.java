/*
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 05 - Yapici ve Yikici Yontemler
 * Ders ornegi 9
 *
 * Amac: Donem boyunca gelistirilen Ogrenci sinifinin Hafta 5 surumu.
 *       Hafta 4'te alanlari private yapip setter'lara dogrulama koymustuk.
 *       Bu hafta:
 *         - uc yapici tek bir ANA yapicida birlesiyor (this(...) zinciri),
 *         - kopya yapici eklendi ve DERIN kopya yapiyor,
 *         - gecersiz istekte nesne artik hic dogmuyor (istisna firlatiliyor).
 */
public class Ogrenci {

    public static final int EN_KUCUK_YAS = 17;
    public static final int EN_BUYUK_YAS = 70;
    public static final int VARSAYILAN_YAS = 18;

    private final String ogrenciNo;
    private String ad;
    private int yas;
    private int[] notlar;
    private int notAdedi;

    /* --- 1. En kisa bicim --- */
    public Ogrenci(String ogrenciNo, String ad) {
        this(ogrenciNo, ad, VARSAYILAN_YAS);
    }

    /* --- 2. Yas verilen bicim --- */
    public Ogrenci(String ogrenciNo, String ad, int yas) {
        this(ogrenciNo, ad, yas, new int[10]);
    }

    /* --- 3. ANA YAPICI: butun dogrulama burada, tek kopya --- */
    public Ogrenci(String ogrenciNo, String ad, int yas, int[] notlar) {
        if (ogrenciNo == null || ogrenciNo.isBlank()) {
            throw new IllegalArgumentException("Ogrenci numarasi bos olamaz");
        }
        if (ad == null || ad.isBlank()) {
            throw new IllegalArgumentException("Ad bos olamaz");
        }
        if (yas < EN_KUCUK_YAS || yas > EN_BUYUK_YAS) {
            throw new IllegalArgumentException(
                    "Yas " + EN_KUCUK_YAS + ".." + EN_BUYUK_YAS + " araliginda olmali: " + yas);
        }
        this.ogrenciNo = ogrenciNo.trim();
        this.ad = ad.trim();
        this.yas = yas;
        this.notlar = notlar.clone();          // disaridaki dizi ile bagi kes
        this.notAdedi = 0;
    }

    /* --- 4. KOPYA YAPICI: derin kopya --- */
    public Ogrenci(Ogrenci kaynak) {
        if (kaynak == null) {
            throw new IllegalArgumentException("Kopyalanacak ogrenci null olamaz");
        }
        this.ogrenciNo = kaynak.ogrenciNo;
        this.ad = kaynak.ad;
        this.yas = kaynak.yas;
        this.notlar = kaynak.notlar.clone();   // DERIN: yeni dizi uretilir
        this.notAdedi = kaynak.notAdedi;
    }

    public String getOgrenciNo() { return ogrenciNo; }
    public String getAd()        { return ad; }
    public int getYas()          { return yas; }
    public int getNotAdedi()     { return notAdedi; }

    public void setAd(String ad) {
        if (ad == null || ad.isBlank()) {
            System.out.println("  RED: ad bos olamaz");
            return;
        }
        this.ad = ad.trim();
    }

    public void setYas(int yas) {
        if (yas < EN_KUCUK_YAS || yas > EN_BUYUK_YAS) {
            System.out.println("  RED: gecersiz yas " + yas);
            return;
        }
        this.yas = yas;
    }

    public boolean notEkle(int not) {
        if (not < 0 || not > 100) {
            System.out.println("  RED: not 0-100 araliginda olmali: " + not);
            return false;
        }
        if (notAdedi >= notlar.length) {
            System.out.println("  RED: not listesi dolu");
            return false;
        }
        notlar[notAdedi++] = not;
        return true;
    }

    public double getNotOrtalamasi() {
        if (notAdedi == 0) {
            return 0.0;
        }
        int toplam = 0;
        for (int i = 0; i < notAdedi; i++) {
            toplam += notlar[i];
        }
        return (double) toplam / notAdedi;
    }

    public int[] getNotlar() {
        int[] kopya = new int[notAdedi];
        System.arraycopy(notlar, 0, kopya, 0, notAdedi);
        return kopya;
    }

    @Override
    public String toString() {
        return String.format("Ogrenci[%s, %s, %d yas, %d not, ort=%.2f]",
                ogrenciNo, ad, yas, notAdedi, getNotOrtalamasi());
    }
}
