<?xml version="1.0" encoding="UTF-8"?>
<con:pipelineEntry xmlns:con="http://www.bea.com/wli/sb/pipeline/config">
    <con:coreEntry>
        <con:binding type="Any XML"/>
        <con:xqConfiguration>
            <con:snippetVersion>1.0</con:snippetVersion>
        </con:xqConfiguration>
    </con:coreEntry>
    <con:router>
        <con:template-overrides>
            <con:action-override id="_ActionId-N53edf2ce.N7c27188c.0.15ea9cac095.N612a">
                <con1:assign xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config">
                    <con1:expr>
                        <con2:xqueryText xmlns:con2="http://www.bea.com/wli/sb/stages/config">'${provider.target.provider.code}'</con2:xqueryText>
                    </con1:expr>
                </con1:assign>
            </con:action-override>
            <con:action-override id="_ActionId-N53edf2ce.N7c27188c.0.15ea9cac095.N6121">
                <con1:assign xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config">
                    <con1:expr>
                        <con2:xqueryText xmlns:con2="http://www.bea.com/wli/sb/stages/config">'${provider.target.api.code}'</con2:xqueryText>
                    </con1:expr>
                </con1:assign>
            </con:action-override>
            <con:action-override id="_ActionId-N53edf2ce.N7c27188c.0.15ea9cac095.N6124">
                <con1:assign xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config">
                    <con1:expr>
                        <con2:xqueryText xmlns:con2="http://www.bea.com/wli/sb/stages/config">'${provider.target.operation.name}'</con2:xqueryText>
                    </con1:expr>
                </con1:assign>
            </con:action-override>
            <con:action-override id="_ActionId-N53edf2ce.N7c27188c.0.15ea9cac095.N6127">
                <con1:assign xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config">
                    <con1:expr>
                        <con2:xqueryText xmlns:con2="http://www.bea.com/wli/sb/stages/config">'${provider.target.operation.version}'</con2:xqueryText>
                    </con1:expr>
                </con1:assign>
            </con:action-override>
            <con:placeholder-override id="PlaceholderID_N53e3d824.N3ab8bf87.0.15e4e8f37e2.N7f34">
                <con:actions/>
            </con:placeholder-override>
            <con:placeholder-override id="PlaceholderID_N53e3d824.N3ab8bf87.0.15e4e8f37e2.N7f35">
                <con:actions/>
            </con:placeholder-override>
            <con:placeholder-override id="PlaceholderID_N53e3d824.N24fc6817.0.15e29261904.N7943">
                <con:actions>
                    <con1:assign varName="SourceError" xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config">
                        <con2:id xmlns:con2="http://www.bea.com/wli/sb/stages/config">_ActionId-N3f57fff3.N47b94b5b.0.163331ea4f9.N7fad</con2:id>
                        <con1:expr>
                            <con2:xqueryTransform xmlns:con2="http://www.bea.com/wli/sb/stages/config">
                                <con2:resource ref="ES_${service.name}_v${service.version}/Integration/Adapters/${provider.target.api.code}_${provider.target.operation.name}/XQuery/get_SourceErrorBinding"/>
                                <con2:param name="LegacyResponse">
                                    <con2:path>$LegacyResponse</con2:path>
                                </con2:param>
                                <con2:param name="TargetProvider">
                                    <con2:path>$TargetProvider</con2:path>
                                </con2:param>
                                <con2:param name="LegacyResult">
                                    <con2:path>$LegacyResult</con2:path>
                                </con2:param>
                                <con2:param name="TargetApi">
                                    <con2:path>$TargetApi</con2:path>
                                </con2:param>
                                <con2:param name="TargetOperation">
                                    <con2:path>$TargetOperation</con2:path>
                                </con2:param>
                            </con2:xqueryTransform>
                        </con1:expr>
                    </con1:assign>
                </con:actions>
            </con:placeholder-override>
            <con:action-override id="_ActionId-7f000001.5813685a.0.15e3a5d5488.N74af">
                <con1:route xmlns:con1="http://www.bea.com/wli/sb/stages/routing/config">
                    <con1:service ref="ES_SubmitPartyIdentificationQuiz_v1/Integration/Adapters/${provider.target.api.code}_${provider.target.operation.name}/${provider.target.api.code}_${provider.target.operation.name}" xsi:type="ref:BusinessServiceRef" xmlns:ref="http://www.bea.com/wli/sb/reference" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
                    <con1:operation>${provider.target.operation.name}</con1:operation>
                </con1:route>
            </con:action-override>
        </con:template-overrides>
    </con:router>
    <con:template ref="SR_Commons/Templates/Integrators/StandardAdapter/StandardAdapter"/>
</con:pipelineEntry>