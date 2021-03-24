package cl.entel.tde.dtm.xml.util;

public class XSDNamespaceRefImpl implements XSDNamespaceRef {

    private String namespace;

    private String prefix;

    public static XSDNamespaceRef getInstance(String namespace, String prefix){
        return new XSDNamespaceRefImpl(namespace, prefix);
    }

    public XSDNamespaceRefImpl(String namespace, String prefix){
        this.namespace = namespace;
        this.prefix = prefix;
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }
}
