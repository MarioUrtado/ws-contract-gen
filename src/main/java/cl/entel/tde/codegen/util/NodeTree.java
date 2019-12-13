package cl.entel.tde.codegen.util;

import cl.entel.tde.codegen.xsd.cardinality.Cardinality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeTree {

    private List<NodeTree> childs;

    private Map<String, NodeTree> hashChilds;

    private String name;

    private String type;

    private Cardinality cardinality;

    private NodeTreeType nodeType;

    public NodeTree(){
        this.childs = new ArrayList<NodeTree>();
        this.hashChilds = new HashMap<String, NodeTree>();
    }

    public NodeTree(String name, String type, Cardinality cardinality, NodeTreeType nodeTreeType){
        this.childs = new ArrayList<NodeTree>();
        this.hashChilds = new HashMap<String, NodeTree>();
        this.name = name;
        this.type = type;
        this.cardinality = cardinality;
        this.nodeType = nodeTreeType;
    }

    public List<NodeTree> getChilds() {
        return childs;
    }

    public void setChilds(List<NodeTree> childs) {
        this.childs = childs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Cardinality getCardinality() {
        return cardinality;
    }

    public void setCardinality(Cardinality cardinality) {
        this.cardinality = cardinality;
    }

    public NodeTreeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeTreeType nodeType) {
        this.nodeType = nodeType;
    }

    public Map<String, NodeTree> getHashChilds() {
        return hashChilds;
    }

    public void setHashChilds(Map<String, NodeTree> hashChilds) {
        this.hashChilds = hashChilds;
    }


    public void appendNode(NodeTree node){
        if (this.hashChilds.get(node.getName()) == null){
            this.childs.add(node);
            this.hashChilds.put(node.getName(), node);
        }

    }

    public NodeTree get(String name){
        return hashChilds.get(name);
    }

    public NodeTree get(int index){
        return childs.get(index);
    }

}