<?xml version="1.0" encoding="UTF-8"?>
<ser:proxyServiceEntry xmlns:ser="http://www.bea.com/wli/sb/services" xmlns:oper="http://xmlns.oracle.com/servicebus/proxy/operations" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:tran="http://www.bea.com/wli/sb/transports">
    <ser:coreEntry>
        <ser:binding type="abstract XML"/>
        <oper:operations enabled="true"/>
        <ser:invoke ref="ES_${service.name}_v${service.version}/Integration/Adapters/${provider.api.code}_${provider.operation.name}/${provider.api.code}_${provider.operation.name}" xsi:type="con:PipelineRef" xmlns:con="http://www.bea.com/wli/sb/pipeline/config"/>
        <ser:xqConfiguration>
            <ser:snippetVersion>1.0</ser:snippetVersion>
        </ser:xqConfiguration>
    </ser:coreEntry>
    <ser:endpointConfig>
        <tran:provider-id>local</tran:provider-id>
        <tran:inbound>true</tran:inbound>
        <tran:inbound-properties/>
    </ser:endpointConfig>
</ser:proxyServiceEntry>