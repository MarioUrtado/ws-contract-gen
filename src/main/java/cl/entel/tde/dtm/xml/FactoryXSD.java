package cl.entel.tde.dtm.xml;

import cl.entel.tde.dtm.util.NodeTree;
import cl.entel.tde.dtm.util.NodeTreeToXML;
import cl.entel.tde.dtm.util.NodeTreeType;
import cl.entel.tde.dtm.util.Service;
import cl.entel.tde.dtm.ws.xsd.cardinality.Cardinality;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Map;

public class FactoryXSD {

    public static Document getInstance(Map<String, NodeTree> nodes){
        Document doc = null;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Root Element: XS:SCHEMA
            doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(Prefix.XSD_NAMESPACE + ":schema");
            rootElement.setAttribute("xmlns:" + Prefix.XSD_NAMESPACE, Namespace.XSD_NAMESPACE);
            rootElement.setAttribute("xmlns:" + Prefix.XSD_SELF_NAMESPACE, Namespace.XSD_EBM_NAMESPACE());
            rootElement.setAttribute("xmlns:" + Prefix.XSD_ESO_MESSAGEHEADER, Namespace.XSD_ESO_MESSAGEHEADER);
            rootElement.setAttribute("targetNamespace", Namespace.XSD_EBM_NAMESPACE());
            rootElement.setAttribute("elementFormDefault", "qualified");
            rootElement.setAttribute("version", "1.0");
            doc.appendChild(rootElement);

            Element importXSD = FactoryXSD.createImport(doc, Namespace.XSD_ESO_MESSAGEHEADER, "ESH/MessageHeader_v1_ESO.xsd");

            rootElement.appendChild(importXSD);

            //Get NodeTree Reqeust
            NodeTree tree = nodes.get("REQ");

            // Element Service Request With Type
            String servicerRequestElement = Service.getName() + "_REQ";
            Element servREQ = FactoryXSD.createElementWithType(doc, servicerRequestElement, Prefix.XSD_SELF_NAMESPACE, (servicerRequestElement + "_Type"), new Cardinality(1,1));

            //Complex Type Service Request
            Element complexType = FactoryXSD.createComplexType(doc, servicerRequestElement+"_Type");
            //Create Reference to Requesht Header
            Element headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "RequestHeader", new Cardinality(1,1));
            //Insert Reference to RequestHeader in Service Request Definition
            ((Element) complexType.getFirstChild()).appendChild(headerReq);
            //instanciar transformador
            NodeTreeToXML xml = new NodeTreeToXML();
            //Transform NodeTree (Tree) to XSD into complexType.sequence of Service Request
            xml.transform(doc, (Element) complexType.getFirstChild(), tree);

            //Append Element Service Request and ComplexType Service Request to Schema
            rootElement.appendChild(servREQ);
            rootElement.appendChild(complexType);

