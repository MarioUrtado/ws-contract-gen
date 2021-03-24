xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns2="http://www.entel.cl/ESO/Exception/JSON/v1";
declare namespace ns1="http://www.entel.cl/ESO/Exception/v1";

declare namespace ns3 = "http://www.entel.cl/ESO/MessageHeader/JSON/v1";

declare function local:getResult( $result as element()  ) as element() {
  <ns3:Result>
      <ns3:status>{fn:data($result/@status)}</ns3:status>
      {
          if ($result/@description)
          then <ns3:description>{fn:data($result/@description)}</ns3:description>
          else ()
      }
      {
          if ($result/*:CanonicalError[1])
          then <ns3:CanonicalError>
              {
                  if ($result/*:CanonicalError/@type)
                  then <ns3:type>{fn:data($result/*:CanonicalError/@type)}</ns3:type>
                  else ()
              }
              {
                  if ($result/*:CanonicalError/@code)
                  then <ns3:code>{fn:data($result/*:CanonicalError/@code)}</ns3:code>
                  else ()
              }
              {
                  if ($result/*:CanonicalError/@description)
                  then <ns3:description>{fn:data($result/*:CanonicalError/@description)}</ns3:description>
                  else ()
              }</ns3:CanonicalError>
          else ()
      }
      {
          if ($result/*:SourceError)
          then <ns3:SourceError>
              {
                  if ($result/*:SourceError/@code)
                  then <ns3:code>{fn:data($result/*:SourceError/@code)}</ns3:code>
                  else ()
              }
              {
                  if ($result/*:SourceError/@description)
                  then <ns3:description>{fn:data($result/*:SourceError/@description)}</ns3:description>
                  else ()
              }
              <ns3:ErrorSourceDetails>
                  {
                      if ($result/*:SourceError/*:ErrorSourceDetails/@source)
                      then <ns3:source>{fn:data($result/*:SourceError/*:ErrorSourceDetails/@source)}</ns3:source>
                      else ()
                  }
                  <ns3:details></ns3:details>
              </ns3:ErrorSourceDetails>
              <ns3:SourceFault>{fn-bea:serialize($result/*:SourceError/*:SourceFault/*[1])}</ns3:SourceFault></ns3:SourceError>
          else ()
      }
      <ns3:CorrelativeErrors>
          {
              for $SourceError in $result/*:CorrelativeErrors/*:SourceError
              return 
              <ns3:SourceError>
                  {
                      if ($SourceError/@code)
                      then <ns3:code>{fn:data($SourceError/@code)}</ns3:code>
                      else ()
                  }
                  {
                      if ($SourceError/@description)
                      then <ns3:description>{fn:data($SourceError/@description)}</ns3:description>
                      else ()
                  }
                  <ns3:ErrorSourceDetails>
                      {
                          if ($SourceError/*:ErrorSourceDetails/@source)
                          then <ns3:source>{fn:data($SourceError/*:ErrorSourceDetails/@source)}</ns3:source>
                          else ()
                      }
                      {
                          if ($SourceError/*:ErrorSourceDetails/@details)
                          then <ns3:details>{fn:data($SourceError/*:ErrorSourceDetails/@details)}</ns3:details>
                          else ()
                      }
                  </ns3:ErrorSourceDetails>
                  <ns3:SourceFault>{fn-bea:serialize($SourceError/*:SourceFault)}</ns3:SourceFault></ns3:SourceError>
          }</ns3:CorrelativeErrors>
  </ns3:Result>
};

declare function local:getResponseHeader( $responseHeader as element() ) as element(ns3:ResponseHeader){
  <ns3:ResponseHeader>
      <ns3:Consumer>
          <ns3:sysCode>{fn:data($responseHeader/*[1]/@sysCode)}</ns3:sysCode>
          <ns3:enterpriseCode>{fn:data($responseHeader/*[1]/@enterpriseCode)}</ns3:enterpriseCode>
          <ns3:countryCode>{fn:data($responseHeader/*[1]/@countryCode)}</ns3:countryCode>
      </ns3:Consumer>
      <ns3:Trace>
          <ns3:clientReqTimestamp>{fn:data($responseHeader/*[2]/@clientReqTimestamp)}</ns3:clientReqTimestamp>
          <ns3:reqTimestamp>{fn:data($responseHeader/*[2]/@reqTimestamp)}</ns3:reqTimestamp>
          <ns3:rspTimestamp>{fn:data($responseHeader/*[2]/@rspTimestamp)}</ns3:rspTimestamp>
          <ns3:processID>{fn:data($responseHeader/*[2]/@processID)}</ns3:processID>
          <ns3:eventID>{fn:data($responseHeader/*[2]/@eventID)}</ns3:eventID>
          <ns3:sourceID>{fn:data($responseHeader/*[2]/@sourceID)}</ns3:sourceID>
          <ns3:correlationEventID>{fn:data($responseHeader/*[2]/@correlationEventID)}</ns3:correlationEventID>
          <ns3:conversationID>{fn:data($responseHeader/*[2]/@conversationID)}</ns3:conversationID>
          <ns3:correlationID>{fn:data($responseHeader/*[2]/@correlationID)}</ns3:correlationID>
          <ns3:Service>
                  <ns3:code>{fn:data($responseHeader/*[2]/*[1]/@code)}</ns3:code>
                  <ns3:name>{fn:data($responseHeader/*[2]/*[1]/@name)}</ns3:name>
                  <ns3:operation>{fn:data($responseHeader/*[2]/*[1]/@operation)}</ns3:operation>
          </ns3:Service>
 
      </ns3:Trace>
      {
          if ($responseHeader/*:Channel[1])
          then <ns3:Channel>
              {
                  if ($responseHeader/*:Channel[1]/@name)
                  then <ns3:name>{fn:data($responseHeader/*:Channel[1]/@name)}</ns3:name>
                  else ()
              }
              {
                  if ($responseHeader/*:Channel[1]/@mode)
                  then <ns3:mode>{fn:data($responseHeader/*:Channel[1]/@mode)}</ns3:mode>
                  else ()
              }</ns3:Channel>
          else ()
      }
      { local:getResult( $responseHeader/*:Result[1] ) }
  </ns3:ResponseHeader>
};

declare variable $unexpectedErrorException as element() external;
declare variable $faultstring as xs:string external;
declare variable $faultcode as xs:string external;

declare function local:func($faultstring as xs:string, $faultcode as xs:string, $detail as element()) as element()  {
  <ns2:Fault>
      <ns2:faultcode>{fn:data($faultcode)}</ns2:faultcode>
      <ns2:faultstring>{fn:data($faultstring)}</ns2:faultstring>
      <ns2:detail>
         {
           if ( $detail/*:Result[1] )
           then (
             local:getResult($detail/*:Result[1])
           )
           else (
             <ns2:UnexpectedErrorException>
              <ns2:exceptionDetail>{fn:data($detail/ns1:UnexpectedErrorException/@exceptionDetail)}</ns2:exceptionDetail>
              {
                if ( $detail/*:UnexpectedErrorException[1]/*:ResponseHeader[1] )
                then (
                  local:getResponseHeader( $detail/*:UnexpectedErrorException[1]/*:ResponseHeader[1])
                )
                else (
                   local:getResult($detail/*:UnexpectedErrorException[1]/*:Result[1])
                )
              }
            </ns2:UnexpectedErrorException>
           )
         }
      </ns2:detail>
  </ns2:Fault>
};

local:func($faultstring, $faultcode ,$unexpectedErrorException)