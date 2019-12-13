package cl.entel.tde.codegen.context;

import cl.entel.tde.codegen.domain.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Context {

    private Service service;

    private String directoryTarget;

    private Configuration configuration;

    public Context() {
        super();
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }


    public String getDirectoryTarget() {
        return directoryTarget;
    }

    public void setDirectoryTarget(String directoryTarget) {
        this.directoryTarget = directoryTarget;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
