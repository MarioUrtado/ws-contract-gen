package cl.entel.tde.dtm.ws.xsd.cardinality;

import javax.smartcardio.Card;

public class Cardinality {

    private int minOccurs;

    private int maxOccurs;

    public Cardinality(){
        this.minOccurs = 0;
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


}