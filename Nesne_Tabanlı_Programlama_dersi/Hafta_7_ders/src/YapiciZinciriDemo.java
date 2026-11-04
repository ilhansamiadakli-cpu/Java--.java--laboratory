/* ==========================================================
 * Nesne Yonelimli Programlama - KTU Of Teknoloji Fakultesi
 * Hafta 07 : Miras (Inheritance)
 * Ders ornegi 6 : Yapici zincirinin ve alan baslatma sirasinin
 *                 adim adim gozlemlenmesi
 *
 * Amac : Hafta 5'te tek sinif icin ogrendigimiz baslatma sirasini
 *        (alan -> baslatma blogu -> yapici) MIRAS ile genisletmek.
 *        Bir alt sinif nesnesi olustururken sira sudur:
 *
 *          1. En UST sinifin alanlari + baslatma bloklari
 *          2. En UST sinifin yapici govdesi
 *          3. Bir alt seviyenin alanlari + baslatma bloklari
 *          4. Bir alt seviyenin yapici govdesi
 *          ... en alta kadar
 *
 * Not : super(...) yazilmazsa derleyici sessizce super() ekler.
 *       Ust sinifta parametresiz yapici yoksa bu derleme hatasidir.
 * ========================================================== */

class A_Ust {
    int alanA = yaz("1. A_Ust alani (alanA = 10)", 10);

    static { System.out.println("0a. A_Ust static blogu (sinif yuklenirken, bir kez)"); }

    { System.out.println("2. A_Ust baslatma blogu"); }

    A_Ust() {
        System.out.println("3. A_Ust() yapici govdesi");
    }

    static int yaz(String mesaj, int deger) {
        System.out.println(mesaj);
        return deger;
    }
}

class B_Orta extends A_Ust {
    int alanB = yaz("4. B_Orta alani (alanB = 20)", 20);

    static { System.out.println("0b. B_Orta static blogu"); }

    { System.out.println("5. B_Orta baslatma blogu"); }

    B_Orta() {
        // super(); derleyici tarafindan buraya eklenir.
        System.out.println("6. B_Orta() yapici govdesi");
    }
}

class C_Alt extends B_Orta {
    int alanC = yaz("7. C_Alt alani (alanC = 30)", 30);

    static { System.out.println("0c. C_Alt static blogu"); }

    { System.out.println("8. C_Alt baslatma blogu"); }

    C_Alt() {
        System.out.println("9. C_Alt() yapici govdesi");
    }
}

public class YapiciZinciriDemo {

    public static void main(String[] args) {
        System.out.println("--- new C_Alt() cagriliyor ---");
        C_Alt c = new C_Alt();
        System.out.println("--- nesne hazir: alanA=" + c.alanA
                           + " alanB=" + c.alanB + " alanC=" + c.alanC + " ---");

        System.out.println();
        System.out.println("--- ikinci nesne: static bloklar TEKRAR calismaz ---");
        new C_Alt();

        System.out.println();
        System.out.println("--- yalnizca B_Orta olusturulursa C_Alt hic devreye girmez ---");
        new B_Orta();
    }
}
