package cl.entel.tde.codegen.domain;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private String version;

    private Map<String, ConfigurationNamespaces> namespaces;

    private Map<String, ConfigurationResource> resources;

    private Map<String, ConfigurationTemplate> templates;

    public Configuration() {
        this.namespaces = new HashMap<>();
        this.resources = new HashMap<>();
        this.templates = new HashMap<>();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, ConfigurationNamespaces> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(Map<String, ConfigurationNamespaces> namespaces) {
        this.namespaces = namespaces;
    }

    public Map<String, ConfigurationResource> getResources() {
        return resources;
    }

    public void setResources(Map<String, ConfigurationResource> resources) {
        this.resources = resources;
    }

    public Map<String, ConfigurationTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, ConfigurationTemplate> templates) {
        this.templates = templates;
    }
}
