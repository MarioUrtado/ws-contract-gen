xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns2="http://www.entel.cl/ESO/Error/v1";

declare variable $LegacyResponse as element()  external;
declare variable $LegacyResult as element()  external;
declare variable $TargetProvider as xs:string external;
declare variable $TargetApi as xs:string external;
declare variable $TargetOperation as xs:string external;

declare function local:get_SourceErrorCode() as xs:string{
  let $SourceErrorCode := if (exists($LegacyResponse)) 
                          then data($LegacyResponse)
                          else data($LegacyResult)
  return $SourceErrorCode
};

declare function local:get_SourceErrorDescription() as xs:string{
  let $SourceErrorDescription := ''
  return $SourceErrorDescription
};

declare function local:get_SourceErrorBinding($LegacyResponse as element(),
                                              $LegacyResult as element(),
                                              $TargetProvider as xs:string,
                                              $TargetApi as xs:string,
                                              $TargetOperation as xs:string) as element() {

    let $SourceErrorCode := local:get_SourceErrorCode()
    let $SourceErrorDescription := local:get_SourceErrorDescription()
    return 

    <ns2:SourceError  code="{$SourceErrorCode}" description="{$SourceErrorDescription}">
        <ns2:ErrorSourceDetails source="{$TargetProvider}" details="{concat($TargetApi, '_',$TargetOperation )}" />
        <ns2:SourceFault>{$LegacyResponse}</ns2:SourceFault>
    </ns2:SourceError>
};

local:get_SourceErrorBinding($LegacyResponse, $LegacyResult, $TargetProvider, $TargetApi, $TargetOperation)
