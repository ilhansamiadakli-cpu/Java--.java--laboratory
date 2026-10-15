/*
 * Nesne Yonelimli Programlama - Hafta 03
 * Ornek 3c: Sekil'in ucuncu alt sinifi - SONRADAN eklenen tur
 *
 * Bu dosya, SekilKotu.java'daki sorunun cozumunu kanitlar: yeni bir sekil
 * turu eklemek icin VAR OLAN hicbir dosyaya dokunulmaz. Yalnizca bu dosya
 * yazilir ve sistem yeni turu tanir.
 */
public class Ucgen extends Sekil {

    private final double taban;
    private final double yukseklik;

    public Ucgen(double taban, double yukseklik, String renk) {
        super("Dik ucgen", renk);
        this.taban = (taban > 0) ? taban : 1.0;
        this.yukseklik = (yukseklik > 0) ? yukseklik : 1.0;
        if (taban <= 0 || yukseklik <= 0) {
            System.out.println("Uyari: olculer pozitif olmalidir; 1.0 kullanildi.");
        }
    }

    @Override
    public double alan() {
        return taban * yukseklik / 2.0;
    }

    @Override
    public double cevre() {
        return taban + yukseklik + Math.hypot(taban, yukseklik);
    }
}
