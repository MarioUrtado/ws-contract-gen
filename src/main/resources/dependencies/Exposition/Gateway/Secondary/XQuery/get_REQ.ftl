xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://www.entel.cl/EBM/${service.name}/v${service.version}";
(:: import schema at "../../../Contract/Primary/${service.name}_v${service.version}_EBM.xsd" ::)

declare namespace ns2 = "http://www.entel.cl/ESO/MessageHeader/v1";

declare namespace ns4 = "http://www.entel.cl/ESO/Error/v1";

declare namespace ns3 = "http://www.entel.cl/ESO/Result/v2";

declare namespace jns1="http://www.entel.cl/ESO/MessageHeader/JSON/v1";

declare variable $ServiceRequest as element()  external;

declare function local:get_${service.name}_REQ($ServiceRequest as element() ) as element()  {
    <ns1:${service.name}_REQ>
        {local:get_RequestHeader($ServiceRequest/*:RequestHeader[1] )}
        {$ServiceRequest/ns1:Body}
    </ns1:${service.name}_REQ>
};

declare function local:get_RequestHeader($JsonRequestHeader as element()? ) as element()  {
    <ns2:RequestHeader>
        <ns2:Consumer sysCode="{fn:data($JsonRequestHeader/jns1:Consumer/jns1:sysCode)}" enterpriseCode="{fn:data($JsonRequestHeader/jns1:Consumer/jns1:enterpriseCode)}"
            countryCode="{fn:data($JsonRequestHeader/jns1:Consumer/jns1:countryCode)}">
        </ns2:Consumer>
        <ns2:Trace clientReqTimestamp="{fn:data($JsonRequestHeader/jns1:Trace/jns1:clientReqTimestamp)}" eventID="{fn:data($JsonRequestHeader/jns1:Trace/jns1:eventID)}">
            {
                if ($JsonRequestHeader/jns1:Trace/jns1:reqTimestamp)
                then attribute reqTimestamp {fn:data($JsonRequestHeader/jns1:Trace/jns1:reqTimestamp)}
                else ()
            }
            {
                if ($JsonRequestHeader/jns1:Trace/jns1:rspTimestamp)
                then attribute rspTimestamp {fn:data($JsonRequestHeader/jns1:Trace/jns1:rspTimestamp)}
                else ()
            }
            {
                if ($JsonRequestHeader/jns1:Trace/jns1:processID)
                then attribute processID {fn:data($JsonRequestHeader/jns1:Trace/jns1:processID)}
                else ()
            }
            {
                if ($JsonRequestHeader/jns1:Trace/jns1:sourceID)
                then attribute sourceID {fn:data($JsonRequestHeader/jns1:Trace/jns1:sourceID)}
                else ()
            }
            {
                if ($JsonRequestHeader/jns1:Trace/jns1:correlationEventID)
                then attribute correlationEventID {fn:data($JsonRequestHeader/jns1:Trace/jns1:correlationEventID)}
                else ()
            }
            {
                if ($JsonRequestHeader/jns1:Trace/jns1:conversationID)
                then attribute conversationID {fn:data($JsonRequestHeader/jns1:Trace/jns1:conversationID)}
                else ()
            }
            {
                if ($JsonRequestHeader/jns1:Trace/jns1:correlationID)
                then attribute correlationID {fn:data($JsonRequestHeader/jns1:Trace/jns1:correlationID)}
                else ()
            }
        </ns2:Trace>
        {
            if ($JsonRequestHeader/jns1:Channel)
            then <ns2:Channel>
                {
                    if ($JsonRequestHeader/jns1:Channel/jns1:name)
                    then attribute name {fn:data($JsonRequestHeader/jns1:Channel/jns1:name)}
                    else ()
                }
                {
                    if ($JsonRequestHeader/jns1:Channel/jns1:mode)
                    then attribute mode {fn:data($JsonRequestHeader/jns1:Channel/jns1:mode)}
                    else ()
                }</ns2:Channel>
            else ()
        }
    </ns2:RequestHeader>
};

local:get_${service.name}_REQ($ServiceRequest)
