package cl.entel.tde.dtm.xml;

import cl.entel.tde.dtm.util.Service;

public class Namespace {

    public static String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";

    public static String WSDL_DEFINITION = "http://schemas.xmlsoap.org/wsdl/";

    public static String WSDL_HTTP = "http://schemas.xmlsoap.org/wsdl/http/";

    public static String WSDL_SOAP_HTTP = "http://schemas.xmlsoap.org/soap/http";

    public static String WSDL_SOAP11 = "http://schemas.xmlsoap.org/wsdl/soap/";

    public static String WSDL_SOAP12 = "http://schemas.xmlsoap.org/wsdl/soap12/";

    public static String XSD_ESO_MESSAGEHEADER = "http://www.entel.cl/ESO/MessageHeader/v1";

    public static String XSD_ESO_MESSAGEHEADER_JSON = "http://www.entel.cl/ESO/MessageHeader/JSON/v1";

    private static String XSD_EBM_NAMESPACE_TEMPLATE = "http://www.entel.cl/EBM/SERVICE_NAME/vSERVICE_VERSION";

    private static String WSDL_ESC_NAMESPACE_TEMPLATE = "http://www.entel.cl/ESC/SERVICE_NAME/vSERVICE_VERSION";

    public static String WADL_SOA_REST = "http://www.oracle.com/soa/rest";

    public static String WADL_APPLICATION= "http://wadl.dev.java.net/2009/02";

    public static String XSD_EBM_NAMESPACE(){
        return Namespace.XSD_EBM_NAMESPACE_TEMPLATE.replace("SERVICE_NAME", Service.getName()).replace("SERVICE_VERSION", Service.getVersion());
    }

    public static String WSDL_ESC_NAMESPACE(){
        return Namespace.WSDL_ESC_NAMESPACE_TEMPLATE.replace("SERVICE_NAME", Service.getName()).replace("SERVICE_VERSION", Service.getVersion());
    }



}
