package cl.entel.tde.codegen.context;

public class Service {

    private String name;

    private String version;

    private String code;

    public Service(String name, String version, String code) {
        this.name = name;
        this.version = version;
        this.code = code;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
