/*
 * Nesne Yonelimli Programlama - Hafta 04
 * Ornek 6: DEGISMEZ (immutable) sinif
 *
 * Degismezligin dort kurali bu sinifta uygulanmistir:
 *  1) Sinif final    -> davranis mirasla degistirilemez
 *  2) Alanlar private final
 *  3) Setter yok
 *  4) "Degistiren" metotlar nesneyi degil YENI BIR NESNE dondurur
 *
 * Sonuc: bir ParaBirimi nesnesi paylasilabilir, anahtar olarak kullanilabilir
 * ve hicbir kod onu arkadan degistiremez.
 */
public final class ParaBirimi {

    private final long kurus;      // tam sayi: kayan nokta yuvarlama hatasi yok
    private final String kod;      // "TRY", "USD", ...

    public ParaBirimi(long kurus, String kod) {
        if (kod == null || kod.length() != 3) {
            throw new IllegalArgumentException("Para birimi kodu 3 harf olmalidir.");
        }
        this.kurus = kurus;
        this.kod = kod.toUpperCase();
    }

    public static ParaBirimi lira(double tutar) {
        return new ParaBirimi(Math.round(tutar * 100.0), "TRY");
    }

    public long getKurus() {
        return kurus;
    }

    public String getKod() {
        return kod;
    }

    public double getTutar() {
        return kurus / 100.0;
    }

    /* Degistirmez; YENI nesne dondurur. */
    public ParaBirimi topla(ParaBirimi digeri) {
        birimKontrol(digeri);
        return new ParaBirimi(this.kurus + digeri.kurus, this.kod);
    }

    public ParaBirimi cikar(ParaBirimi digeri) {
        birimKontrol(digeri);
        return new ParaBirimi(this.kurus - digeri.kurus, this.kod);
    }

    public ParaBirimi carp(int katsayi) {
        return new ParaBirimi(this.kurus * katsayi, this.kod);
    }

    private void birimKontrol(ParaBirimi digeri) {
        if (!this.kod.equals(digeri.kod)) {
            throw new IllegalArgumentException(
                    "Farkli para birimleri toplanamaz: " + this.kod + " / " + digeri.kod);
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", getTutar(), kod);
    }
}
