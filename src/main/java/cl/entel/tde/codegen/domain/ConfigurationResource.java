package cl.entel.tde.codegen.domain;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationResource {

    private String resource;

    private String bucket;

    private String path;

    private Map<String, String> properties;

    public ConfigurationResource() {
        this.properties = new HashMap<>();
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
