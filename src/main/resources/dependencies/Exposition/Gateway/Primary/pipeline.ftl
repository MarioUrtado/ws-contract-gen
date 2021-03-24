<?xml version="1.0" encoding="UTF-8"?>
<con:pipelineEntry xmlns:con="http://www.bea.com/wli/sb/pipeline/config" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <con:coreEntry>
        <con:binding type="SOAP" isSoap12="true" xsi:type="con:SoapBindingType">
            <con:wsdl ref="ES_${service.name}_v${service.version}/Exposition/Contract/Primary/${service.name}_v${service.version}_ESC"/>
            <con:binding>
                <con:name>${service.name}_SOAP12Binding</con:name>
                <con:namespace>http://www.entel.cl/ESC/${service.name}/v${service.version}</con:namespace>
            </con:binding>
        </con:binding>
        <con:xqConfiguration>
            <con:snippetVersion>1.0</con:snippetVersion>
        </con:xqConfiguration>
    </con:coreEntry>
    <con:router>
        <con:template-overrides>
            <con:action-override id="_ActionId-N53ee2ce5.6c4c042e.0.15e959e35ab.N743c">
                <con1:validate xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config">
                    <con1:schema ref="ES_${service.name}_v${service.version}/Exposition/Contract/Primary/ESH/MessageHeader_v1_ESO"/>
                    <con1:schemaElement xmlns:v1="http://www.entel.cl/ESO/MessageHeader/v1">v1:RequestHeader</con1:schemaElement>
                </con1:validate>
            </con:action-override>
            <con:placeholder-override id="PlaceholderID_N53ee2ce5.6c4c042e.0.15e959e35ab.N7793">
                <con:actions>
                    <con1:validate xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config">
                        <con2:id xmlns:con2="http://www.bea.com/wli/sb/stages/config">_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7e0a</con2:id>
                        <con1:schema ref="ES_${service.name}_v${service.version}/Exposition/Contract/Primary/${service.name}_v${service.version}_EBM"/>
                        <con1:schemaElement xmlns:v1="http://www.entel.cl/EBM/${service.name}/v${service.version}">v1:${service.name}_REQ</con1:schemaElement>
                        <con1:varName>body</con1:varName>
                        <con1:location>
                            <con2:xpathText xmlns:con2="http://www.bea.com/wli/sb/stages/config">./*[1]</con2:xpathText>
                        </con1:location>
                    </con1:validate>
                </con:actions>
            </con:placeholder-override>
            <con:placeholder-override id="PlaceholderID_N53ee2ce5.6c4c042e.0.15e959e35ab.N7790">
                <con:actions>
                    <con1:validate xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config">
                        <con2:id xmlns:con2="http://www.bea.com/wli/sb/stages/config">_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7dd7</con2:id>
                        <con1:schema ref="ES_${service.name}_v${service.version}/Exposition/Contract/Primary/${service.name}_v${service.version}_EBM"/>
                        <con1:schemaElement xmlns:v1="http://www.entel.cl/EBM/${service.name}/v${service.version}">v1:${service.name}_RSP</con1:schemaElement>
                        <con1:varName>body</con1:varName>
                        <con1:location>
                            <con2:xpathText xmlns:con2="http://www.bea.com/wli/sb/stages/config">./*[1]</con2:xpathText>
                        </con1:location>
                    </con1:validate>
                </con:actions>
            </con:placeholder-override>
            <con:placeholder-override id="PlaceholderID_N53ee2cf2.652fd7ba.0.15ffe492c86.N6fe1">
                <con:actions/>
            </con:placeholder-override>
            <con:placeholder-override id="PlaceholderID_N53ee2ce5.6c4c042e.0.15e959e35ab.N7686">
                <con:actions/>
            </con:placeholder-override>
            <con:placeholder-override id="PlaceholderID_N53ee2ce5.6c4c042e.0.15e959e35ab.N75b8">
                <con:actions>
                    <con1:replace varName="body" contents-only="true" xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config">
                        <con2:id xmlns:con2="http://www.bea.com/wli/sb/stages/config">_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7da4</con2:id>
                        <con1:expr>
                            <con2:xqueryTransform xmlns:con2="http://www.bea.com/wli/sb/stages/config">
                                <con2:resource ref="ES_${service.name}_v${service.version}/Exposition/Gateway/Primary/XQuery/get_${service.name}_FRSP"/>
                                <con2:param name="MessageHeader">
                                    <con2:path>$MessageHeader</con2:path>
                                </con2:param>
                                <con2:param name="Result">
                                    <con2:path>$Result</con2:path>
                                </con2:param>
                            </con2:xqueryTransform>
                        </con1:expr>
                    </con1:replace>
                </con:actions>
            </con:placeholder-override>
            <con:action-override id="_ActionId-N53ee2ce5.6c4c042e.0.15e959e35ab.N7688">
                <con1:route xmlns:con1="http://www.bea.com/wli/sb/stages/routing/config">
                    <con1:service/>
                </con1:route>
            </con:action-override>
        </con:template-overrides>
    </con:router>
    <con:template ref="SR_Commons/Templates/Gateways/StandardGateway/StandardGateway"/>
</con:pipelineEntry>
