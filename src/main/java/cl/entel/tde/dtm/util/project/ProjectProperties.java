package cl.entel.tde.dtm.util.project;

public class ProjectProperties {

    public static String CONTRACT_SECONDARY;

    public static String CONTRACT_SECONDARY_ESH;

    public static String CONTRACT_PRIMARY;

    public static String CONTRACT_PRIMARY_ESH;

    public static String GATEWAY_SECONDARY;

    public static String GATEWAY_PRIMARY;

    public static String GATEWAY_SECONDARY_XQUERY;

    public static String GATEWAY_PRIMARY_XQUERY;

    public static String COORDINATION_AGGREGATOR;

    public static String COORDINATION_COUNTRYBROKER;

    public static String INTEGRATION_ADAPTERS;

    public static String EXPOSITION_GATEWAY_ADAPTER_SOAP11;

    public static String getExpositionGatewayAdapterSoap11() {
        return EXPOSITION_GATEWAY_ADAPTER_SOAP11;
    }

    public static void setExpositionGatewayAdapterSoap11(String expositionGatewayAdapterSoap11) {
        if (expositionGatewayAdapterSoap11 != null) {
            EXPOSITION_GATEWAY_ADAPTER_SOAP11 = expositionGatewayAdapterSoap11;
        }
    }

    public static String getContractSecondary() {
        return CONTRACT_SECONDARY;
    }

    public static void setContractSecondary(String contractSecondary) {
        if (contractSecondary != null){
            CONTRACT_SECONDARY = contractSecondary;
        }
    }

    public static String getContractSecondaryEsh() {
        return CONTRACT_SECONDARY_ESH;
    }

    public static void setContractSecondaryEsh(String contractSecondaryEsh) {
        if (contractSecondaryEsh != null){
            CONTRACT_SECONDARY_ESH = contractSecondaryEsh;
        }
    }

    public static String getContractPrimary() {
        return CONTRACT_PRIMARY;
    }

    public static void setContractPrimary(String contractPrimary) {
        if (contractPrimary != null){
            CONTRACT_PRIMARY = contractPrimary;
        }
    }

    public static String getContractPrimaryEsh() {
        return CONTRACT_PRIMARY_ESH;
    }

    public static void setContractPrimaryEsh(String contractPrimaryEsh) {
        if (contractPrimaryEsh != null){
            CONTRACT_PRIMARY_ESH = contractPrimaryEsh;
        }
    }

    public static String getGatewaySecondary() {
        return GATEWAY_SECONDARY;
    }

    public static void setGatewaySecondary(String gatewaySecondary) {
        if (gatewaySecondary != null){
            GATEWAY_SECONDARY = gatewaySecondary;
        }
    }

    public static String getGatewayPrimary() {
        return GATEWAY_PRIMARY;
    }

    public static void setGatewayPrimary(String gatewayPrimary) {
        if (gatewayPrimary != null){
            GATEWAY_PRIMARY = gatewayPrimary;
        }
    }

    public static String getGatewaySecondaryXquery() {
        return GATEWAY_SECONDARY_XQUERY;
    }

    public static void setGatewaySecondaryXquery(String gatewaySecondaryXquery) {
        if (gatewaySecondaryXquery != null){
            GATEWAY_SECONDARY_XQUERY = gatewaySecondaryXquery;
        }
    }

    public static String getGatewayPrimaryXquery() {
        return GATEWAY_PRIMARY_XQUERY;
    }

    public static void setGatewayPrimaryXquery(String gatewayPrimaryXquery) {
        if (gatewayPrimaryXquery != null){
            GATEWAY_PRIMARY_XQUERY = gatewayPrimaryXquery;
        }
    }

    public static String getCoordinationAggregator() {
        return COORDINATION_AGGREGATOR;
    }

    public static void setCoordinationAggregator(String coordinationAggregator) {
        if (coordinationAggregator != null) {
            COORDINATION_AGGREGATOR = coordinationAggregator;
        }
    }

    public static String getCoordinationCountrybroker() {
        return COORDINATION_COUNTRYBROKER;
    }

    public static void setCoordinationCountrybroker(String coordinationCountrybroker) {
        if (coordinationCountrybroker != null) {
            COORDINATION_COUNTRYBROKER = coordinationCountrybroker;
        }
    }

    public static String getIntegrationAdapters() {
        return INTEGRATION_ADAPTERS;
    }

    public static void setIntegrationAdapters(String integrationAdapters) {
        if (integrationAdapters != null) {
            INTEGRATION_ADAPTERS = integrationAdapters;
        }
    }
}
