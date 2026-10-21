public class gorev3_ZamanDemo {
    public static void main(String Args[]){
        Zaman adana = new Zaman(23, 59, 30);
        Zaman andırın = new Zaman(25, 70, 99);
        System.out.println("--- Sinir denetimi (25:70:99) ---");
        System.out.println("ew Zaman(25, 70, 99) ->" + andırın);
        System.out.println("--- Zincirleme ilerletme ---");
        System.out.println("baslangic              :" + adana);
        adana.ilerlet(45);
        adana.ilerlet(15);
        System.out.println("5+15 sn sonra (gun asimi):" + adana);


    }
}
