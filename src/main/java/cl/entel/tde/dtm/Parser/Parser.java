package cl.entel.tde.dtm.Parser;

import cl.entel.tde.dtm.microsoft.excel.Reader;
import cl.entel.tde.dtm.util.NodeTree;
import cl.entel.tde.dtm.util.NodeTreeType;
import cl.entel.tde.dtm.util.NodeTreeUtil;
import cl.entel.tde.dtm.util.RowDTO;
import cl.entel.tde.dtm.ws.xsd.cardinality.Cardinality;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

public class Parser {

    public static NodeTree parse(String filepath) throws Exception{

        Reader reader = new Reader();
        Workbook book = reader.read(filepath);
        Sheet sheet = book.getSheetAt(1);

        int  rowNum = 1;
        boolean breakFor = true;

        while(breakFor){
            Row row = sheet.getRow(rowNum);
            Cell firstCell = row.getCell(0);
            if (firstCell.getStringCellValue().equals("REQUEST BODY")){
                breakFor = false;
            }
            rowNum++;
        }

        NodeTree root = new NodeTree("Body", "complexType", new Cardinality(1,1), NodeTreeType.COMPLEX);
        for (int i = rowNum; i < sheet.getLastRowNum() ; i++) {
            RowDTO rowDto = new RowDTO(sheet.getRow(i));

            if (rowDto.getEntityPath().equals("ResponseHeader")) {
                break;
            }
            if (rowDto.getEntityPath().equals("")) {
                if (rowDto.getType().equals("complexType") && !rowDto.getAttribute().equals("")) {
                    NodeTreeUtil.marshall(root, rowDto);
                }
            } else {
                NodeTreeUtil.marshall(root, rowDto);
            }
        }
        return root;
    }

    public static NodeTree parseForResponse(String filepath) throws Exception{

        Reader reader = new Reader();
        Workbook book = reader.read(filepath);
        Sheet sheet = book.getSheetAt(1);
        int  rowNum = 1;

        for (int i = 1; i < sheet.getLastRowNum() ; i++){
            Row rw = sheet.getRow(i);
            RowDTO row = new RowDTO(rw);
            if (row.getEntityPath().equals("RESPONSE BODY")){
                rowNum = i + 1;
                break;
            }
        }

        NodeTree root = new NodeTree("Body", "complexType", new Cardinality(1,1), NodeTreeType.COMPLEX);
        for (int i = rowNum; i < sheet.getLastRowNum() ; i++) {
            RowDTO rowDto = new RowDTO(sheet.getRow(i));
            if (rowDto.getEntityPath().equals("END RESPONSE BODY")){
                break;
            } else if (!rowDto.getEntityPath().equals("")){
                NodeTreeUtil.marshall(root, rowDto);
            }
        }
        return root;
    }

    public static String getServiceName(String filepath){
        return filepath.split("-CDM")[0].split("DTM-")[1];
    }

    public static String getServiceVersion(String filepath){
        String version = filepath.split("_v")[1].split("\\.")[0];
        return version;
    }
}