<?xml version="1.0" encoding="UTF-8"?>
<ser:proxyServiceEntry xmlns:ser="http://www.bea.com/wli/sb/services" xmlns:con="http://www.bea.com/wli/sb/services/security/config" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:oper="http://xmlns.oracle.com/servicebus/proxy/operations" xmlns:tran="http://www.bea.com/wli/sb/transports" xmlns:env="http://www.bea.com/wli/config/env">
    <ser:coreEntry>
        <ser:security>
            <con:inboundWss processWssHeader="true"/>
        </ser:security>
        <ser:binding type="native REST" xsi:type="con:NativeRestBindingType" xmlns:con="http://www.bea.com/wli/sb/services/bindings/config">
            <con:wadl ref="ES_${service.name}_v${service.version}/Exposition/Contract/Secondary/${service.name}_v${service.version}_ESC"/>
        </ser:binding>
        <oper:operations enabled="true"/>
        <ser:ws-policy>
            <ser:binding-mode>no-policies</ser:binding-mode>
        </ser:ws-policy>
        <ser:invoke ref="ES_${service.name}_v${service.version}/Exposition/Gateway/Secondary/${service.name}_REST" xsi:type="con:PipelineRef" xmlns:con="http://www.bea.com/wli/sb/pipeline/config"/>
        <ser:xqConfiguration>
            <ser:snippetVersion>1.0</ser:snippetVersion>
        </ser:xqConfiguration>
    </ser:coreEntry>
    <ser:endpointConfig>
        <tran:provider-id>http</tran:provider-id>
        <tran:inbound>true</tran:inbound>
        <tran:URI>
            <env:value>/ES/JSON/${service.name}/v${service.version}</env:value>
        </tran:URI>
        <tran:inbound-properties/>
        <tran:provider-specific xsi:type="http:HttpEndPointConfiguration" xmlns:http="http://www.bea.com/wli/sb/transports/http">
            <http:inbound-properties/>
            <http:compression>
                <http:compression-support>false</http:compression-support>
            </http:compression>
        </tran:provider-specific>
    </ser:endpointConfig>
</ser:proxyServiceEntry>