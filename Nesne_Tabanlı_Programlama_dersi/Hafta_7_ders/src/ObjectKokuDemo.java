/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 11 : Object koku, final sinif ve elmas problemine
 *                  Java'nin yaniti
 *
 * Amac : (a) extends yazilmasa bile her sinifin Object'ten
 *            turedigini kanitlamak,
 *        (b) Object'ten miras alinan metotlarin varsayilan
 *            davranisini gostermek,
 *        (c) final sinifin turetilemedigini belirtmek,
 *        (d) coklu SINIF mirasinin yasak, coklu ARAYUZ
 *            gerceklemesinin serbest oldugunu gostermek.
 *
 * Not : Arayuzler Hafta 10'un konusudur; burada yalnizca elmas
 *       probleminin Java'daki cozumunu gormek icin kullanilir.
 * ========================================================== */

/* extends yazilmadi; yine de Object'ten turer. */
class Bos { }

/* final sinif: hicbir sinif bundan turetilemez.
 *   class Bunu extends Kilitli { }   ->  The type Bunu cannot subclass
 *                                        the final class Kilitli       */
final class Kilitli {
    final int deger = 42;
}

/* ---- Elmas problemi ve Java'nin yaniti ----
 * Iki farkli sinifin ayni adli metodunu miras almak istesek
 * hangisinin gecerli olacagi belirsiz kalirdi. Java bu belirsizligi
 * COKLU SINIF MIRASINI YASAKLAYARAK ortadan kaldirir:
 *     class X extends A, B { }   ->  Syntax error
 * Coklu ARAYUZ gerceklemesi serbesttir, cunku arayuzler durum
 * (alan) tasimaz; catisma yalnizca default metotlarda olusabilir ve
 * o durumda derleyici acik cozum ister. */
interface Yazdirilabilir {
    String yazdir();
    default String etiket() { return "[yazdirilabilir]"; }
}

interface Arsivlenebilir {
    String arsivAnahtari();
    default String etiket() { return "[arsivlenebilir]"; }
}

class Rapor implements Yazdirilabilir, Arsivlenebilir {
    private final String konu;

    Rapor(String konu) { this.konu = konu; }

    public String yazdir()       { return "RAPOR: " + konu; }
    public String arsivAnahtari() { return "ARS-" + konu.hashCode(); }

    /* Iki arayuzden ayni adli default metot geldigi icin derleyici
     * bizden acik bir karar ister; hangi surumu istedigimizi
     * Arayuz.super.metot() ile yazariz. */
    @Override
    public String etiket() {
        return Yazdirilabilir.super.etiket() + Arsivlenebilir.super.etiket();
    }
}

public class ObjectKokuDemo {

    public static void main(String[] args) {
        System.out.println("=== 1) Her sinif Object'ten turer ===");
        Bos b = new Bos();
        System.out.println("  Bos sinifinda hicbir uye yazilmadi.");
        System.out.println("  b.getClass().getSimpleName() -> " + b.getClass().getSimpleName());
        System.out.println("  b.getClass().getSuperclass() -> " + b.getClass().getSuperclass());
        System.out.println("  b instanceof Object          -> " + (b instanceof Object));

        System.out.println();
        System.out.println("=== 2) Miras zinciri yukari dogru izlenebilir ===");
        Class<?> c = new Yonetici("Murat Kaya", "32345678903", 1978,
                                  "P-2001", 55000.0, "Kalite", 3).getClass();
        StringBuilder zincir = new StringBuilder();
        while (c != null) {
            zincir.append(c.getSimpleName());
            c = c.getSuperclass();
            if (c != null) {
                zincir.append(" -> ");
            }
        }
        System.out.println("  " + zincir);

        System.out.println();
        System.out.println("=== 3) Object'ten gelen varsayilan davranis ===");
        Bos b2 = new Bos();
        System.out.println("  b.equals(b)  -> " + b.equals(b)
                           + "   (varsayilan: referans esitligi)");
        System.out.println("  b.equals(b2) -> " + b.equals(b2)
                           + "  (alanlar ayni olsa da FALSE)");
        System.out.println("  toString() ezilmezse sinifAdi@hashCode basilir:");
        System.out.println("  b.toString() bicimi -> "
                           + b.toString().replaceAll("@.*", "@<onaltilik hash>"));

        System.out.println();
        System.out.println("=== 4) toString() ezildiginde ===");
        Ogrenci o = new Ogrenci("Elif Demir", "22345678902", 2005,
                                "YZM376014", "Yazilim Muhendisligi");
        System.out.println("  o.toString() -> " + o);

        System.out.println();
        System.out.println("=== 5) final sinif ===");
        Kilitli k = new Kilitli();
        System.out.println("  Kilitli.deger = " + k.deger);
        System.out.println("  'class Bunu extends Kilitli' satiri derlenmezdi:");
        System.out.println("  The type Bunu cannot subclass the final class Kilitli");

        System.out.println();
        System.out.println("=== 6) Elmas problemi : coklu arayuz serbest ===");
        Rapor r = new Rapor("Donem Sonu");
        System.out.println("  r.yazdir()        -> " + r.yazdir());
        System.out.println("  r.arsivAnahtari() -> " + r.arsivAnahtari());
        System.out.println("  r.etiket()        -> " + r.etiket());
        System.out.println("  Iki arayuzden ayni adli default metot geldi;");
        System.out.println("  derleyici acik karar istedi, Arayuz.super ile verdik.");
        System.out.println("  'class X extends A, B' ise SOZDIZIMI HATASIDIR.");
    }
}
