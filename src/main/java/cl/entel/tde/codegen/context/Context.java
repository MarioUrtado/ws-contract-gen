package cl.entel.tde.codegen.context;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Context {

    private Service service;



    private String directoryTarget;

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
}
