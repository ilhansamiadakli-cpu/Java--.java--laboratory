package kutuphane;

public class Katalog {
    private final Kitap[] kitaplar;
    private int adet;

    public Katalog(int kapasite) {
        this.kitaplar = new Kitap[kapasite > 0 ? kapasite : 10];
        this.adet = 0;
    }

    public void ekle(Kitap k) {
        if (k == null) {
            System.out.println("  RED: bos kitap eklenemez.");
            return;
        }
        if (this.adet >= this.kitaplar.length) {
            System.out.println("  RED: katalog dolu.");
            return;
        }
        this.kitaplar[this.adet++] = k;
    }

    public Kitap bul(String isbn) {
        if (isbn == null) {
            return null;
        }
        for (int i = 0; i < this.adet; i++) {
            if (this.kitaplar[i].getIsbn().equals(isbn)) {
                return this.kitaplar[i];
            }
        }
        return null;
    }

    public boolean oduncVer(String isbn) {
        Kitap k = bul(isbn);
        if (k == null) {
            System.out.println("  RED: " + isbn + " katalogda yok.");
            return false;
        }
        if (k.oduncte) {
            System.out.println("  RED: " + k.getBaslik() + " zaten oduncte.");
            return false;
        }
        k.oduncte = true;
        System.out.println("  OK : " + k.getBaslik() + " odunc verildi.");
        return true;
    }

    public boolean iadeAl(String isbn) {
        Kitap k = bul(isbn);
        if (k == null) {
            System.out.println("  RED: " + isbn + " katalogda yok.");
            return false;
        }
        if (!k.oduncte) {
            System.out.println("  RED: " + k.getBaslik() + " zaten rafta.");
            return false;
        }
        k.oduncte = false;
        System.out.println("  OK : " + k.getBaslik() + " iade alindi.");
        return true;
    }

    public int getRaftakiSayisi() {
        int sayac = 0;
        for (int i = 0; i < this.adet; i++) {
            if (!this.kitaplar[i].oduncte) {
                sayac++;
            }
        }
        return sayac;
    }

    public void listele() {
        for (int i = 0; i < this.adet; i++) {
            System.out.println("  " + this.kitaplar[i]);
        }
    }

    @Override
    public String toString() {
        return "Katalog[toplam=" + this.adet + ", rafta=" + getRaftakiSayisi() + "]";
    }
}