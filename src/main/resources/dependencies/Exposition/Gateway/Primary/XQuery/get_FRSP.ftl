xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://www.entel.cl/EBM/${service.name}/v${service.version}";
(:: import schema at "../../../Contract/Primary/${service.name}_v${service.version}_EBM.xsd" ::)

declare variable $MessageHeader as element() external;
declare variable $Result as element() external;

declare function local:get_${service.name}_FRSP($MessageHeader as element(),  $Result as element()) as element() (:: schema-element(ns1:${service.name}_FRSP) ::) {
    <ns1:${service.name}_FRSP>
        {$MessageHeader/*[1]}
        {$MessageHeader/*[2]}
        {$MessageHeader/*[3]}
        {$Result}
        <ns1:Body></ns1:Body>
    </ns1:${service.name}_FRSP>
};

local:get_${service.name}_FRSP($MessageHeader, $Result)