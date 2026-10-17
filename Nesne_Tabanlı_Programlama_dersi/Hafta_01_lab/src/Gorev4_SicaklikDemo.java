
public class Gorev4_SicaklikDemo {
    public static void main(String[] args){
        // Deney A
        System.out.println("\n-------[DENEY A]-------");
        Sicaklik_2 s1 = new Sicaklik_2();
        s1.celsius = 25.0;
        Sicaklik_2 s3 = s1;
        s3.isit(10.0);
        s1.yaz("s1");
        s3.yaz("s3");
        System.out.println(s1 == s3);
        //Deney B
        System.out.println("\n-------[DENEY B]-------");
        Sicaklik_2 s2 = new Sicaklik_2();
        s2.celsius = s1.celsius;
        s2.isit(10);
        s1.yaz("s1");
        s2.yaz("s2");
        // deney C
        System.out.println("\n-------[DENEY C]-------");
        sifirla(s1);
        s1.yaz("s1");
        s3.yaz("s3");
        s2.yaz("s2");
        
    }
    static void sifirla(Sicaklik_2 s) {
    s.celsius = 0.0;
    }
}
