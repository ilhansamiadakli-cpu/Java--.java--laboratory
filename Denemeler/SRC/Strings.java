public class Strings {
    public static void main(String[] args) {
        String mesaj ="mer haba laar";
        System.out.println("Eleman sayısı: " + mesaj.length());
        System.out.println("İlk eleman: " + mesaj.charAt(0));
        System.out.println("Son eleman: " + mesaj.charAt(mesaj.length()-1));
        System.out.println("İlk 5 eleman: " + mesaj.substring(0,5));
        System.out.println("Son 5 eleman: " + mesaj.substring(mesaj.length()-5));
        System.out.println("Büyük harf: " + mesaj.toUpperCase());
        System.out.println("Küçük harf: " + mesaj.toLowerCase());
        System.out.println("İlk harf büyük: " + mesaj.substring(0,1).toUpperCase() + mesaj.substring(1));
        System.out.println(mesaj.startsWith("m"));
        System.out.println(mesaj.endsWith("r"));

        char[] karakterler = new char[5];
        mesaj.getChars(0,5,karakterler,0);
        System.out.println(karakterler);
        System.out.println(mesaj.indexOf('a'));// string içinde kaç tane a var ve ilk a nın indexi kaçtır
        System.out.println(mesaj.lastIndexOf('a'));// string içinde kaç tane a var ve son a nın indexi kaçtır
        System.out.println(mesaj.replace('a','e'));// string içindeki a harflerini e harfi ile değiştirir
        
        String mesaj2 = "merhaba";
        System.out.println(mesaj.equals(mesaj2));// stringler eşit mi
        System.out.println(mesaj.equalsIgnoreCase(mesaj2));// stringler eşit mi (büyük küçük harf duyarsız)
        System.out.println(mesaj.compareTo(mesaj2));// stringler eşit mi (büyük küçük harf duyarlı)

        String mesaj3 = mesaj.replace(' ', '-');
        System.out.println(mesaj3);
}

}
