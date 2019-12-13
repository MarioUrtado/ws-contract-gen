package cl.entel.tde.codegen.contract.xsd;

import cl.entel.tde.codegen.context.Context;
import cl.entel.tde.codegen.mustache.MustacheExecutor;
import cl.entel.tde.codegen.util.NodeTree;
import cl.entel.tde.codegen.util.NodeTreeType;
import cl.entel.tde.codegen.xsd.cardinality.Cardinality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Map;

@Component
public class FactoryNXSD extends FactoryXSD {

    @Autowired
    private MustacheExecutor mustacheExecutor;

    public FactoryNXSD() {
    }

    public Document getInstance(Map<String, NodeTree> nodes, Context context){
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
            rootElement.setAttribute("xmlns:" + Prefix.XSD_SELF_NAMESPACE, mustacheExecutor.build(context.getConfiguration().getNamespaces().get("service-namespace-ebm").getValue(), context));
            rootElement.setAttribute("xmlns:" + Prefix.XSD_ESO_MESSAGEHEADER, Namespace.XSD_ESO_MESSAGEHEADER_JSON);
            rootElement.setAttribute("targetNamespace", mustacheExecutor.build(context.getConfiguration().getNamespaces().get("service-namespace-ebm").getValue(), context));
            rootElement.setAttribute("elementFormDefault", "qualified");
            rootElement.setAttribute("version", "1.0");
            doc.appendChild(rootElement);

            Element importXSD = createImport(doc, Namespace.XSD_ESO_MESSAGEHEADER_JSON, "ESH/MessageHeader_v1_ESOJ.xsd");
            rootElement.appendChild(importXSD);
            NodeTree tree = nodes.get("REQ");
            String servicerRequestElement = context.getService().getName() + "_REQ";
            Element servREQ = createElementWithType(doc, servicerRequestElement, Prefix.XSD_SELF_NAMESPACE, (servicerRequestElement + "_Type"), new Cardinality(1,1));

            Element complexType = createComplexType(doc, servicerRequestElement+"_Type");
            Element headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "RequestHeader", new Cardinality(1,1));
            ((Element) complexType.getFirstChild()).appendChild(headerReq);

            this.transform(doc, (Element) complexType.getFirstChild(), tree);

            rootElement.appendChild(servREQ);
            rootElement.appendChild(complexType);

            //Get NodeTree Response
            tree = nodes.get("RSP");
            String servicerResponseElement = context.getService().getName() + "_RSP";
            Element servRSP = createElementWithType(doc, servicerResponseElement, Prefix.XSD_SELF_NAMESPACE, (servicerResponseElement + "_Type"), new Cardinality(1,1));
            complexType = createComplexType(doc, servicerResponseElement+"_Type");
            headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "ResponseHeader", new Cardinality(1,1));
            ((Element) complexType.getFirstChild()).appendChild(headerReq);
            this.transform(doc, (Element) complexType.getFirstChild(), tree);
            rootElement.appendChild(servRSP);
            rootElement.appendChild(complexType);

            String servicerFResponseElement = context.getService().getName() + "_FRSP";
            Element servRFSP = createElementWithType(doc, servicerFResponseElement, Prefix.XSD_SELF_NAMESPACE, (servicerFResponseElement + "_Type"), new Cardinality(1,1));
            complexType = createComplexType(doc, servicerFResponseElement+"_Type");
            headerReq = createEntityRef(doc, Prefix.XSD_ESO_MESSAGEHEADER, "ResponseHeader", new Cardinality(1,1));
            ((Element) complexType.getFirstChild()).appendChild(headerReq);

            this.transform(doc, (Element) complexType.getFirstChild(), nodes.get("FRSP"));
            rootElement.appendChild(servRFSP);
            rootElement.appendChild(complexType);
            rootElement.appendChild(createDefaultFault(doc,servicerFResponseElement ));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public Element createDefaultFault(Document doc, String refNameDetail){
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


    //-------------------
    public Element transform(Document doc, Element rootElement, NodeTree tree){
        try {
            Element complex = this.createEntity(doc,tree.getName(),tree.getCardinality());
            rootElement.appendChild(complex);
            this.recursiveBody(doc, this.getSequence(complex), tree);
            //recursiveBody(doc, rootElement, tree );
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