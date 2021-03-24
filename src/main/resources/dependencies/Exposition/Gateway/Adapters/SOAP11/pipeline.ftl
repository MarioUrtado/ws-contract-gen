<?xml version="1.0" encoding="UTF-8"?>
<con:pipelineEntry xmlns:con="http://www.bea.com/wli/sb/pipeline/config" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:con1="http://www.bea.com/wli/sb/stages/config" xmlns:con2="http://www.bea.com/wli/sb/stages/routing/config">
    <con:coreEntry>
        <con:binding type="SOAP" isSoap12="false" xsi:type="con:SoapBindingType">
            <con:wsdl ref="ES_${service.name}_v${service.version}/Exposition/Contract/Primary/${service.name}_v${service.version}_ESC"/>
            <con:binding>
                <con:name>${service.name}_SOAP11Binding</con:name>
                <con:namespace>http://www.entel.cl/ESC/${service.name}/v${service.version}</con:namespace>
            </con:binding>
        </con:binding>
        <con:xqConfiguration>
            <con:snippetVersion>1.0</con:snippetVersion>
        </con:xqConfiguration>
    </con:coreEntry>
    <con:router>
        <con:pipeline type="error" name="error-N3f57fff5.N1d9835d.0.162b268b968.N7fb5">
            <con:stage id="_StageId-N3f57fff5.N1d9835d.0.162b268b968.N7fb4" name="Route Error Handler">
                <con:context/>
                <con:actions>
                    <con2:insert varName="inbound" xmlns:con2="http://www.bea.com/wli/sb/stages/transform/config" xmlns:con3="http://www.bea.com/wli/sb/stages/routing/config">
                        <con1:id>_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7e59</con1:id>
                        <con2:location>
                            <con1:xpathText>./ctx:transport/ctx:response</con1:xpathText>
                        </con2:location>
                        <con2:where>last-child</con2:where>
                        <con2:expr>
                            <con1:xqueryText>if( not(exists($HTTPStatus/@code)) or data($HTTPStatus/@code) = '') 
then () 
else &lt;http:http-response-code>{data($HTTPStatus/@code)}&lt;/http:http-response-code></con1:xqueryText>
                        </con2:expr>
                    </con2:insert>
                    <con2:insert varName="inbound" xmlns:con2="http://www.bea.com/wli/sb/stages/transform/config" xmlns:con3="http://www.bea.com/wli/sb/stages/routing/config">
                        <con1:id>_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7e56</con1:id>
                        <con2:location>
                            <con1:xpathText>./ctx:transport/ctx:response/ctx:response-code</con1:xpathText>
                        </con2:location>
                        <con2:where>after</con2:where>
                        <con2:expr>
                            <con1:xqueryText>if (data($HTTPStatus/@description) = '' or not(exists($HTTPStatus/@description))) 
then ()
else &lt;tp:response-message>{data($HTTPStatus/@description)}&lt;/tp:response-message></con1:xqueryText>
                        </con2:expr>
                    </con2:insert>
                    <con1:reply isError="true">
                        <con1:id>_ActionId-N3f57fff5.N1d9835d.0.162b268b968.N7f81</con1:id>
                    </con1:reply>
                </con:actions>
            </con:stage>
        </con:pipeline>
        <con:pipeline type="request" name="request-N3f57fff3.1083fb74.0.162dbb8052f.N7f8e"/>
        <con:pipeline type="response" name="response-N3f57fff3.1083fb74.0.162dbb8052f.N7f8d">
            <con:stage id="_StageId-N3f57fff3.1083fb74.0.162dbb8052f.N7ec0" name="Stamp HTTP Status" xmlns:con2="http://www.bea.com/wli/sb/stages/transform/config" xmlns:con3="http://www.bea.com/wli/sb/stages/routing/config">
                <con:context/>
                <con:actions>
                    <con2:insert varName="inbound">
                        <con1:id>_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7ebf</con1:id>
                        <con2:location>
                            <con1:xpathText>./ctx:transport/ctx:response</con1:xpathText>
                        </con2:location>
                        <con2:where>last-child</con2:where>
                        <con2:expr>
                            <con1:xqueryText>if( not(exists($HTTPStatus/@code)) or data($HTTPStatus/@code) = '') 
then () 
else &lt;http:http-response-code>{data($HTTPStatus/@code)}&lt;/http:http-response-code></con1:xqueryText>
                        </con2:expr>
                    </con2:insert>
                    <con2:insert varName="inbound">
                        <con1:id>_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7ebe</con1:id>
                        <con2:location>
                            <con1:xpathText>./ctx:transport/ctx:response/ctx:response-code</con1:xpathText>
                        </con2:location>
                        <con2:where>after</con2:where>
                        <con2:expr>
                            <con1:xqueryText>if (data($HTTPStatus/@description) = '' or not(exists($HTTPStatus/@description))) 
then ()
else &lt;tp:response-message>{data($HTTPStatus/@description)}&lt;/tp:response-message></con1:xqueryText>
                        </con2:expr>
                    </con2:insert>
                </con:actions>
            </con:stage>
        </con:pipeline>
        <con:flow>
            <con:pipeline-node name="Message Logic">
                <con:request>request-N3f57fff3.1083fb74.0.162dbb8052f.N7f8e</con:request>
                <con:response>response-N3f57fff3.1083fb74.0.162dbb8052f.N7f8d</con:response>
            </con:pipeline-node>
            <con:route-node name="Route To Primary Gateway" error-handler="error-N3f57fff5.N1d9835d.0.162b268b968.N7fb5">
                <con:context/>
                <con:actions>
                    <con2:route>
                        <con1:id>_ActionId-N3f57fff5.N1d9835d.0.162b268b968.N7fe7</con1:id>
                        <con2:service ref="ES_${service.name}_v${service.version}/Exposition/Gateway/Primary/${service.name}" xsi:type="ref:ProxyRef" xmlns:ref="http://www.bea.com/wli/sb/reference"/>
                        <con2:operation>${service.name}</con2:operation>
                        <con2:outboundTransform/>
                        <con2:responseTransform/>
                    </con2:route>
                </con:actions>
            </con:route-node>
        </con:flow>
        <con:shared-variables>
            <con:variable>HTTPStatus</con:variable>
        </con:shared-variables>
    </con:router>
</con:pipelineEntry>