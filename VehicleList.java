
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class VehicleList {

    private String sipp=null;
    private String name=null;
    private Double price=null;
    private String supplier=null;
    private Double rating=null;

    private SIPP sippDetailed =null;
    private int vehicleScore;

    public VehicleList(String sipp, String name, Double price, String supplier, Double rating){

        this.sipp = sipp;
        this.name = name;
        this.price = price;
        this.supplier = supplier;
        this.rating = rating;

        if(sipp!=null){
            char[] chars = sipp.toCharArray();
            if(chars.length==4){
                sippDetailed = new SIPP(chars[0], chars[1], chars[2], chars[3]);
                vehicleScore = generateVehicleScore();
            }
        }
    }

    private int generateVehicleScore(){
        int score = 0;
        if(getSippDetailed().getTransmission().equals("Manual")){
            score+=1;
        }
        if(getSippDetailed().getTransmission().equals("Automatic")){
            score+=5;
        }
        if(getSippDetailed().getFuel().equals("Petrol, AC")){
            score+=2;
        }
        return score;
    }

    public int getVehicleScore(){
        return vehicleScore;
    }

    public Double getSumOfScores(){
        return vehicleScore+rating;
    }

    public SIPP getSippDetailed(){
        return sippDetailed;
    }

    public String getSipp() {
        return sipp;
    }

    public void setSipp(String sipp) {
        this.sipp = sipp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
