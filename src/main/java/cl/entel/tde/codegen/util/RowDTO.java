package cl.entel.tde.codegen.util;

import cl.entel.tde.codegen.exception.InvalidRowSpecificationException;
import cl.entel.tde.codegen.xsd.types.XSDTypes;
import org.apache.poi.ss.usermodel.Row;
import java.util.HashMap;
import java.util.Map;

public class RowDTO {

    private Row row;

    public RowDTO(Row row){
        super();
        this.row = row;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public String getEntityPath(){
        String entity = "";
        try{
            entity =  this.row.getCell(0).getStringCellValue();
        } catch (Exception e){
            e.printStackTrace();
        }
        return entity;
    }

    public String getAttribute(){
        String attribute = "";
        try{
            attribute = this.row.getCell(2).getStringCellValue();
        } catch (Exception e){
            e.printStackTrace();
        }
        return attribute;
    }

    public String getCardinality(){
        String cardinality= "0..1";
        try{
            cardinality = this.row.getCell(3).getStringCellValue();
        } catch (Exception e){
        }
        return cardinality;
    }

    public String getType(){
        String type= "";
        try{
            type = this.row.getCell(5).getStringCellValue();
        } catch (Exception e){
        }
        return type;
    }

    public void validate() throws InvalidRowSpecificationException {
        Map<String, String> checks = new HashMap<String, String>();
        if (XSDTypes.getNormalizeValue(getType()).equals("")){
            checks.put("Type", getType());
        }
        if (getType().toUpperCase().equals("COMPLEXTYPE") && getAttribute().equals("")){
            checks.put("Attribute",String.valueOf(this.row.getRowNum()));
        }
    }
}
