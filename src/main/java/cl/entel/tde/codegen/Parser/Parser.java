package cl.entel.tde.codegen.Parser;

import cl.entel.tde.codegen.exception.MalFormedFilePathException;
import cl.entel.tde.codegen.util.NodeTree;
import cl.entel.tde.codegen.util.NodeTreeType;
import cl.entel.tde.codegen.util.NodeTreeUtil;
import cl.entel.tde.codegen.util.RowDTO;
import cl.entel.tde.codegen.xsd.cardinality.Cardinality;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Parser {


    @Value("${dtm.request.delimiter.begin}")
    private String delimiterBeginRequest;

    @Value("${dtm.request.delimiter.end}")
    private String delimiterEndRequest;

    @Value("${dtm.response.delimiter.begin}")
    private String delimiterBeginResponse;

    @Value("${dtm.response.delimiter.end}")
    private String delimiterEndResponse;

    @Autowired
    private Reader reader;

    @Autowired
    private NodeTreeUtil nodeTreeUtil;

    public Parser(){}

    public NodeTree parse(String filepath) throws Exception{
        Workbook book = reader.read(filepath);
        Sheet sheet = book.getSheetAt(1);

        int  rowNum = 1;
        boolean breakFor = true;

        while(breakFor){
            Row row = sheet.getRow(rowNum);
            Cell firstCell = row.getCell(0);
            if (firstCell.getStringCellValue().equals(delimiterBeginRequest)){
                breakFor = false;
            }
            rowNum++;
        }

        NodeTree root = new NodeTree("Body", "complexType", new Cardinality(1,1), NodeTreeType.COMPLEX);
        for (int i = rowNum; i < sheet.getLastRowNum() ; i++) {
            RowDTO rowDto = new RowDTO(sheet.getRow(i));

            if (rowDto.getEntityPath().equals(delimiterEndRequest)) {
                break;
            }
            if (rowDto.getEntityPath().equals("")) {
                if (rowDto.getType().equals("complexType") && !rowDto.getAttribute().equals("")) {
                    nodeTreeUtil.marshall(root, rowDto);
                }
            } else {
                nodeTreeUtil.marshall(root, rowDto);
            }
        }
        return root;
    }

    public NodeTree parseForResponse(String filepath) throws Exception{

        Reader reader = new Reader();
        Workbook book = reader.read(filepath);
        Sheet sheet = book.getSheetAt(1);
        int  rowNum = 1;

        for (int i = 1; i < sheet.getLastRowNum() ; i++){
            Row rw = sheet.getRow(i);
            RowDTO row = new RowDTO(rw);
            if (row.getEntityPath().equals(delimiterBeginResponse)){
                rowNum = i + 1;
                break;
            }
        }

        NodeTree root = new NodeTree("Body", "complexType", new Cardinality(1,1), NodeTreeType.COMPLEX);
        for (int i = rowNum; i < sheet.getLastRowNum() ; i++) {
            RowDTO rowDto = new RowDTO(sheet.getRow(i));
            if (rowDto.getEntityPath().equals(delimiterEndResponse)){
                break;
            } else if (!rowDto.getEntityPath().equals("")){
                nodeTreeUtil.marshall(root, rowDto);
            }
        }
        return root;
    }

    public String getServiceName(String filepath) throws MalFormedFilePathException{
        try{
            File f = new File(filepath);
            return f.getName().split("-CDM")[0].split("DTM-")[1];
        } catch (Exception e){
            throw new MalFormedFilePathException(filepath, (new File(filepath)).getName());
        }

    }

    public String getServiceVersion(String filepath) throws MalFormedFilePathException{
        String version;
        try{
            File f = new File(filepath);
            version = f.getName().split("_v")[1].split("\\.")[0];
        } catch (Exception e){
            throw new MalFormedFilePathException(filepath, (new File(filepath)).getName());
        }
        return version;
    }
}