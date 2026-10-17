
public class Gorev1_Kimlik {
    public  static  void main(String[] args){
        String isim = "ilhan sami adaklı";
        int no = 455116;
        String Bolum = "Yazılım Mühendisliği";
        Double ortalama = 2.5;
        System.out.printf("+---------------------------------------+%n");
        System.out.printf("| %-38s|%n","ÖĞRENCİ KİMLİK KARTI");
        System.out.printf("+---------------------------------------+%n");
        System.out.printf("| Ad Soyad : %-27s|%n",isim);
        System.out.printf("| Numara   : %-27d|%n",no);
        System.out.printf("| Bolum    : %-27s|%n",Bolum);
        System.out.printf("| Ortalama : %-27f|%n",ortalama);
        System.out.printf("+---------------------------------------+%n");

    }


}
