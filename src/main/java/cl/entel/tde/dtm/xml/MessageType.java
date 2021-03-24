package cl.entel.tde.dtm.xml;

public class MessageType {
    public static String IN = "In";
    public static String OUT = "Out";
    public static String FAULT = "Fault";
    public static String CALLBACK = "Callback";

    public static String XSD_PORT_TYPE_OPERATION(String messageType){
        if (MessageType.IN.equals(messageType)){
            return "input";
        } else if (MessageType.OUT.equals(messageType)){
            return "output";
        } else if (MessageType.FAULT.equals(messageType)){
            return "fault";
        } else if (MessageType.CALLBACK.equals(messageType)){
            return "output";
        }
        return null;
    }
}
