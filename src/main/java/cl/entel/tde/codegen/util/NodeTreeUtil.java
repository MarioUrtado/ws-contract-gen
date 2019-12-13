package cl.entel.tde.codegen.util;

import cl.entel.tde.codegen.xsd.cardinality.CDMToXSD;
import cl.entel.tde.codegen.xsd.cardinality.Cardinality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NodeTreeUtil {

    @Autowired
    private CDMToXSD cdmToXSD;

    public void marshall (NodeTree tree, RowDTO rowDto){
        String[] depthPath = rowDto.getEntityPath().split("\\.");
        NodeTree node = tree;

        //This Row is Relative to Root Element Body.
        if (!rowDto.getEntityPath().toUpperCase().equals("BODY") && !rowDto.getEntityPath().equals("") ){
            for (int idx = 0; idx < depthPath.length; idx++){
                //Si la entidad no existe en el Arbol de Nodos
                if (node.get(depthPath[idx]) == null){
                    NodeTree newChild = new NodeTree(depthPath[idx],
                            "complexType",
                            new Cardinality(0,1),
                            NodeTreeType.COMPLEX
                    );
                    node.appendNode(newChild);
                    node = newChild;
                } else {
                    node = node.get(depthPath[idx]);
                }
            }
        }
        //Se asume que si es complexType y termino de recorrer. el ultimo nombre del la cadena de entity corresponde a una entidad.
        if ( rowDto.getType().equals("complexType")   ){
            node.setCardinality(cdmToXSD.parse(rowDto.getCardinality()));
        } else {
            if( !rowDto.getAttribute().equals("") ){
                node.appendNode(new NodeTree(rowDto.getAttribute(),
                        rowDto.getType(),
                        cdmToXSD.parse(rowDto.getCardinality()),
                        NodeTreeType.SIMPLE
                ));
            }

        }
    }
}