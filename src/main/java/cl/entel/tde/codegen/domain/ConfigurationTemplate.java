package cl.entel.tde.codegen.domain;

import java.util.List;

public class ConfigurationTemplate {

    private String description;

    private List<String> resources;

    private String version;

    public ConfigurationTemplate() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
