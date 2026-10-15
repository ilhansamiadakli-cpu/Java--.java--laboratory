import java.util.Locale;

public class SesliHarfler {
    public static void main(String[] args) {
        String mesaj = "Merhaba, Nasılsınız?".toLowerCase(new Locale("tr", "TR"));
        int inceHarfSayisi = 0;
        int kalinHarfSayisi = 0;

        for (int i = 0; i < mesaj.length(); i++) {
            char harf = mesaj.charAt(i);

            if ("eiöü".indexOf(harf) >= 0) {
                inceHarfSayisi++;
            } else if ("aıou".indexOf(harf) >= 0) {
                kalinHarfSayisi++;
            }
        }

        System.out.println("Metindeki ince sesli harf sayısı: " + inceHarfSayisi);
        System.out.println("Metindeki kalın sesli harf sayısı: " + kalinHarfSayisi);
    }
}