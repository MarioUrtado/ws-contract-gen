package cl.entel.tde.dtm.xml;

import cl.entel.tde.dtm.util.NodeTree;
import cl.entel.tde.dtm.util.NodeTreeToXML;
import cl.entel.tde.dtm.util.Service;
import cl.entel.tde.dtm.ws.xsd.cardinality.Cardinality;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Map;

public class FactoryNXSD extends FactoryXSD {

    public static Document getInstance(Map<String, NodeTree> nodes){
        Document doc = null;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(Prefix.XSD_NAMESPACE + ":schema");
            rootElement.setAttribute("xmlns:" + Prefix.XSD_NAMESPACE, Namespace.XSD_NAMESPACE);
            rootElement.setAttribute("xmlns:nxsd", "http://xmlns.oracle.com/pcbpel/nxsd");
            rootElement.setAttribute("nxsd:version", "JSON");
            rootElement.setAttribute("nxsd:encoding", "UTF-8");
            rootElement.setAttribute("xmlns:" + Prefix.XSD_SELF_NAMESPACE, Namespace.XSD_EBM_NAMESPACE());
            rootElement.setAttribute("xmlns:" + Prefix.XSD_ESO_MESSAGEHEADER, Namespace.XSD_ESO_MESSAGEHEADER_JSON);
            rootElement.setAttribute("targetNamespace", Namespace.XSD_EBM_NAMESPACE());
            rootElement.setAttribute("elementFormDefault", "qualified");
            rootElement.setAttribute("version", "1.0");
            doc.appendChild(rootElement);

            Element importXSD = FactoryNXSD.createImport(doc, Namespace.XSD_ESO_MESSAGEHEADER_JSON, "ESH/MessageHeader_v1_ESOJ.xsd");
            rootElement.appendChild(importXSD);
            NodeTree tree = nodes.get("REQ");
            String servicerRequestElement = Service.getName() + "_REQ";
            Element servREQ = FactoryNXSD.createElementWithType(doc, servicerRequestElement, Prefix.XSD_SELF_NAMESPACE, (servicerRequestElement + "_Type"), new Cardinality(1,1));

            Element complexType = FactoryNXSD.createComplexType(doc, servicerRequestElement+"_Type");
            Element headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "RequestHeader", new Cardinality(1,1));
            ((Element) complexType.getFirstChild()).appendChild(headerReq);
            NodeTreeToXML xml = new NodeTreeToXML();
            xml.transform(doc, (Element) complexType.getFirstChild(), tree);

            rootElement.appendChild(servREQ);
            rootElement.appendChild(complexType);

            //Get NodeTree Response
            tree = nodes.get("RSP");
            String servicerResponseElement = Service.getName() + "_RSP";
            Element servRSP = FactoryNXSD.createElementWithType(doc, servicerResponseElement, Prefix.XSD_SELF_NAMESPACE, (servicerResponseElement + "_Type"), new Cardinality(1,1));
            complexType = FactoryNXSD.createComplexType(doc, servicerResponseElement+"_Type");
            headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "ResponseHeader", new Cardinality(1,1));
            ((Element) complexType.getFirstChild()).appendChild(headerReq);
            xml = new NodeTreeToXML();
            xml.transform(doc, (Element) complexType.getFirstChild(), tree);
            rootElement.appendChild(servRSP);
            rootElement.appendChild(complexType);

            String servicerFResponseElement = Service.getName() + "_FRSP";
            Element servRFSP = FactoryNXSD.createElementWithType(doc, servicerFResponseElement, Prefix.XSD_SELF_NAMESPACE, (servicerFResponseElement + "_Type"), new Cardinality(1,1));
            complexType = FactoryNXSD.createComplexType(doc, servicerFResponseElement+"_Type");
            headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "ResponseHeader", new Cardinality(1,1));
            ((Element) complexType.getFirstChild()).appendChild(headerReq);
            xml = new NodeTreeToXML();
            xml.transform(doc, (Element) complexType.getFirstChild(), nodes.get("FRSP"));
            rootElement.appendChild(servRFSP);
            rootElement.appendChild(complexType);
            rootElement.appendChild(FactoryNXSD.createDefaultFault(doc,servicerFResponseElement ));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return doc;
    }

    public static Element createDefaultFault(Document doc, String refNameDetail){
        Element element = doc.createElement(Prefix.XSD_NAMESPACE + ":element");
        element.setAttribute("ref", Prefix.XSD_SELF_NAMESPACE + ":" + refNameDetail);
        Element sequence= doc.createElement(Prefix.XSD_NAMESPACE + ":sequence");
        sequence.appendChild(element);
        Element complextType= doc.createElement(Prefix.XSD_NAMESPACE + ":complexType");
        complextType.appendChild(sequence);
        Element detail = doc.createElement(Prefix.XSD_NAMESPACE + ":element");
        detail.setAttribute("name" , "detail");
        detail.appendChild(complextType);
        Element faultstring = doc.createElement(Prefix.XSD_NAMESPACE + ":element");
        faultstring.setAttribute("name", "faultstring");
        faultstring.setAttribute("type", Prefix.XSD_NAMESPACE + ":string");
        Element faultcode = doc.createElement(Prefix.XSD_NAMESPACE + ":element");
        faultcode.setAttribute("name", "faultcode");
        faultcode.setAttribute("type", Prefix.XSD_NAMESPACE+":string");

        Element mainSequence = doc.createElement(Prefix.XSD_NAMESPACE + ":sequence");
        mainSequence.appendChild(faultcode);
        mainSequence.appendChild(faultstring);
        mainSequence.appendChild(detail);

        Element mainComplexType = doc.createElement(Prefix.XSD_NAMESPACE + ":complexType");
        mainComplexType.appendChild(mainSequence);
        Element fault = doc.createElement(Prefix.XSD_NAMESPACE + ":element");
        fault.setAttribute("name", "Fault");
        fault.appendChild(mainComplexType);
        return fault;
    }
}