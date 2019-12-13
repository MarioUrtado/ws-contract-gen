package cl.entel.tde.codegen.contract.xsd;

import cl.entel.tde.codegen.context.Context;
import cl.entel.tde.codegen.mustache.MustacheExecutor;
import cl.entel.tde.codegen.util.NodeTree;
import cl.entel.tde.codegen.util.NodeTreeType;
import cl.entel.tde.codegen.xsd.cardinality.Cardinality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Map;

@Component
public class FactoryXSD {

    @Autowired
    private MustacheExecutor mustacheExecutor;

    public FactoryXSD() {
    }

    public Document getInstance(Map<String, NodeTree> nodes, Context context){
        Document doc = null;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Root Element: XS:SCHEMA
            doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(Prefix.XSD_NAMESPACE + ":schema");
            rootElement.setAttribute("xmlns:" + Prefix.XSD_NAMESPACE, Namespace.XSD_NAMESPACE);
            rootElement.setAttribute("xmlns:" + Prefix.XSD_SELF_NAMESPACE, mustacheExecutor.build(context.getConfiguration().getNamespaces().get("service-namespace-ebm").getValue(), context));
            rootElement.setAttribute("xmlns:" + Prefix.XSD_ESO_MESSAGEHEADER, Namespace.XSD_ESO_MESSAGEHEADER);
            rootElement.setAttribute("targetNamespace",  mustacheExecutor.build(context.getConfiguration().getNamespaces().get("service-namespace-ebm").getValue(), context));
            rootElement.setAttribute("elementFormDefault", "qualified");
            rootElement.setAttribute("version", "1.0");
            doc.appendChild(rootElement);

            Element importXSD = createImport(doc, Namespace.XSD_ESO_MESSAGEHEADER, "ESH/MessageHeader_v1_ESO.xsd");

            rootElement.appendChild(importXSD);

            //Get NodeTree Reqeust
            NodeTree tree = nodes.get("REQ");

            String servicerRequestElement = context.getService().getName() + "_REQ";
            Element servREQ = createElementWithType(doc, servicerRequestElement, Prefix.XSD_SELF_NAMESPACE, (servicerRequestElement + "_Type"), new Cardinality(1,1));


            Element complexType = createComplexType(doc, servicerRequestElement+"_Type");
            Element headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "RequestHeader", new Cardinality(1,1));

            ((Element) complexType.getFirstChild()).appendChild(headerReq);

            //NodeTreeToXML xml = new NodeTreeToXML();
            this.transform(doc, (Element) complexType.getFirstChild(), tree);


            rootElement.appendChild(servREQ);
            rootElement.appendChild(complexType);

            tree = nodes.get("RSP");

            String servicerResponseElement = context.getService().getName() + "_RSP";
            Element servRSP = createElementWithType(doc, servicerResponseElement, Prefix.XSD_SELF_NAMESPACE, (servicerResponseElement + "_Type"), new Cardinality(1,1));
            complexType = createComplexType(doc, servicerResponseElement+"_Type");
            headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "ResponseHeader", new Cardinality(1,1));
            ((Element) complexType.getFirstChild()).appendChild(headerReq);
            //instanciar transformador
            //xml = new NodeTreeToXML();
            this.transform(doc, (Element) complexType.getFirstChild(), tree);
            rootElement.appendChild(servRSP);
            rootElement.appendChild(complexType);

            String servicerFResponseElement = context.getService().getName() + "_FRSP";
            Element servRFSP = createElementWithType(doc, servicerFResponseElement, Prefix.XSD_SELF_NAMESPACE, (servicerFResponseElement + "_Type"), new Cardinality(1,1));
            complexType = createComplexType(doc, servicerFResponseElement+"_Type");
            headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "ResponseHeader", new Cardinality(1,1));
            ((Element) complexType.getFirstChild()).appendChild(headerReq);
            //xml = new NodeTreeToXML();
            this.transform(doc, (Element) complexType.getFirstChild(), nodes.get("FRSP"));
            rootElement.appendChild(servRFSP);
            rootElement.appendChild(complexType);


        }catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public Element createField(Document document, String prefix, String name, String type, Cardinality cardinality){
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

    public Element createEntityRef(Document doc, String prefix, String name, Cardinality cardinality){
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

    public Element createEntity(Document doc, String name, Cardinality cardinality){
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

    public Element createImport(Document doc, String namespace, String path){
        Element element = doc.createElement(Prefix.XSD_NAMESPACE + ":import");
        element.setAttribute("schemaLocation", path);
        element.setAttribute("namespace", namespace);
        return element;
    }

    public Element getSequence(Element entity){
        NodeList nodeList = entity.getElementsByTagName(Prefix.XSD_NAMESPACE + ":complexType");
        Node node = nodeList.item(0);
        Element complex = (Element) node;
        nodeList = complex.getElementsByTagName(Prefix.XSD_NAMESPACE + ":sequence");
        node = nodeList.item(0);
        return (Element) node;

    }

    public Element createElementWithType(Document doc, String name, String prefixType, String type, Cardinality cardinality){
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

    public Element createComplexType(Document doc, String name){
        Element complexType = doc.createElement(Prefix.XSD_NAMESPACE + ":complexType");
        complexType.setAttribute("name", name);
        complexType.appendChild(doc.createElement(Prefix.XSD_NAMESPACE +":sequence"));
        return complexType;
    }






    //-----------------
    public Element transform(Document doc, Element rootElement, NodeTree tree){
        try {
            // TODO: IMPORT MessageHeader
            Element complex = this.createEntity(doc,tree.getName(),tree.getCardinality());
            rootElement.appendChild(complex);
            this.recursiveBody(doc, this.getSequence(complex), tree);
            //doc.appendChild(rootElement);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootElement;
    }

    public void recursiveBody(Document doc, Element element, NodeTree tree){
        for (NodeTree node: tree.getChilds()) {
            if (node.getNodeType() == NodeTreeType.SIMPLE){
                Element fieldElement = this.createField(doc, Prefix.XSD_NAMESPACE, node.getName(),node.getType(), node.getCardinality());
                element.appendChild(fieldElement);
            }
        }

        for (NodeTree node: tree.getChilds()) {
            if (node.getNodeType() == NodeTreeType.COMPLEX){
                Element complex = this.createEntity(doc,node.getName(),node.getCardinality());
                element.appendChild(complex);
                this.recursiveBody(doc, this.getSequence(complex), node);
            }
        }
    }

}