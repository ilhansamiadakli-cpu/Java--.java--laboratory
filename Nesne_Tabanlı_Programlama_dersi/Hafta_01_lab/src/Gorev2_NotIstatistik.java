
public class Gorev2_NotIstatistik {
    public static void main(String[] args){
        int notlar[] = {68, 74, 91, 55, 80, 43, 97, 62};
        notlariYazdir(notlar);
        System.out.printf("Ortalama    : %.2f%n", ortalama(notlar));
        System.out.printf("En yuksek   : %d%n", enBuyuk(notlar));
        System.out.printf("En dusuk    : %d%n", enKucuk(notlar));
        System.out.printf("Gecen sayisi: %d%n", gecenSayisi(notlar, 50));
    }
    public static double ortalama(int dizi[]){
        double toplam = 0;
        for(int i=0; i < dizi.length;i++){
            toplam += dizi[i];
        }
        return (double) toplam/dizi.length;
    }
    public static int enBuyuk(int dizi[]){
        int enbuyuk = dizi[0];
        for( int sayi : dizi){
            if(sayi > enbuyuk){
                enbuyuk = sayi;
            }
        }

        return enbuyuk;
    }
    public static int gecenSayisi(int dizi[], int gecme){
        int sayac = 0;
        for(int sayi : dizi){
            if(sayi >= gecme){
                sayac++;
            }
        }
        return sayac;
    }
    public static int enKucuk(int dizi[]){
        int enKucuk = dizi[0];
        for(int sayilar : dizi){
            if(sayilar < enKucuk){
                enKucuk = sayilar;
            }
        }
        return enKucuk;
    }
    public static  void notlariYazdir(int dizi[]){
        System.out.print("[");
        for(int i = 0; i < dizi.length ; i++){
            System.out.print(dizi[i]);
            if(i < dizi.length - 1){
                System.out.print(", ");
            }   
        }
        System.out.println("]");
    }
}
