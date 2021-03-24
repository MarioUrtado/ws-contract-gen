package cl.entel.tde.dtm.util;

import cl.entel.tde.dtm.ws.xsd.cardinality.Cardinality;
import cl.entel.tde.dtm.xml.FactoryXSD;
import cl.entel.tde.dtm.xml.Namespace;
import cl.entel.tde.dtm.xml.Prefix;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class NodeTreeToXML {


    public NodeTreeToXML(){
        super();
    }

    public Element transform(Document doc, Element rootElement, NodeTree tree){
        try {
            // TODO: IMPORT MessageHeader

            Element complex = FactoryXSD.createEntity(doc,tree.getName(),tree.getCardinality());
            rootElement.appendChild(complex);
            this.recursiveBody(doc, FactoryXSD.getSequence(complex), tree);


            //recursiveBody(doc, rootElement, tree );
            doc.appendChild(rootElement);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return rootElement;
    }

    /*
    public void recursiveBody(Document doc, Element element, NodeTree tree){

        for (NodeTree node: tree.getChilds()) {
            if (node.getNodeType() == NodeTreeType.SIMPLE){
                Element fieldElement = FactoryXSD.createField(doc, Prefix.XSD_NAMESPACE, node.getName(),node.getType(), node.getCardinality());
                element.appendChild(fieldElement);
            } else if (node.getNodeType() == NodeTreeType.COMPLEX){
                Element complex = FactoryXSD.createEntity(doc,node.getName(),node.getCardinality());
                element.appendChild(complex);
                this.recursiveBody(doc, FactoryXSD.getSequence(complex), node);
            }
        }
    }*/

    public void recursiveBody(Document doc, Element element, NodeTree tree){

        for (NodeTree node: tree.getChilds()) {
            if (node.getNodeType() == NodeTreeType.SIMPLE){
                Element fieldElement = FactoryXSD.createField(doc, Prefix.XSD_NAMESPACE, node.getName(),node.getType(), node.getCardinality());
                element.appendChild(fieldElement);
            }
        }

        for (NodeTree node: tree.getChilds()) {
             if (node.getNodeType() == NodeTreeType.COMPLEX){
                Element complex = FactoryXSD.createEntity(doc,node.getName(),node.getCardinality());
                element.appendChild(complex);
                this.recursiveBody(doc, FactoryXSD.getSequence(complex), node);
            }
        }
    }
}
