package cl.entel.tde.codegen.xsd.cardinality;

import org.springframework.stereotype.Component;

@Component
public class CDMToXSD {

    public Cardinality parse (String cdmCardinalityValue){
        if (cdmCardinalityValue.equals("0..1")){
            return new Cardinality(0, 1);
        } else if (cdmCardinalityValue.equals("1..1")){
            return new Cardinality(1, 1);
        } else if (cdmCardinalityValue.equals("0..N")){
            return new Cardinality(0, -1);
        } else if (cdmCardinalityValue.equals("1..N")){
            return new Cardinality(1, -1);
        };
        return new Cardinality(0, 1);
    }

}
