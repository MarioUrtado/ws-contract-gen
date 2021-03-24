package cl.entel.tde.dtm.ws.xsd.cardinality;

import javax.smartcardio.Card;

public class Cardinality {

    private int minOccurs;

    private int maxOccurs;

    public Cardinality(){
        this.minOccurs = 1;
        this.maxOccurs = 1;
    }
    public Cardinality(int minOccurs, int maxOccurs){
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
    }

    public int getMinOccurs() {
        return minOccurs;
    }

    public void setMinOccurs(int minOccurs) {
        this.minOccurs = minOccurs;
    }

    public int getMaxOccurs() {
        return maxOccurs;
    }

    public void setMaxOccurs(int maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

   /* public String getMinOccurs(){
        if (this.minOccurs < 0 ) {
            return "unbounded";
        }
        return new String.valueOf(this.minOccurs);
    }

    public String getMaxOccurs(){
        if (this.maxOccurs < 0 ) {
            return "unbounded";
        }
        return new String.valueOf(this.maxOccurs);
    }*/
}