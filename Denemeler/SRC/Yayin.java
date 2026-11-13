public class Yayin {
    private final String id;
    private String baslik;
    private int yayinYili;

    public Yayin(String id, String baslik, int yayinYili) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID bos olamaz");
        }
        if (baslik == null || baslik.isBlank()) {
            throw new IllegalArgumentException("Baslik bos olamaz");
        }
        this.id = id;
        this.baslik = baslik;
        this.yayinYili = yayinYili;
    }

    public String getId() {
        return id;
    }

    public String getBaslik() {
        return baslik;
    }

    public int getYayinYili() {
        return yayinYili;
    }

    public String kunye() {
        return id + " - " + baslik + " (" + yayinYili + ")";
    }

    public int oduncGunu() {
        return 15; // Varsayılan ödünç süresi 15 gün
    }
}
class Dergi extends Yayin {
    private int sayiNo;

    public Dergi(String id, String baslik, int yayinYili, int sayiNo) {
        super(id, baslik, yayinYili);
        this.sayiNo = sayiNo;
    }

    public int getSayiNo() {
        return sayiNo;
    }

    @Override
    public String kunye() {
        return super.kunye() + " - Sayı No: " + sayiNo;
    }
    @Override 
    public int oduncGunu() {
        return 7; // Dergiler için ödünç süresi 7 gün
    }
}