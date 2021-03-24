xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://www.entel.cl/EBM/${service.name}/v${service.version}";

declare namespace esons_jsonheader="http://www.entel.cl/ESO/MessageHeader/JSON/v1";

declare namespace esons_result = "http://www.entel.cl/ESO/Result/v2";

declare namespace esons_error = "http://www.entel.cl/ESO/Error/v1";

declare variable $ServiceResponse as element() external;

declare function local:get_${service.name}_RSP($ServiceResponse as element() ) as element()  {
    <ns1:${service.name}_RSP>
        { local:get_ResponseHeader($ServiceResponse/*:ResponseHeader)}
        {$ServiceResponse/*:Body}
    </ns1:${service.name}_RSP>
};

declare function local:get_ResponseHeader($ResponseHeader as element()?) as element()  {
    <esons_jsonheader:ResponseHeader>
        <esons_jsonheader:Consumer>
            <esons_jsonheader:sysCode>{fn:data($ResponseHeader/*[1]/@sysCode)}</esons_jsonheader:sysCode>
            <esons_jsonheader:enterpriseCode>{fn:data($ResponseHeader/*[1]/@enterpriseCode)}</esons_jsonheader:enterpriseCode>
            <esons_jsonheader:countryCode>{fn:data($ResponseHeader/*[1]/@countryCode)}</esons_jsonheader:countryCode></esons_jsonheader:Consumer>
        <esons_jsonheader:Trace>
            <esons_jsonheader:clientReqTimestamp>{fn:data($ResponseHeader/*[2]/@clientReqTimestamp)}</esons_jsonheader:clientReqTimestamp>
            {
                if ($ResponseHeader/*[2]/@reqTimestamp)
                then <esons_jsonheader:reqTimestamp>{fn:data($ResponseHeader/*[2]/@reqTimestamp)}</esons_jsonheader:reqTimestamp>
                else ()
            }
            {
                if ($ResponseHeader/*[2]/@rspTimestamp)
                then <esons_jsonheader:rspTimestamp>{fn:data($ResponseHeader/*[2]/@rspTimestamp)}</esons_jsonheader:rspTimestamp>
                else ()
            }
            {
                if ($ResponseHeader/*[2]/@processID)
                then <esons_jsonheader:processID>{fn:data($ResponseHeader/*[2]/@processID)}</esons_jsonheader:processID>
                else ()
            }
            <esons_jsonheader:eventID>{fn:data($ResponseHeader/*[2]/@eventID)}</esons_jsonheader:eventID>
            {
                if ($ResponseHeader/*[2]/@sourceID)
                then <esons_jsonheader:sourceID>{fn:data($ResponseHeader/*[2]/@sourceID)}</esons_jsonheader:sourceID>
                else ()
            }
            {
                if ($ResponseHeader/*[2]/@correlationEventID)
                then <esons_jsonheader:correlationEventID>{fn:data($ResponseHeader/*[2]/@correlationEventID)}</esons_jsonheader:correlationEventID>
                else ()
            }
            <esons_jsonheader:conversationID>{fn:data($ResponseHeader/*[2]/@conversationID)}</esons_jsonheader:conversationID>
            {
                if ($ResponseHeader/*[2]/@correlationID)
                then <esons_jsonheader:correlationID>{fn:data($ResponseHeader/*[2]/@correlationID)}</esons_jsonheader:correlationID>
                else ()
            }
             <esons_jsonheader:Service>
                     <esons_jsonheader:code>{fn:data($ResponseHeader/*[2]/*[1]/@code)}</esons_jsonheader:code>
                     <esons_jsonheader:name>{fn:data($ResponseHeader/*[2]/*[1]/@name)}</esons_jsonheader:name>
                    <esons_jsonheader:operation>{fn:data($ResponseHeader/*[2]/*[1]/@operation)}</esons_jsonheader:operation>
                </esons_jsonheader:Service>
          </esons_jsonheader:Trace>
        {
            if ($ResponseHeader/*:Channel)
            then <esons_jsonheader:Channel>
                {
                    if ($ResponseHeader/*:Channel/@name)
                    then <esons_jsonheader:name>{fn:data($ResponseHeader/*:Channel/@name)}</esons_jsonheader:name>
                    else ()
                }
                {
                    if ($ResponseHeader/*:Channel/@mode)
                    then <esons_jsonheader:mode>{fn:data($ResponseHeader/*:Channel/@mode)}</esons_jsonheader:mode>
                    else ()
                }</esons_jsonheader:Channel>
            else ()
        }
        <esons_jsonheader:Result>
            <esons_jsonheader:status>{fn:data($ResponseHeader/esons_result:Result[1]/@status)}</esons_jsonheader:status>
            {
                if ($ResponseHeader/esons_result:Result[1]/@description)
                then <esons_jsonheader:description>{fn:data($ResponseHeader/esons_result:Result[1]/@description)}</esons_jsonheader:description>
                else ()
            }
            {
                if ($ResponseHeader/esons_result:Result[1]/esons_error:CanonicalError)
                then <esons_jsonheader:CanonicalError>
                    {
                        if ($ResponseHeader/esons_result:Result[1]/esons_error:CanonicalError/@type)
                        then <esons_jsonheader:type>{fn:data($ResponseHeader/esons_result:Result[1]/esons_error:CanonicalError/@type)}</esons_jsonheader:type>
                        else ()
                    }
                    {
                        if ($ResponseHeader/esons_result:Result[1]/esons_error:CanonicalError/@code)
                        then <esons_jsonheader:code>{fn:data($ResponseHeader/esons_result:Result[1]/esons_error:CanonicalError/@code)}</esons_jsonheader:code>
                        else ()
                    }
                    {
                        if ($ResponseHeader/esons_result:Result[1]/esons_error:CanonicalError/@description)
                        then <esons_jsonheader:description>{fn:data($ResponseHeader/esons_result:Result[1]/esons_error:CanonicalError/@description)}</esons_jsonheader:description>
                        else ()
                    }</esons_jsonheader:CanonicalError>
                else ()
            }
            {
                if ($ResponseHeader/esons_result:Result[1]/esons_error:SourceError)
                then <esons_jsonheader:SourceError>
                    {
                        if ($ResponseHeader/esons_result:Result[1]/esons_error:SourceError/@code)
                        then <esons_jsonheader:code>{fn:data($ResponseHeader/esons_result:Result[1]/esons_error:SourceError/@code)}</esons_jsonheader:code>
                        else ()
                    }
                    {
                        if ($ResponseHeader/esons_result:Result[1]/esons_error:SourceError/@description)
                        then <esons_jsonheader:description>{fn:data($ResponseHeader/esons_result:Result[1]/esons_error:SourceError/@description)}</esons_jsonheader:description>
                        else ()
                    }
                    <esons_jsonheader:ErrorSourceDetails>
                        {
                            if ($ResponseHeader/esons_result:Result[1]/esons_error:SourceError/esons_error:ErrorSourceDetails/@source)
                            then <esons_jsonheader:source>{fn:data($ResponseHeader/esons_result:Result[1]/esons_error:SourceError/esons_error:ErrorSourceDetails/@source)}</esons_jsonheader:source>
                            else ()
                        }
                        {
                            if ($ResponseHeader/esons_result:Result[1]/esons_error:SourceError/esons_error:ErrorSourceDetails/@details)
                            then <esons_jsonheader:details>{fn:data($ResponseHeader/esons_result:Result[1]/esons_error:SourceError/esons_error:ErrorSourceDetails/@details)}</esons_jsonheader:details>
                            else ()
                        }
                    </esons_jsonheader:ErrorSourceDetails>
                    <esons_jsonheader:SourceFault>{fn-bea:serialize($ResponseHeader/esons_result:Result[1]/esons_error:SourceError/esons_error:SourceFault)}</esons_jsonheader:SourceFault></esons_jsonheader:SourceError>
                else ()
            }
            {
                if ($ResponseHeader/esons_result:Result[1]/esons_result:CorrelativeErrors)
                then <esons_jsonheader:CorrelativeErrors>
                    {
                        for $SourceError in $ResponseHeader/esons_result:Result[1]/esons_result:CorrelativeErrors/esons_error:SourceError
                        return 
                        <esons_jsonheader:SourceError>
                            {
                                if ($SourceError/@code)
                                then <esons_jsonheader:code>{fn:data($SourceError/@code)}</esons_jsonheader:code>
                                else ()
                            }
                            {
                                if ($SourceError/@description)
                                then <esons_jsonheader:description>{fn:data($SourceError/@description)}</esons_jsonheader:description>
                                else ()
                            }
                            <esons_jsonheader:ErrorSourceDetails>
                                {
                                    if ($SourceError/esons_error:ErrorSourceDetails/@source)
                                    then <esons_jsonheader:source>{fn:data($SourceError/esons_error:ErrorSourceDetails/@source)}</esons_jsonheader:source>
                                    else ()
                                }
                                {
                                    if ($SourceError/esons_error:ErrorSourceDetails/@details)
                                    then <esons_jsonheader:details>{fn:data($SourceError/esons_error:ErrorSourceDetails/@details)}</esons_jsonheader:details>
                                    else ()
                                }
                            </esons_jsonheader:ErrorSourceDetails>
                            <esons_jsonheader:SourceFault>{fn-bea:serialize($SourceError/esons_error:SourceFault)}</esons_jsonheader:SourceFault></esons_jsonheader:SourceError>
                    }</esons_jsonheader:CorrelativeErrors>
                else ()
            }
        </esons_jsonheader:Result>
    </esons_jsonheader:ResponseHeader>
};

local:get_${service.name}_RSP($ServiceResponse)
