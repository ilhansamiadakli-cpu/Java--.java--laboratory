public class Sporcu{
    private final String tcKimlik;
    private String ad;
    private int[] macSkorları;

    public Sporcu(String tcKimlik, String ad, int[] macSkorları){
        if(tcKimlik.length() != 11 || tcKimlik.isBlank()){
	        throw new IllegalArgumentException("tckimlik doğru girilmelidir");
        }
        if(ad == null || ad.isBlank()){
	        throw new IllegalArgumentException("ad boş olamaz");
        }
        if(macSkorları == null){
	        throw new IllegalArgumentException("skorlar boş olamaz");
        }               
	    this.tcKimlik = tcKimlik;
	    this.ad = ad;
	    this.macSkorları = macSkorları.clone();
}
public Sporcu(String tcKimlik , String ad){
	this(tcKimlik,ad, new int[0]);
}
public int[] getMacSkorlari(){
	return this.macSkorları;
}
public String getTcKimlik(){
	return this.tcKimlik;
}
public String getAd(){
	return this.ad;
}

}