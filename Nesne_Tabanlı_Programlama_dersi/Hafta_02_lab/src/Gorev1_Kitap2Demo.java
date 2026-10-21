public class Gorev1_Kitap2Demo {
    public static void main(String[] args) {
        Kitap2 kitap1 = new Kitap2("Sefiller", "Victor Hugo", 1232, 1862, 25.5);
        Kitap2 kitap2 = new Kitap2(null, null, -100, 1400, -10.0);
        System.out.println(kitap1);
        System.out.println(kitap2);
    }
}
