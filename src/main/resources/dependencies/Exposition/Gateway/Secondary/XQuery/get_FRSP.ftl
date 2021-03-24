xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://www.entel.cl/EBM/${service.name}/v${service.version}";
(:: import schema at "../../../Contract/Secondary/${service.name}_v${service.version}_EBM.xsd"::)

declare namespace ns2 = "http://www.entel.cl/ESO/MessageHeader/v1";

declare namespace nsj1="http://www.entel.cl/ESO/MessageHeader/JSON/v1";

declare namespace ns3 = "http://www.entel.cl/ESO/Result/v2";

declare namespace ns4 = "http://www.entel.cl/ESO/Error/v1";

(: data($body/*:Fault/*:Code[1]/*:Value[1]) :)
declare variable $faultcode as xs:string external;

(: data($body/*:Fault/*:Reason[1]/*:Text[1]) :)
declare variable $faultreason as xs:string external;

(: data($body/*:Fault/detail[1]/*:${service.name}_FRSP[1]) :)
declare variable $ServiceFResponse as element() external;

declare function local:${service.name}_FRSP($faultcode as xs:string, 
                                              $faultreason as xs:string, 
                                              $ServiceFResponse as element() )
                                              as element() (:: schema-element(ns1:Fault) ::) {
    <ns1:Fault>
        <ns1:faultcode>{$faultcode}</ns1:faultcode>
        <ns1:faultstring>{$faultreason}</ns1:faultstring>
        <ns1:detail>
            <ns1:${service.name}_FRSP>
                {local:get_ResponseHeader($ServiceFResponse/*:ResponseHeader[1])}
                <ns1:Body></ns1:Body>
            </ns1:${service.name}_FRSP>
        </ns1:detail>
    </ns1:Fault>
};

declare function local:get_ResponseHeader($ResponseHeader as element()?) as element()  {
    <nsj1:ResponseHeader>
        <nsj1:Consumer>
            <nsj1:sysCode>{fn:data($ResponseHeader/*[1]/@sysCode)}</nsj1:sysCode>
            <nsj1:enterpriseCode>{fn:data($ResponseHeader/*[1]/@enterpriseCode)}</nsj1:enterpriseCode>
            <nsj1:countryCode>{fn:data($ResponseHeader/*[1]/@countryCode)}</nsj1:countryCode></nsj1:Consumer>
        <nsj1:Trace>
            <nsj1:clientReqTimestamp>{fn:data($ResponseHeader/*[2]/@clientReqTimestamp)}</nsj1:clientReqTimestamp>
            {
                if ($ResponseHeader/*[2]/@reqTimestamp)
                then <nsj1:reqTimestamp>{fn:data($ResponseHeader/*[2]/@reqTimestamp)}</nsj1:reqTimestamp>
                else ()
            }
            {
                if ($ResponseHeader/*[2]/@rspTimestamp)
                then <nsj1:rspTimestamp>{fn:data($ResponseHeader/*[2]/@rspTimestamp)}</nsj1:rspTimestamp>
                else ()
            }
            {
                if ($ResponseHeader/*[2]/@processID)
                then <nsj1:processID>{fn:data($ResponseHeader/*[2]/@processID)}</nsj1:processID>
                else ()
            }
            <nsj1:eventID>{fn:data($ResponseHeader/*[2]/@eventID)}</nsj1:eventID>
            {
                if ($ResponseHeader/*[2]/@sourceID)
                then <nsj1:sourceID>{fn:data($ResponseHeader/*[2]/@sourceID)}</nsj1:sourceID>
                else ()
            }
            {
                if ($ResponseHeader/*[2]/@correlationEventID)
                then <nsj1:correlationEventID>{fn:data($ResponseHeader/*[2]/@correlationEventID)}</nsj1:correlationEventID>
                else ()
            }
            <nsj1:conversationID>{fn:data($ResponseHeader/*[2]/@conversationID)}</nsj1:conversationID>
            {
                if ($ResponseHeader/*[2]/@correlationID)
                then <nsj1:correlationID>{fn:data($ResponseHeader/*[2]/@correlationID)}</nsj1:correlationID>
                else ()
            }
             <nsj1:Service>
                     <nsj1:code>{fn:data($ResponseHeader/*[2]/*[1]/@code)}</nsj1:code>
                     <nsj1:name>{fn:data($ResponseHeader/*[2]/*[1]/@name)}</nsj1:name>
                    <nsj1:operation>{fn:data($ResponseHeader/*[2]/*[1]/@operation)}</nsj1:operation>
                </nsj1:Service>
          </nsj1:Trace>
        {
            if ($ResponseHeader/*:Channel)
            then <nsj1:Channel>
                {
                    if ($ResponseHeader/*:Channel/@name)
                    then <nsj1:name>{fn:data($ResponseHeader/*:Channel/@name)}</nsj1:name>
                    else ()
                }
                {
                    if ($ResponseHeader/*:Channel/@mode)
                    then <nsj1:mode>{fn:data($ResponseHeader/*:Channel/@mode)}</nsj1:mode>
                    else ()
                }</nsj1:Channel>
            else ()
        }
        <nsj1:Result>
            <nsj1:status>{fn:data($ResponseHeader/ns3:Result[1]/@status)}</nsj1:status>
            {
                if ($ResponseHeader/ns3:Result[1]/@description)
                then <nsj1:description>{fn:data($ResponseHeader/ns3:Result[1]/@description)}</nsj1:description>
                else ()
            }
            {
                if ($ResponseHeader/ns3:Result[1]/ns4:CanonicalError)
                then <nsj1:CanonicalError>
                    {
                        if ($ResponseHeader/ns3:Result[1]/ns4:CanonicalError/@type)
                        then <nsj1:type>{fn:data($ResponseHeader/ns3:Result[1]/ns4:CanonicalError/@type)}</nsj1:type>
                        else ()
                    }
                    {
                        if ($ResponseHeader/ns3:Result[1]/ns4:CanonicalError/@code)
                        then <nsj1:code>{fn:data($ResponseHeader/ns3:Result[1]/ns4:CanonicalError/@code)}</nsj1:code>
                        else ()
                    }
                    {
                        if ($ResponseHeader/ns3:Result[1]/ns4:CanonicalError/@description)
                        then <nsj1:description>{fn:data($ResponseHeader/ns3:Result[1]/ns4:CanonicalError/@description)}</nsj1:description>
                        else ()
                    }</nsj1:CanonicalError>
                else ()
            }
            {
                if ($ResponseHeader/ns3:Result[1]/ns4:SourceError)
                then <nsj1:SourceError>
                    {
                        if ($ResponseHeader/ns3:Result[1]/ns4:SourceError/@code)
                        then <nsj1:code>{fn:data($ResponseHeader/ns3:Result[1]/ns4:SourceError/@code)}</nsj1:code>
                        else ()
                    }
                    {
                        if ($ResponseHeader/ns3:Result[1]/ns4:SourceError/@description)
                        then <nsj1:description>{fn:data($ResponseHeader/ns3:Result[1]/ns4:SourceError/@description)}</nsj1:description>
                        else ()
                    }
                    <nsj1:ErrorSourceDetails>
                        {
                            if ($ResponseHeader/ns3:Result[1]/ns4:SourceError/ns4:ErrorSourceDetails/@source)
                            then <nsj1:source>{fn:data($ResponseHeader/ns3:Result[1]/ns4:SourceError/ns4:ErrorSourceDetails/@source)}</nsj1:source>
                            else ()
                        }
                        {
                            if ($ResponseHeader/ns3:Result[1]/ns4:SourceError/ns4:ErrorSourceDetails/@details)
                            then <nsj1:details>{fn:data($ResponseHeader/ns3:Result[1]/ns4:SourceError/ns4:ErrorSourceDetails/@details)}</nsj1:details>
                            else ()
                        }
                    </nsj1:ErrorSourceDetails>
                    <nsj1:SourceFault>{fn-bea:serialize($ResponseHeader/ns3:Result[1]/ns4:SourceError/ns4:SourceFault)}</nsj1:SourceFault></nsj1:SourceError>
                else ()
            }
            {
                if ($ResponseHeader/ns3:Result[1]/ns3:CorrelativeErrors)
                then <nsj1:CorrelativeErrors>
                    {
                        for $SourceError in $ResponseHeader/ns3:Result[1]/ns3:CorrelativeErrors/ns4:SourceError
                        return 
                        <nsj1:SourceError>
                            {
                                if ($SourceError/@code)
                                then <nsj1:code>{fn:data($SourceError/@code)}</nsj1:code>
                                else ()
                            }
                            {
                                if ($SourceError/@description)
                                then <nsj1:description>{fn:data($SourceError/@description)}</nsj1:description>
                                else ()
                            }
                            <nsj1:ErrorSourceDetails>
                                {
                                    if ($SourceError/ns4:ErrorSourceDetails/@source)
                                    then <nsj1:source>{fn:data($SourceError/ns4:ErrorSourceDetails/@source)}</nsj1:source>
                                    else ()
                                }
                                {
                                    if ($SourceError/ns4:ErrorSourceDetails/@details)
                                    then <nsj1:details>{fn:data($SourceError/ns4:ErrorSourceDetails/@details)}</nsj1:details>
                                    else ()
                                }
                            </nsj1:ErrorSourceDetails>
                            <nsj1:SourceFault>{fn-bea:serialize($SourceError/ns4:SourceFault)}</nsj1:SourceFault></nsj1:SourceError>
                    }</nsj1:CorrelativeErrors>
                else ()
            }
        </nsj1:Result>
    </nsj1:ResponseHeader>
};


local:${service.name}_FRSP($faultcode, $faultreason, $ServiceFResponse)
