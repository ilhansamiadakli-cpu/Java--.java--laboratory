/*
 * Nesne Yonelimli Programlama - Hafta 03
 * Ornek 1: Dort ilkeden ONCE - tip kodu ve switch yaklasimi
 *
 * Hafta 02'de TEK bir sinifi duzgun yazmayi ogrendik: private alanlar,
 * yapici, getter/setter. Ancak ayni programda BIRDEN COK TURDE varlik
 * bulundugunda bu yaklasim cabuk bozulur. Bu dosya bozulmayi gosterir.
 */
public class SekilKotu {

    // Tip kodu: nesnenin "ne oldugunu" siradan bir metin alani tutuyor.
    private String tur;
    private double a;   // daire: yaricap | kare: kenar | dikdortgen: genislik
    private double b;   // yalnizca dikdortgende anlamli; digerlerinde olu alan

    public SekilKotu(String tur, double a, double b) {
        this.tur = tur;
        this.a = a;
        this.b = b;
    }

    public double alan() {
        switch (tur) {
            case "daire":      return Math.PI * a * a;
            case "kare":       return a * a;
            case "dikdortgen": return a * b;
            default:           return 0.0;   // sessiz yanlis sonuc
        }
    }

    public double cevre() {
        switch (tur) {
            case "daire":      return 2 * Math.PI * a;
            case "kare":       return 4 * a;
            case "dikdortgen": return 2 * (a + b);
            default:           return 0.0;
        }
    }

    public String tanit() {
        switch (tur) {
            case "daire":      return "Daire(r=" + a + ")";
            case "kare":       return "Kare(k=" + a + ")";
            case "dikdortgen": return "Dikdortgen(" + a + "x" + b + ")";
            default:           return "Bilinmeyen sekil";
        }
    }

    public static void main(String[] args) {
        SekilKotu[] liste = {
            new SekilKotu("daire", 3.0, 0.0),
            new SekilKotu("kare", 4.0, 0.0),
            new SekilKotu("dikdortgen", 5.0, 2.0),
            new SekilKotu("ucgen", 6.0, 4.0)      // YENI tur: hicbir switch bilmiyor
        };

        System.out.println("--- Tip kodu + switch yaklasimi ---");
        for (SekilKotu s : liste) {
            System.out.printf("%-22s alan=%8.3f  cevre=%8.3f%n",
                              s.tanit(), s.alan(), s.cevre());
        }

        // Anlamsiz nesne uretmek serbest: kare icin ikinci olcu verilebiliyor.
        SekilKotu tuhaf = new SekilKotu("kare", 4.0, 999.0);
        System.out.println();
        System.out.println("Anlamsiz nesne: " + tuhaf.tanit()
                           + " -> b alani 999.0 ama hic kullanilmiyor");
        System.out.println("Ucgen icin alan 0.000 dondu: HATA YOK, sessizce yanlis.");
        System.out.println("Yeni tur eklemek icin UC ayri switch degistirilmeliydi.");
    }
}
