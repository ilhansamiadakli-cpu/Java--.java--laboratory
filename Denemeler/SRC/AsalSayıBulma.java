public class AsalSayıBulma {
    public static void main(String[] args){
        System.out.println("Bir sayı giriniz: ");
        int sayi = new java.util.Scanner(System.in).nextInt();
        boolean isAsal = sayi >= 2;
        for (int i = 2; isAsal && i <= sayi / i; i++) {
            if (sayi % i == 0) {
                isAsal = false;
            }
        }
        System.out.print(sayi + " sayısı asallık durumu: ");
        if(isAsal){
            System.out.println("asaldır");

        }
        else{
            System.out.println("değildir");
        }
    }
}
