package cl.entel.tde.dtm.util;

import cl.entel.tde.codegen.contract.xsd.FactoryXSD;
import cl.entel.tde.codegen.contract.xsd.Prefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Component
public class NodeTreeToXML {

    @Autowired
    private FactoryXSD factoryXSD;

    public NodeTreeToXML(){
        super();
    }

    public Element transform(Document doc, Element rootElement, NodeTree tree){
        try {
            // TODO: IMPORT MessageHeader
            Element complex = factoryXSD.createEntity(doc,tree.getName(),tree.getCardinality());
            rootElement.appendChild(complex);
            this.recursiveBody(doc, factoryXSD.getSequence(complex), tree);
            //recursiveBody(doc, rootElement, tree );
            doc.appendChild(rootElement);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return rootElement;
    }

    public void recursiveBody(Document doc, Element element, NodeTree tree){
        for (NodeTree node: tree.getChilds()) {
            if (node.getNodeType() == NodeTreeType.SIMPLE){
                Element fieldElement = factoryXSD.createField(doc, Prefix.XSD_NAMESPACE, node.getName(),node.getType(), node.getCardinality());
                element.appendChild(fieldElement);
            }
        }

        for (NodeTree node: tree.getChilds()) {
             if (node.getNodeType() == NodeTreeType.COMPLEX){
                Element complex = factoryXSD.createEntity(doc,node.getName(),node.getCardinality());
                element.appendChild(complex);
                this.recursiveBody(doc, factoryXSD.getSequence(complex), node);
            }
        }
    }
}