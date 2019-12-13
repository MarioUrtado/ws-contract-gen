package cl.entel.tde.codegen.xsd.types;

import java.util.HashMap;
import java.util.Map;

public class XSDTypes {

    private static Map<String, String> types = null;

    private static synchronized Map<String, String> getInstance(){
        if(types == null){
            types = new HashMap<String, String>();
            types.put("ANYURI","anyURI");
            types.put("BASE64BINARY","base64Binary");
            types.put("BOOLEAN","boolean");
            types.put("BYTE","byte");
            types.put("DATE","date");
            types.put("DATETIME","dateTime");
            types.put("DECIMAL","decimal");
            types.put("DOUBLE","double");
            types.put("DURATION","duration");
            types.put("FLOAT","float");
            types.put("GDAY","gDay");
            types.put("GMONTH","gMonth");
            types.put("GMONTHDAY","gMonthDay");
            types.put("GYEAR","gYear");
            types.put("GYEARMONTH","gYearMonth");
            types.put("HEXBINARY","hexBinary");
            types.put("ID","ID");
            types.put("IDREF","IDREF");
            types.put("IDREFS","IDREFS");
            types.put("INT","int");
            types.put("INTEGER","integer");
            types.put("LANGUAGE","language");
            types.put("LONG","long");
            types.put("NAME","Name");
            types.put("NCNAME","NCName");
            types.put("NEGATIVEINTEGER","negativeInteger");
            types.put("NMTOKEN","NMTOKEN");
            types.put("NMTOKENS","NMTOKENS");
            types.put("NONNEGATIVEINTEGER","nonNegativeInteger");
            types.put("NONPOSITIVEINTEGER","nonPositiveInteger");
            types.put("NORMALIZEDSTRING","normalizedString");
            types.put("POSITIVEINTEGER","positiveInteger");
            types.put("QNAME","QName");
            types.put("SHORT","short");
            types.put("STRING","string");
            types.put("TIME","time");
            types.put("TOKEN","token");
            types.put("UNSIGNEDBYTE","unsignedByte");
            types.put("UNSIGNEDINT","unsignedInt");
            types.put("UNSIGNEDLONG","unsignedLong");
            types.put("UNSIGNEDSHORT","unsignedShort");
        }
        return types;
    }

    public static String getNormalizeValue(String strType){
        String value = getInstance().get(strType.toUpperCase());
        if (value == null){
            return "";
        }
        return value;
    }

}
