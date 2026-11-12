/*
 * Nesne Yonelimli Programlama - Hafta 09
 * Ders Ornegi 8: Object'ten devralinan toString, equals ve hashCode metotlarinin
 *                dogru ezilmesi.
 *
 * Amac: equals'i ezmenin neden bir COK BICIMLILIK konusu oldugunu gostermek.
 *       Object.equals referans karsilastirir; biz onu DEGER karsilastirmasiyla
 *       degistiriyoruz. == operatoru ise ezilemez, hep referans karsilastirir.
 */
public class EsitlikDemo {

    public static void main(String[] args) {
        System.out.println("== 1. Ezilmemis sinif: Ham ==");
        Ham h1 = new Ham(3, 4);
        Ham h2 = new Ham(3, 4);
        // Object.toString adres icerdiginden cikti her calistirmada degisir;
        // bu yuzden yalnizca BICIMINI yazdiriyoruz.
        System.out.println("  h1        : " + h1.toString().split("@")[0] + "@<adres>");
        System.out.println("  h1 == h2  : " + (h1 == h2));
        System.out.println("  h1.equals : " + h1.equals(h2));
        System.out.println("  Iki nesne ayni degerde ama esit sayilmadi.");

        System.out.println();
        System.out.println("== 2. Ezilmis sinif: Nokta ==");
        Nokta n1 = new Nokta(3, 4);
        Nokta n2 = new Nokta(3, 4);
        Nokta n3 = n1;
        System.out.println("  n1        : " + n1);
        System.out.println("  n1 == n2  : " + (n1 == n2)
                           + "     (== ezilemez, hep referans bakar)");
        System.out.println("  n1.equals : " + n1.equals(n2)
                           + "      (equals ezildi, deger bakar)");
        System.out.println("  n1 == n3  : " + (n1 == n3)
                           + "      (ayni nesne)");

        System.out.println();
        System.out.println("== 3. equals sozlesmesi ==");
        System.out.println("  yansimali  n1.equals(n1) : " + n1.equals(n1));
        System.out.println("  simetrik   n1.equals(n2) : " + n1.equals(n2)
                           + " / n2.equals(n1) : " + n2.equals(n1));
        System.out.println("  null-guvenli n1.equals(null) : " + n1.equals(null));
        System.out.println("  farkli tur n1.equals(\"3,4\") : " + n1.equals("3,4"));

        System.out.println();
        System.out.println("== 4. hashCode neden equals ile birlikte ezilir ==");
        System.out.println("  n1.hashCode() : " + n1.hashCode());
        System.out.println("  n2.hashCode() : " + n2.hashCode());
        System.out.println("  h1.hashCode() ve h2.hashCode() FARKLIDIR:");
        System.out.println("    esit mi? " + (h1.hashCode() == h2.hashCode()));
        System.out.println("  Esit nesnelerin hashCode'u AYNI olmak zorundadir;");
        System.out.println("  aksi halde HashMap/HashSet nesneyi kaybeder (Hafta 13).");

        System.out.println();
        System.out.println("== 5. Cok bicimli equals cagrisi ==");
        Object o1 = n1;                     // statik tur Object
        System.out.println("  Object o1 = n1;  o1.equals(n2) : " + o1.equals(n2));
        System.out.println("  Statik tur Object olsa da Nokta.equals calisti.");
    }
}

// equals/hashCode/toString EZILMEMIS sinif
class Ham {
    final int x;
    final int y;

    Ham(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

// equals/hashCode/toString DOGRU ezilmis sinif
class Nokta {
    private final int x;
    private final int y;

    Nokta(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {                    // 1. hizli yol
            return true;
        }
        if (!(o instanceof Nokta)) {        // 2. null ve tur denetimi birlikte
            return false;
        }
        Nokta d = (Nokta) o;                // 3. guvenli downcast
        return x == d.x && y == d.y;        // 4. anlamli alanlar
    }

    @Override
    public int hashCode() {
        return 31 * x + y;                  // equals ile ayni alanlardan uretilir
    }

    @Override
    public String toString() {
        return "Nokta(" + x + ", " + y + ")";
    }
}
