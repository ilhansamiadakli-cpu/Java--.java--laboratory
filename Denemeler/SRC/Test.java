class Hayvan { 
    public void sesCikar() { System.out.println("Ses"); } 
}

class Kedi extends Hayvan { 
    @Override
    public void sesCikar() { System.out.println("Miyav"); } 
    public void tirmala() { System.out.println("Tırmaladı!"); } 
}

class Kopek extends Hayvan { 
    @Override
    public void sesCikar() { System.out.println("Hav"); } 
}

public class Test {
    public static void main(String[] args) {
        Hayvan h1 = new Kedi();
        Hayvan h2 = new Kopek();
        
        h1.sesCikar();
        
        Kedi k = (Kedi) h1;
        k.tirmala();
        
        Kedi k2 = (Kedi) h2;
        k2.tirmala();
    }
}