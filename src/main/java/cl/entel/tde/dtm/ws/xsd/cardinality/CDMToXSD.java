package cl.entel.tde.dtm.ws.xsd.cardinality;

public class CDMToXSD {

    public static Cardinality parse (String cdmCardinalityValue){
        if (cdmCardinalityValue.equals("0..1")){
            return new Cardinality(0, 1);
        } else if (cdmCardinalityValue.equals("1..1")){
            return new Cardinality(1, 1);
        } else if (cdmCardinalityValue.equals("0..N")){
            return new Cardinality(0, -1);
        } else if (cdmCardinalityValue.equals("1..N")){
            return new Cardinality(1, -1);
        };
        return new Cardinality(1, 1);
    }

}
