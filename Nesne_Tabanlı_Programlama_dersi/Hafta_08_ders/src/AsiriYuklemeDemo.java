/*
 * Nesne Yonelimli Programlama - Hafta 09
 * Ders Ornegi 3: DERLEME ZAMANI cok bicimliligi (asiri yukleme / overloading).
 *
 * Amac: Ayni ada sahip metotlarin ARGUMAN LISTESINE gore secildigini ve bu
 *       secimin derleyici tarafindan, program calismadan once yapildigini
 *       gostermek. Calisma zamani cok bicimliliginden (ezme) farki budur.
 */
public class AsiriYuklemeDemo {

    static String yazdir(int x) {
        return "yazdir(int)      -> " + x;
    }

    static String yazdir(double x) {
        return "yazdir(double)   -> " + x;
    }

    static String yazdir(String x) {
        return "yazdir(String)   -> " + x;
    }

    static String yazdir(Object x) {
        return "yazdir(Object)   -> " + x;
    }

    static String yazdir(int x, int y) {
        return "yazdir(int,int)  -> " + x + "," + y;
    }

    public static void main(String[] args) {
        System.out.println("== Asiri yukleme: secimi DERLEYICI yapar ==");
        System.out.println(yazdir(7));            // int
        System.out.println(yazdir(7.5));          // double
        System.out.println(yazdir("yedi"));       // String
        System.out.println(yazdir(3, 4));         // int,int

        System.out.println();
        System.out.println("== Genisletme (widening) ile eslesme ==");
        byte b = 5;
        System.out.println(yazdir(b));            // byte -> int genisletilir
        long l = 9L;
        System.out.println(yazdir(l));            // long -> double genisletilir

        System.out.println();
        System.out.println("== BILDIRILEN tur kazanir ==");
        Object nesne = "ben bir String'im";
        // Nesnenin GERCEK turu String; ama degiskenin BILDIRILEN turu Object.
        // Asiri yuklemede secim bildirilen ture gore yapilir:
        System.out.println(yazdir(nesne));        // yazdir(Object) secilir
        System.out.println("Gercek tur: " + nesne.getClass().getSimpleName());
        System.out.println("Yine de yazdir(Object) calisti. Sebep: asiri yukleme");
        System.out.println("cozumlemesi derleme zamanindadir, calisma zamaninda degil.");
    }
}
