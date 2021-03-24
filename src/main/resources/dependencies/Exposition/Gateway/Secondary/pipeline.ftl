<?xml version="1.0" encoding="UTF-8"?>
<con:pipelineEntry xmlns:con="http://www.bea.com/wli/sb/pipeline/config" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config" xmlns:con2="http://www.bea.com/wli/sb/stages/config" xmlns:con3="http://www.bea.com/wli/sb/stages/routing/config">
    <con:coreEntry>
        <con:binding type="Native REST" xsi:type="con:NativeRestBindingType">
            <con:wadl ref="ES_${service.name}_v${service.version}/Exposition/Contract/Secondary/${service.name}_v${service.version}_ESC"/>
        </con:binding>
        <con:xqConfiguration>
            <con:snippetVersion>1.0</con:snippetVersion>
        </con:xqConfiguration>
    </con:coreEntry>
    <con:router errorHandler="error-N53e3edd3.N5ec6440a.0.161e2298a95.N7e89">
        <con:pipeline type="request" name="request-N53e3edd3.N1eec741a.0.161de9265f9.N8000">
            <con:stage id="_StageId-N53e3edd3.N1eec741a.0.161de9265f9.N7ffe" name="Marshall Request">
                <con:context/>
                <con:actions>
                    <con1:nxsdTranslation>
                        <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7f67</con2:id>
                        <con1:type>Native-To-XML</con1:type>
                        <con1:sourceExpr>
                            <con2:xqueryText>$body/text()</con2:xqueryText>
                        </con1:sourceExpr>
                        <con1:nxsd ref="ES_${service.name}_v${service.version}/Exposition/Contract/Secondary/${service.name}_v${service.version}_EBM"/>
                        <con1:schemaElement xmlns:v1="http://www.entel.cl/EBM/${service.name}/v${service.version}">v1:${service.name}_REQ</con1:schemaElement>
                        <con1:replace-body-content/>
                        <con1:enforceSchemaOrder>true</con1:enforceSchemaOrder>
                    </con1:nxsdTranslation>
                    <con1:replace contents-only="true" varName="body">
                        <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7f64</con2:id>
                        <con1:expr>
                            <con2:xqueryTransform>
                                <con2:resource ref="ES_${service.name}_v${service.version}/Exposition/Gateway/Secondary/XQuery/get_${service.name}_REQ"/>
                                <con2:param name="ServiceRequest">
                                    <con2:path>$body/*[1]</con2:path>
                                </con2:param>
                            </con2:xqueryTransform>
                        </con1:expr>
                    </con1:replace>
                </con:actions>
            </con:stage>
        </con:pipeline>
        <con:pipeline type="response" name="response-N53e3edd3.N1eec741a.0.161de9265f9.N7fff">
            <con:stage id="_StageId-N3f57fff3.1083fb74.0.162dbb8052f.N7f97" name="Stamp HTTP Status" xmlns:con1="http://www.bea.com/wli/sb/stages/config" xmlns:con2="http://www.bea.com/wli/sb/stages/transform/config">
                <con:context/>
                <con:actions>
                    <con2:insert varName="inbound">
                        <con4:id xmlns:con4="http://www.bea.com/wli/sb/stages/config">_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7f96</con4:id>
                        <con2:location>
                            <con1:xpathText xmlns:con4="http://www.bea.com/wli/sb/stages/config">./ctx:transport/ctx:response</con1:xpathText>
                        </con2:location>
                        <con2:where>last-child</con2:where>
                        <con2:expr>
                            <con1:xqueryText xmlns:con4="http://www.bea.com/wli/sb/stages/config">if( not(exists($HTTPStatus/@code)) or data($HTTPStatus/@code) = '') 
then () 
else &lt;http:http-response-code>{data($HTTPStatus/@code)}&lt;/http:http-response-code></con1:xqueryText>
                        </con2:expr>
                    </con2:insert>
                    <con2:insert varName="inbound">
                        <con4:id xmlns:con4="http://www.bea.com/wli/sb/stages/config">_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7f95</con4:id>
                        <con2:location>
                            <con1:xpathText xmlns:con4="http://www.bea.com/wli/sb/stages/config">./ctx:transport/ctx:response/ctx:response-code</con1:xpathText>
                        </con2:location>
                        <con2:where>after</con2:where>
                        <con2:expr>
                            <con1:xqueryText xmlns:con4="http://www.bea.com/wli/sb/stages/config">if (data($HTTPStatus/@description) = '' or not(exists($HTTPStatus/@description))) 
then ()
else &lt;tp:response-message>{data($HTTPStatus/@description)}&lt;/tp:response-message></con1:xqueryText>
                        </con2:expr>
                    </con2:insert>
                </con:actions>
            </con:stage>
            <con:stage id="_StageId-N53e3edd3.N1eec741a.0.161de9265f9.N7ffd" name="Marshall Response">
                <con:context/>
                <con:actions>
                    <con1:replace varName="body" contents-only="true">
                        <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7f5e</con2:id>
                        <con1:expr>
                            <con2:xqueryTransform>
                                <con2:resource ref="ES_${service.name}_v${service.version}/Exposition/Gateway/Secondary/XQuery/get_${service.name}_RSP"/>
                                <con2:param name="ServiceResponse">
                                    <con2:path>$body/*[1]</con2:path>
                                </con2:param>
                            </con2:xqueryTransform>
                        </con1:expr>
                    </con1:replace>
                    <con1:nxsdTranslation>
                        <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7f61</con2:id>
                        <con1:type>XML-To-Native</con1:type>
                        <con1:sourceExpr>
                            <con2:xqueryText>$body/*[1]</con2:xqueryText>
                        </con1:sourceExpr>
                        <con1:nxsd ref="ES_${service.name}_v${service.version}/Exposition/Contract/Secondary/${service.name}_v${service.version}_EBM"/>
                        <con1:schemaElement xmlns:v1="http://www.entel.cl/EBM/${service.name}/v${service.version}">v1:${service.name}_RSP</con1:schemaElement>
                        <con1:replace-body-content/>
                    </con1:nxsdTranslation>
                    <con1:transport-headers>
                        <con2:id>_ActionId-N53e3edd3.N5ec6440a.0.161e2298a95.N7ddc</con2:id>
                        <con1:header-set>inbound-response</con1:header-set>
                        <con1:header value="expression" name="Content-Type">
                            <con2:xqueryText>'application/json'</con2:xqueryText>
                        </con1:header>
                    </con1:transport-headers>
                </con:actions>
            </con:stage>
        </con:pipeline>
        <con:pipeline type="error" name="error-N53e3edd3.N1eec741a.0.161de9265f9.N7efb">
            <con:stage id="_StageId-N53e3edd3.N1eec741a.0.161de9265f9.N7efa" name="Route Error Handler">
                <con:context/>
                <con:actions>
                    <con1:ifThenElse>
                        <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7ebf</con2:id>
                        <con1:case id="_BranchId-N53e3edd3.N1eec741a.0.161de9265f9.N7ebe">
                            <con1:condition>
                                <con2:xqueryText>(: ${service.name}_FRSP on Response :)
exists($body/*:Fault/detail[1]/*:${service.name}_FRSP[1])</con2:xqueryText>
                            </con1:condition>
                            <con1:actions>
                                <con1:replace varName="body" contents-only="true">
                                    <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7e8a</con2:id>
                                    <con1:expr>
                                        <con2:xqueryTransform>
                                            <con2:resource ref="ES_${service.name}_v${service.version}/Exposition/Gateway/Secondary/XQuery/get_${service.name}_FRSP"/>
                                            <con2:param name="faultcode">
                                                <con2:path>data($body/*:Fault/*:faultcode[1])</con2:path>
                                            </con2:param>
                                            <con2:param name="ServiceFResponse">
                                                <con2:path>$body/*:Fault/detail[1]/*:${service.name}_FRSP[1]</con2:path>
                                            </con2:param>
                                            <con2:param name="faultreason">
                                                <con2:path>data($body/*:Fault/*:faultstring[1])</con2:path>
                                            </con2:param>
                                        </con2:xqueryTransform>
                                    </con1:expr>
                                </con1:replace>
                                <con1:nxsdTranslation>
                                    <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7e84</con2:id>
                                    <con1:type>XML-To-Native</con1:type>
                                    <con1:sourceExpr>
                                        <con2:xqueryText>$body/*[1]</con2:xqueryText>
                                    </con1:sourceExpr>
                                    <con1:nxsd ref="ES_${service.name}_v${service.version}/Exposition/Contract/Secondary/${service.name}_v${service.version}_EBM"/>
                                    <con1:schemaElement xmlns:v1="http://www.entel.cl/EBM/${service.name}/v${service.version}">v1:Fault</con1:schemaElement>
                                    <con1:replace-body-content/>
                                </con1:nxsdTranslation>
                            </con1:actions>
                        </con1:case>
                        <con1:default>
                            <con1:replace contents-only="true" varName="body">
                                <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7e7a</con2:id>
                                <con1:expr>
                                    <con2:xqueryTransform>
                                        <con2:resource ref="ES_${service.name}_v${service.version}/Exposition/Gateway/Secondary/XQuery/get_UnexpectedErrorException"/>
                                        <con2:param name="faultstring">
                                            <con2:path>data($body/*:Fault/*:faultstring[1])</con2:path>
                                        </con2:param>
                                        <con2:param name="faultcode">
                                            <con2:path>data($body/*:Fault/*:faultcode[1])</con2:path>
                                        </con2:param>
                                        <con2:param name="unexpectedErrorException">
                                            <con2:path>$body/*:Fault/*:detail[1]</con2:path>
                                        </con2:param>
                                    </con2:xqueryTransform>
                                </con1:expr>
                            </con1:replace>
                            <con1:nxsdTranslation>
                                <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7e77</con2:id>
                                <con1:type>XML-To-Native</con1:type>
                                <con1:sourceExpr>
                                    <con2:xqueryText>$body/*[1]</con2:xqueryText>
                                </con1:sourceExpr>
                                <con1:nxsd ref="ES_${service.name}_v${service.version}/Exposition/Contract/Secondary/ESH/Exception_v1_ESOJ"/>
                                <con1:schemaElement xmlns:v1="http://www.entel.cl/ESO/Exception/JSON/v1">v1:Fault</con1:schemaElement>
                                <con1:replace-body-content/>
                            </con1:nxsdTranslation>
                        </con1:default>
                    </con1:ifThenElse>
                    <con1:transport-headers>
                        <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7e81</con2:id>
                        <con1:header-set>inbound-response</con1:header-set>
                        <con1:header value="expression" name="Content-Type">
                            <con2:xqueryText>'application/json'</con2:xqueryText>
                        </con1:header>
                    </con1:transport-headers>
                    <con2:insert varName="inbound" xmlns:con1="http://www.bea.com/wli/sb/stages/config" xmlns:con2="http://www.bea.com/wli/sb/stages/transform/config">
                        <con4:id xmlns:con4="http://www.bea.com/wli/sb/stages/config">_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7f92</con4:id>
                        <con2:location>
                            <con1:xpathText xmlns:con4="http://www.bea.com/wli/sb/stages/config">./ctx:transport/ctx:response</con1:xpathText>
                        </con2:location>
                        <con2:where>last-child</con2:where>
                        <con2:expr>
                            <con1:xqueryText xmlns:con4="http://www.bea.com/wli/sb/stages/config">if( not(exists($HTTPStatus/@code)) or data($HTTPStatus/@code) = '') 
then () 
else &lt;http:http-response-code>{data($HTTPStatus/@code)}&lt;/http:http-response-code></con1:xqueryText>
                        </con2:expr>
                    </con2:insert>
                    <con2:insert varName="inbound" xmlns:con1="http://www.bea.com/wli/sb/stages/config" xmlns:con2="http://www.bea.com/wli/sb/stages/transform/config">
                        <con4:id xmlns:con4="http://www.bea.com/wli/sb/stages/config">_ActionId-N3f57fff3.1083fb74.0.162dbb8052f.N7f8f</con4:id>
                        <con2:location>
                            <con1:xpathText xmlns:con4="http://www.bea.com/wli/sb/stages/config">./ctx:transport/ctx:response/ctx:response-code</con1:xpathText>
                        </con2:location>
                        <con2:where>after</con2:where>
                        <con2:expr>
                            <con1:xqueryText xmlns:con4="http://www.bea.com/wli/sb/stages/config">if (data($HTTPStatus/@description) = '' or not(exists($HTTPStatus/@description))) 
then ()
else &lt;tp:response-message>{data($HTTPStatus/@description)}&lt;/tp:response-message></con1:xqueryText>
                        </con2:expr>
                    </con2:insert>
                    <con2:reply isError="true">
                        <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7e7e</con2:id>
                    </con2:reply>
                </con:actions>
            </con:stage>
        </con:pipeline>
        <con:pipeline type="error" name="error-N53e3edd3.N5ec6440a.0.161e2298a95.N7e89">
            <con:stage id="_StageId-N53e3edd3.N5ec6440a.0.161e2298a95.N7e1c" name="GenericFailure">
                <con:context/>
                <con:actions>
                    <con1:replace varName="Result">
                        <con2:id>_ActionId-N53e3edd3.N5ec6440a.0.161e2298a95.N7e1b</con2:id>
                        <con1:expr>
                            <con2:xqueryTransform>
                                <con2:resource ref="SR_Commons/XQuery/get_Result_Simple"/>
                                <con2:param name="resultStatus">
                                    <con2:path>'ERROR'</con2:path>
                                </con2:param>
                                <con2:param name="sourceErrorDescription">
                                    <con2:path>$operation</con2:path>
                                </con2:param>
                                <con2:param name="canonicalErrorType">
                                    <con2:path>'TEC'</con2:path>
                                </con2:param>
                                <con2:param name="errorSourceCode">
                                    <con2:path>'FRW'</con2:path>
                                </con2:param>
                                <con2:param name="canonicalErrorCode">
                                    <con2:path>'-1'</con2:path>
                                </con2:param>
                                <con2:param name="errorSourceDetails">
                                    <con2:path>data($fault/ctx:location)</con2:path>
                                </con2:param>
                                <con2:param name="sourceErrorCode">
                                    <con2:path>'99'</con2:path>
                                </con2:param>
                                <con2:param name="canonicalErrorDescription">
                                    <con2:path>'NO SE ENCONTRARON TRADUCCIONES'</con2:path>
                                </con2:param>
                                <con2:param name="resultDescription">
                                    <con2:path>'Transaccion terminada con errores'</con2:path>
                                </con2:param>
                                <con2:param name="sourceFault">
                                    <con2:path>$body</con2:path>
                                </con2:param>
                            </con2:xqueryTransform>
                        </con1:expr>
                    </con1:replace>
                    <con1:replace varName="body" contents-only="true">
                        <con2:id>_ActionId-N53e3edd3.N5ec6440a.0.161e2298a95.N7e1a</con2:id>
                        <con1:expr>
                            <con2:xqueryTransform>
                                <con2:resource ref="ES_${service.name}_v${service.version}/Exposition/Gateway/Secondary/XQuery/get_UnexpectedErrorException"/>
                                <con2:param name="faultstring">
                                    <con2:path>data($fault/ctx:reason)</con2:path>
                                </con2:param>
                                <con2:param name="faultcode">
                                    <con2:path>data($fault/ctx:errorCode)</con2:path>
                                </con2:param>
                                <con2:param name="unexpectedErrorException">
                                    <con2:path>&lt;wrapper>{$Result}&lt;/wrapper></con2:path>
                                </con2:param>
                            </con2:xqueryTransform>
                        </con1:expr>
                    </con1:replace>
                    <con1:nxsdTranslation>
                        <con2:id>_ActionId-N53e3edd3.N5ec6440a.0.161e2298a95.N7e19</con2:id>
                        <con1:type>XML-To-Native</con1:type>
                        <con1:sourceExpr>
                            <con2:xqueryText>$body/*[1]</con2:xqueryText>
                        </con1:sourceExpr>
                        <con1:nxsd ref="ES_${service.name}_v${service.version}/Exposition/Contract/Secondary/ESH/Exception_v1_ESOJ"/>
                        <con1:schemaElement xmlns:v1="http://www.entel.cl/ESO/Exception/JSON/v1">v1:Fault</con1:schemaElement>
                        <con1:replace-body-content/>
                    </con1:nxsdTranslation>
                    <con1:transport-headers>
                        <con2:id>_ActionId-N53e3edd3.N5ec6440a.0.161e2298a95.N7de5</con2:id>
                        <con1:header-set>inbound-response</con1:header-set>
                        <con1:header value="expression" name="Content-Type">
                            <con2:xqueryText>'application/json'</con2:xqueryText>
                        </con1:header>
                    </con1:transport-headers>
                    <con2:reply isError="true">
                        <con2:id>_ActionId-N53e3edd3.N5ec6440a.0.161e2298a95.N7de2</con2:id>
                    </con2:reply>
                </con:actions>
            </con:stage>
        </con:pipeline>
        <con:flow>
            <con:pipeline-node name="Message Transformation">
                <con:request>request-N53e3edd3.N1eec741a.0.161de9265f9.N8000</con:request>
                <con:response>response-N53e3edd3.N1eec741a.0.161de9265f9.N7fff</con:response>
            </con:pipeline-node>
            <con:route-node name="Route To Gateway" error-handler="error-N53e3edd3.N1eec741a.0.161de9265f9.N7efb">
                <con:context/>
                <con:actions>
                    <con3:route>
                        <con2:id>_ActionId-N53e3edd3.N1eec741a.0.161de9265f9.N7e87</con2:id>
                        <con3:service ref="ES_${service.name}_v${service.version}/Exposition/Gateway/Primary/${service.name}" xsi:type="ref:ProxyRef" xmlns:ref="http://www.bea.com/wli/sb/reference"/>
                        <con3:operation>${service.name}</con3:operation>
                        <con3:outboundTransform/>
                        <con3:responseTransform/>
                    </con3:route>
                </con:actions>
            </con:route-node>
        </con:flow>
        <con:shared-variables>
            <con:variable>HTTPStatus</con:variable>
        </con:shared-variables>
    </con:router>
</con:pipelineEntry>