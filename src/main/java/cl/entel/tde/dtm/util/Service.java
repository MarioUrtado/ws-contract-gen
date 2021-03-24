package cl.entel.tde.dtm.util;

public class Service {

    private static String name;

    private static String version;

    public static String getName(){
        return Service.name;
    }

    public static void setName(String name){
        Service.name = name;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        Service.version = version;
    }
}
