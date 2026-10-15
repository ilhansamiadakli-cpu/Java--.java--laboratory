public class Gorev2_KanBasinciDemo {

    public static void main(String[] args) {
        KanBasinci olcum = new KanBasinci(118, 76);
        System.out.println("Olcum 1 : " + olcum);
        System.out.println("Nabiz basinci: " + olcum.getNabizBasinci());

        System.out.println();
        System.out.println("--- Tutarsiz istekler ---");
        olcum.ayarla(90, 130);
        olcum.ayarla(300, 80);
        olcum.ayarla(120, 120);
        System.out.println("Degismedi: " + olcum);

        System.out.println();
        System.out.println("--- Gecerli olcumler ---");
        olcum.ayarla(135, 85);
        System.out.println("Olcum 2 : " + olcum);
        olcum.ayarla(152, 96);
        System.out.println("Olcum 3 : " + olcum);
        System.out.println("Nabiz basinci: " + olcum.getNabizBasinci());
    }

    @Override
    public String toString() {
        return "Gorev2_KanBasinciDemo[]";
    }
}