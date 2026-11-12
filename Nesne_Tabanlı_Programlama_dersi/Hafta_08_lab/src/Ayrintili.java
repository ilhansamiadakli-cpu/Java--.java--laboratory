public class Ayrintili extends Yayin {
    public Ayrintili(String baslik, int yil) {
        super(baslik, yil);
    }
}

class AyrintiliKitap extends Ayrintili {
    private final String yazar;
    private final int sayfa;

    public AyrintiliKitap(String baslik, int yil, String yazar, int sayfa) {
        super(baslik, yil);
        this.yazar = yazar;
        this.sayfa = sayfa;
    }

    
    @Override
    public String tur() {
        return "Kitap";
    }

    
    @Override
    public int oduncGunu() {
        return sayfa > 600 ? 30 : 21;
    }

    
    @Override
    public String toString() {
        return super.toString() + " | " + yazar + ", " + sayfa + " s.";
    }
}

class AyrintiliDergi extends Ayrintili {
    private final int sayiNo;

    public AyrintiliDergi(String baslik, int yil, int sayiNo) {
        super(baslik, yil);
        this.sayiNo = sayiNo;
    }

    // degistirme: ust sinifin varsayilan tur adi "Dergi" ile degistiriliyor
    @Override
    public String tur() {
        return "Dergi";
    }

    // degistirme: dergi odunc suresi 3 gun olarak degistiriliyor
    @Override
    public int oduncGunu() {
        return 3;
    }

    // genisletme: super.toString() cagriliyor ve uzerine sayi numarasi ekleniyor
    @Override
    public String toString() {
        return super.toString() + " | sayi " + sayiNo;
    }
}

class AyrintiliDVD extends Ayrintili {
    private final int dakika;

    public AyrintiliDVD(String baslik, int yil, int dakika) {
        super(baslik, yil);
        this.dakika = dakika;
    }

    // degistirme: ust sinifin varsayilan tur adi "DVD" ile degistiriliyor
    @Override
    public String tur() {
        return "DVD";
    }

    // degistirme: DVD odunc suresi 2 gun olarak degistiriliyor
    @Override
    public int oduncGunu() {
        return 2;
    }

    // genisletme: super.toString() cagriliyor ve uzerine sure (dk) bilgisi ekleniyor
    @Override
    public String toString() {
        return super.toString() + " | " + dakika + " dk";
    }
}
class EzilmemisOrnek {
}