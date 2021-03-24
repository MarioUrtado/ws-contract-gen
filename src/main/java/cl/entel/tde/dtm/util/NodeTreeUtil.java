package cl.entel.tde.dtm.util;

import cl.entel.tde.dtm.ws.xsd.cardinality.CDMToXSD;
import cl.entel.tde.dtm.ws.xsd.cardinality.Cardinality;
import org.apache.poi.ss.usermodel.Row;

public class NodeTreeUtil {

    public static void marshall (NodeTree tree, RowDTO rowDto){
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
            node.setCardinality(CDMToXSD.parse(rowDto.getCardinality()));
            /*node.appendNode( new NodeTree(rowDto.getAttribute(),
                    "complexType",
                    CDMToXSD.parse(rowDto.getCardinality()),
                    NodeTreeType.COMPLEX
            ));*/
        } else {
            if( !rowDto.getAttribute().equals("") ){
                node.appendNode(new NodeTree(rowDto.getAttribute(),
                        rowDto.getType(),
                        CDMToXSD.parse(rowDto.getCardinality()),
                        NodeTreeType.SIMPLE
                ));
            }

        }
    }
}