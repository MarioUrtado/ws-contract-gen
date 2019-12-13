package cl.entel.tde.codegen.contract.xsd;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLWriter {

    public void write(Document document, String targetFilePath, String targetFile) throws TransformerConfigurationException, TransformerException {
        new File(targetFilePath).mkdirs();
        Transformer transformer = TransformerFactory.getInsetance();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(targetFilePath + File.separatorChar + targetFile));
        transformer.transform(source, result);
    }

}
