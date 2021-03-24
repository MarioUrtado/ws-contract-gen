package cl.entel.tde.dtm.xml;

import cl.entel.tde.dtm.util.Service;

public class SOAPDefinition {

    @Deprecated
    public static String  WSDL_SOAP11_BINDING_NAME(String serviceName){
        return serviceName + "_SOAP11Binding";
    }

    @Deprecated
    public static String  WSDL_SOAP12_BINDING_NAME(String serviceName){
        return serviceName + "_SOAP12Binding";
    }

    @Deprecated
    public static String WSDL_SOAP11_SERVICE_PORT_NAME(){
        return Service.getName() + "_SOAP11_Port";
    }

    @Deprecated
    public static String WSDL_SOAP12_SERVICE_PORT_NAME(){
        return Service.getName() + "_SOAP12_Port";
    }

    public static String WSDL_SOAP11_SERVICE_PORT_LOCATION(){ return "http://www.entel.cl/ES/11/" + Service.getName() + "/v" + Service.getVersion(); }

    public static String WSDL_SOAP12_SERVICE_PORT_LOCATION(){ return "http://www.entel.cl/ES/" + Service.getName() + "/v" + Service.getVersion(); }


}
