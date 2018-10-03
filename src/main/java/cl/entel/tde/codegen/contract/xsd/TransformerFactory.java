package cl.entel.tde.codegen.contract.xsd;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;

public class TransformerFactory {

    private static Transformer transformer = null;

    public static synchronized Transformer getInsetance() throws TransformerConfigurationException {
        if (transformer == null){
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        }
        return transformer;
    }

}
