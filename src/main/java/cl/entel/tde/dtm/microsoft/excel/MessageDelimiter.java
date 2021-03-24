package cl.entel.tde.dtm.microsoft.excel;

public class MessageDelimiter {

    public static String requestBegin;

    public static String requestEnd;

    public static String responseBegin;

    public static String responseEnd;

    public static void setRequestBegin(String requestBegin) {
        if(requestBegin != null){
            MessageDelimiter.requestBegin = requestBegin;
        }
    }

    public static void setRequestEnd(String requestEnd) {
        if (requestEnd != null) {
            MessageDelimiter.requestEnd = requestEnd;
        }
    }

    public static void setResponseBegin(String responseBegin) {
        if (responseBegin != null){
            MessageDelimiter.responseBegin = responseBegin;
        }
    }

    public static void setResponseEnd(String responseEnd) {
        if (responseEnd != null){
            MessageDelimiter.responseEnd = responseEnd;
        }
    }
}

