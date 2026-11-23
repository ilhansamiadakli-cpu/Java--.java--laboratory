class Dosya implements AutoCloseable {
    private String isim;

    public Dosya(String isim) {
        this.isim = isim;
        System.out.println("Açıldı: " + this.isim);
    }

    public void islemYap() {
        System.out.println("İşlem yapılıyor...");
        throw new RuntimeException("Beklenmedik Hata!");
    }

    @Override
    public void close() {
        System.out.println("Kapandı: " + this.isim);
    }
}

public class Main {
    public static void main(String[] args) {
        try (Dosya d = new Dosya("veri.txt")) {
            d.islemYap();
            System.out.println("Try bloğu bitti.");
        } catch (RuntimeException e) {
            System.out.println("Yakalandı: " + e.getMessage());
        }
    }
}