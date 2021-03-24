package cl.entel.tde.dtm.util;

public class ExcelRowDelimiter {

    private static String requestBegin;

    private static String requestEnd;

    private static String responseBegin;

    private static String responseEnd;

    private static String failureBegin;

    private static String failureEnd;

    private static String callbackBegin;

    private static String callbackEnd;

    public static String getRequestBegin() {
        return requestBegin;
    }

    public static void setRequestBegin(String requestBegin) {
        if (requestBegin != null){
            ExcelRowDelimiter.requestBegin = requestBegin;
        }
    }

    public static String getRequestEnd() {
        return requestEnd;
    }

    public static void setRequestEnd(String requestEnd) {
        if (requestEnd != null){
            ExcelRowDelimiter.requestEnd = requestEnd;
        }
    }

    public static String getResponseBegin() {
        return responseBegin;
    }

    public static void setResponseBegin(String responseBegin) {
        if (responseBegin != null){
            ExcelRowDelimiter.responseBegin = responseBegin;
        }
    }

    public static String getResponseEnd() {
        return responseEnd;
    }

    public static void setResponseEnd(String responseEnd) {
        if (responseEnd != null){
            ExcelRowDelimiter.responseEnd = responseEnd;
        }
    }

    public static String getFailureBegin() {
        return failureBegin;
    }

    public static void setFailureBegin(String failureBegin) {
        if ( failureBegin != null){
            ExcelRowDelimiter.failureBegin = failureBegin;
        }
    }

    public static String getFailureEnd() {
        return failureEnd;
    }

    public static void setFailureEnd(String failureEnd) {
        if (failureEnd != null){
            ExcelRowDelimiter.failureEnd = failureEnd;
        }
    }

    public static String getCallbackBegin() {
        return callbackBegin;
    }

    public static void setCallbackBegin(String callbackBegin) {
        if (callbackBegin != null){
            ExcelRowDelimiter.callbackBegin = callbackBegin;
        }
    }

    public static String getCallbackEnd() {
        return callbackEnd;
    }

    public static void setCallbackEnd(String callbackEnd) {
        if (callbackEnd != null){
            ExcelRowDelimiter.callbackEnd = callbackEnd;
        }
    }
}
