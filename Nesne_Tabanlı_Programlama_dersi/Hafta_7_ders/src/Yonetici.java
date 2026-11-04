/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 4 : IKI SEVIYELI hiyerarsi (Kisi -> Personel -> Yonetici)
 *
 * Amac : Mirasin ZINCIRLENEBILDIGINI gostermek. Yonetici, Personel'in
 *        de Kisi'nin de tum public uyelerine sahiptir. Yapici zinciri
 *        de bu sirayla, EN TEPEDEN asagiya dogru calisir.
 *
 * Ayrica : final metot ornegi. sicilKodu() metodu final'dir; alt
 *          siniflar onu ezemez. Boylece kurumsal kimlik bicimi
 *          hiyerarsinin hicbir yerinde bozulamaz.
 * ========================================================== */

public class Yonetici extends Personel {

    private final String birim;
    private int astSayisi;

    public Yonetici(String ad, String tcNo, int dogumYili, String sicilNo,
                    double aylikBrut, String birim, int astSayisi) {
        // Ust sinifin yapicisina unvani sabit gecirebiliriz.
        super(ad, tcNo, dogumYili, sicilNo, "Yonetici", aylikBrut);
        System.out.println("    [Yonetici yapicisi calisti]");
        this.birim     = birim;
        this.astSayisi = Math.max(0, astSayisi);
    }

    public String getBirim()   { return birim; }
    public int    getAstSayisi() { return astSayisi; }

    public void astEkle(int adet) {
        if (adet <= 0) {
            System.out.println("  UYARI: eklenecek ast sayisi pozitif olmalidir.");
            return;
        }
        astSayisi += adet;
    }

    /** Yonetim tazminati: ast basina brutun %2'si, en cok %20. */
    public double yonetimTazminati() {
        double oran = Math.min(0.20, 0.02 * astSayisi);
        return getAylikBrut() * oran;
    }

    /* Personel.aylikNet() metodunu eziyoruz; super.aylikNet() ile
     * temel hesabi tekrar yazmadan kullanip tazminati ekliyoruz. */
    @Override
    public double aylikNet() {
        return super.aylikNet() + yonetimTazminati();
    }

    @Override
    public String kimlikKarti() {
        return super.kimlikKarti() + " | " + birim + " birimi, " + astSayisi + " ast";
    }

    @Override
    public String toString() {
        return "Yonetici{" + getAd() + ", " + birim + ", ast=" + astSayisi + "}";
    }
}
