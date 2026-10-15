public class Main {
    public static String checkUserRegistration(String username, int age, boolean hasPremium){
        if(username == null || username.isEmpty()){
            return "Geçersiz kullanıcı adı";
        }
        if(age < 18 && !hasPremium){
            return "kullanıcı 18 yaşından küçük";
        }
            return "kayıt başarılı";
        
    }
    public static void main(String[] args){
        String isim = "ahmet";
        int yaş = 17;
        boolean premium = true;
        double bakiye = 808.9;// tl
        System.out.println(checkUserRegistration(isim,yaş,premium));


    }
}
class BankAccount{
    private  double balance;
    private  String accountHolder;

    BankAccount(String isim, double bakiye){
        if(!(isim == null)){
        this.accountHolder = isim;
        }
        if(bakiye < 0){
            System.out.println("Hata: bakiye 0 dan küçük olamaz, hesap otomatik 0 ile açılmıştır");
            bakiye = 0;
        }
        this.balance = bakiye;
    }
    public  void deposit(double amount){
        if(amount <= 0){ 
            System.out.println("Hata: lütfen geçerli bir para birimi giriniz");
            return ;
        }
        
            balance = balance + amount; // balance += amount da olur 
    }
    public  void withdraw(double amount){
        if(amount <= 0){
            System.out.println("Hata: lütfen geçerli bir para birimi giriniz ");
            return ;
        }
        if(balance <= 0){
            System.out.println("Hata: yetersiz bakiye ");
            return ;
        }
        if(amount > balance){
            System.out.println("Hata: bakiyeniz yetersiz");
            return ;
        }

            balance = balance - amount;
        
    }
}