            //Get NodeTree Response
            tree = nodes.get("RSP");
            // Element Service Request With Type
            String servicerResponseElement = Service.getName() + "_RSP";
            Element servRSP = FactoryXSD.createElementWithType(doc, servicerResponseElement, Prefix.XSD_SELF_NAMESPACE, (servicerResponseElement + "_Type"), new Cardinality(1,1));
            complexType = FactoryXSD.createComplexType(doc, servicerResponseElement+"_Type");
            headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "ResponseHeader", new Cardinality(1,1));
            ((Element) complexType.getFirstChild()).appendChild(headerReq);
            //instanciar transformador
            xml = new NodeTreeToXML();
            xml.transform(doc, (Element) complexType.getFirstChild(), tree);
            rootElement.appendChild(servRSP);
            rootElement.appendChild(complexType);

            //Get NodeTree Response
            // Element Service Request With Type
            String servicerFResponseElement = Service.getName() + "_FRSP";
            Element servRFSP = FactoryXSD.createElementWithType(doc, servicerFResponseElement, Prefix.XSD_SELF_NAMESPACE, (servicerFResponseElement + "_Type"), new Cardinality(1,1));
            complexType = FactoryXSD.createComplexType(doc, servicerFResponseElement+"_Type");
            headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "ResponseHeader", new Cardinality(1,1));
            ((Element) complexType.getFirstChild()).appendChild(headerReq);
            xml = new NodeTreeToXML();
            xml.transform(doc, (Element) complexType.getFirstChild(), nodes.get("FRSP"));
            rootElement.appendChild(servRFSP);
            rootElement.appendChild(complexType);


        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return doc;
    }

    public static Element createField(Document document, String prefix, String name, String type, Cardinality cardinality){
        Element element;
        if (prefix == null) {
            element = document.createElement("element");
        } else {
            element= document.createElement( prefix + ":element");
        }
        element.setAttribute("name",name);
        element.setAttribute("type", Prefix.XSD_NAMESPACE + ":" + type);

        //Only set MinOccurs if value is different to 1, because 1 is the Default Value for minOccurs
        if (cardinality.getMinOccurs() != 1){
            element.setAttribute("minOccurs", String.valueOf(cardinality.getMinOccurs()));
        }

        //Only set MinOccurs if value is different to 1, because 1 is the Default Value for maxOccurs
        // value -1 is the unbounded value
        if (cardinality.getMaxOccurs() == -1){
            element.setAttribute("maxOccurs", "unbounded");
        } else if (cardinality.getMaxOccurs() != 1){
            element.setAttribute("maxOccurs", String.valueOf(cardinality.getMaxOccurs()));
        }
        return element;
    }

    public static Element createEntityRef(Document doc, String prefix, String name, Cardinality cardinality){
        Element element = doc.createElement(Prefix.XSD_NAMESPACE+ ":element");
        element.setAttribute("ref", prefix + ":" + name);
        //Only set MinOccurs if value is different to 1, because 1 is the Default Value for minOcrrus
        if (cardinality.getMinOccurs() != 1){
            element.setAttribute("minOccurs", String.valueOf(cardinality.getMinOccurs()));
        }

        //Only set MinOccurs if value is different to 1, because 1 is the Default Value for minOcrrus
        // value -1 is the unbounded value
        if (cardinality.getMaxOccurs() == -1){
            element.setAttribute("maxOccurs", "unbounded");
        } else if (cardinality.getMaxOccurs() != 1){
            element.setAttribute("maxOccurs", String.valueOf(cardinality.getMaxOccurs()));
        }
        return element;
    }

    public static Element createEntity(Document doc, String name, Cardinality cardinality){
        Element element = doc.createElement(Prefix.XSD_NAMESPACE + ":element");
        element.setAttribute("name", name);
        Element complexType = doc.createElement(Prefix.XSD_NAMESPACE + ":complexType");
        Element sequence = doc.createElement(Prefix.XSD_NAMESPACE + ":sequence");

        complexType.appendChild(sequence);
        element.appendChild(complexType);

        //Only set MinOccurs if value is different to 1, because 1 is the Default Value for minOcrrus
        if (cardinality.getMinOccurs() != 1){
            element.setAttribute("minOccurs", String.valueOf(cardinality.getMinOccurs()));
        }

        //Only set MinOccurs if value is different to 1, because 1 is the Default Value for minOcrrus
        // value -1 is the unbounded value
        if (cardinality.getMaxOccurs() == -1){
            element.setAttribute("maxOccurs", "unbounded");
        } else if (cardinality.getMaxOccurs() != 1){
            element.setAttribute("maxOccurs", String.valueOf(cardinality.getMaxOccurs()));
        }
        return element;
    }

    public static Element createImport(Document doc, String namespace, String path){
        Element element = doc.createElement(Prefix.XSD_NAMESPACE + ":import");
        element.setAttribute("schemaLocation", path);
        element.setAttribute("namespace", namespace);
        return element;
    }

    public static Element getSequence(Element entity){
        NodeList nodeList = entity.getElementsByTagName(Prefix.XSD_NAMESPACE + ":complexType");
        Node node = nodeList.item(0);
        Element complex = (Element) node;
        nodeList = complex.getElementsByTagName(Prefix.XSD_NAMESPACE + ":sequence");
        node = nodeList.item(0);
        return (Element) node;

    }

    public static Element createElementWithType(Document doc, String name, String prefixType, String type, Cardinality cardinality){
        Element element = doc.createElement(Prefix.XSD_NAMESPACE + ":element");
        element.setAttribute("name", name);
        //Only set MinOccurs if value is different to 1, because 1 is the Default Value for minOcrrus
        if (cardinality.getMinOccurs() != 1){
            element.setAttribute("minOccurs", String.valueOf(cardinality.getMinOccurs()));
        }
        //Only set MinOccurs if value is different to 1, because 1 is the Default Value for minOcrrus
        // value -1 is the unbounded value
        if (cardinality.getMaxOccurs() == -1){
            element.setAttribute("maxOccurs", "unbounded");
        } else if (cardinality.getMaxOccurs() != 1){
            element.setAttribute("maxOccurs", String.valueOf(cardinality.getMaxOccurs()));
        }
        element.setAttribute("type",  prefixType+ ":" + type);
        return element;
    }

    public static Element createComplexType(Document doc, String name){
        Element complexType = doc.createElement(Prefix.XSD_NAMESPACE + ":complexType");
        complexType.setAttribute("name", name);
        complexType.appendChild(doc.createElement(Prefix.XSD_NAMESPACE +":sequence"));
        return complexType;
    }


}