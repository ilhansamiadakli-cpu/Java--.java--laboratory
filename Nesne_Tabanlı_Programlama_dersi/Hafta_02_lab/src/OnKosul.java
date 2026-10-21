   public class OnKosul {
        private String ad;
     
        public OnKosul(String ad) {
            this.ad = ad;
        }
     
        @Override
        public String toString() {
           return "OnKosul: " + ad;
       }
    
       public static void main(String[] args) {
          System.out.println(new OnKosul("hazir"));
       }
   }