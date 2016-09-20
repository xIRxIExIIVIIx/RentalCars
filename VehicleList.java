
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class VehicleList {

    private String sipp=null;
    private String name;
    private Double price;
    private String supplier;
    private Double rating;

    private SIPP detailedSipp=null;

    public void makeSIPPDetails()throws Exception{
        if(sipp!=null){
            char[] chars = sipp.toCharArray();
            detailedSipp = new SIPP(chars[0], chars[1], chars[2], chars[3]);
        }else{
            throw new Exception("attempted to gather SIPP details without setting SIPP");
        }
       
    }

    public SIPP getSIPPDetailed(){
        if(detailedSipp!=null){
            return detailedSipp;
        }else return null;
    }

    /**
     * 
     * @return
     *     The sipp
     */
    public String getSipp() {
        return sipp;
    }

    /**
     * 
     * @param sipp
     *     The sipp
     */
    public void setSipp(String sipp) {
        this.sipp = sipp;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The supplier
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * 
     * @param supplier
     *     The supplier
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * 
     * @return
     *     The rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * 
     * @param rating
     *     The rating
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

}
