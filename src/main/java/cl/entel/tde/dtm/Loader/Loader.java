package cl.entel.tde.dtm.Loader;

import cl.entel.tde.dtm.microsoft.excel.MessageDelimiter;
import cl.entel.tde.dtm.util.ExcelRowDelimiter;
import cl.entel.tde.dtm.util.ProjectPath;
import cl.entel.tde.dtm.util.project.ProjectProperties;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Loader {

    private static Properties properties;

    public static void load(){
        properties = new Properties();
        try {
            InputStream in = Loader.class.getResourceAsStream("/application.properties");
            properties.load(in);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Properties getProperties(){
        Loader.load();
        return properties;
    }

    public static void loadExcelDelimiters(){
        ExcelRowDelimiter.setRequestBegin(properties.getProperty("dtm.request.delimiter.begin"));
        ExcelRowDelimiter.setRequestEnd(properties.getProperty("dtm.request.delimiter.end"));

        ExcelRowDelimiter.setResponseBegin(properties.getProperty("dtm.response.delimiter.begin"));
        ExcelRowDelimiter.setResponseEnd(properties.getProperty("dtm.response.delimiter.end"));
    }

    public static void loadProjectPaths(){

        ProjectProperties.setContractPrimary(properties.getProperty("proyect.directory.exposition.contract.primary"));
        ProjectProperties.setContractPrimaryEsh(properties.getProperty("proyect.directory.exposition.contract.primary.esh"));

        ProjectProperties.setContractSecondary(properties.getProperty("proyect.directory.exposition.contract.secondary"));
        ProjectProperties.setContractSecondaryEsh(properties.getProperty("proyect.directory.exposition.contract.secondary.esh"));

        ProjectProperties.setGatewayPrimary(properties.getProperty("proyect.directory.exposition.gateway.primary"));
        ProjectProperties.setGatewayPrimaryXquery(properties.getProperty("proyect.directory.exposition.gateway.primary.xquery"));

        ProjectProperties.setGatewaySecondary(properties.getProperty("proyect.directory.exposition.gateway.secondary"));
        ProjectProperties.setGatewaySecondaryXquery(properties.getProperty("proyect.directory.exposition.gateway.secondary.xquery"));

        ProjectProperties.setExpositionGatewayAdapterSoap11(properties.getProperty("proyect.directory.exposition.gateway.adapter.SOAP11"));

        ProjectProperties.setCoordinationAggregator(properties.getProperty("proyect.directory.coordination.aggregator"));
        ProjectProperties.setCoordinationCountrybroker(properties.getProperty("proyect.directory.coordination.countryBroker"));

        ProjectProperties.setIntegrationAdapters(properties.getProperty("proyect.directory.integration.adapters"));
        ProjectProperties.setIntegrationAdapters(properties.getProperty("proyect.directory.exposition.gateway.adapter.SOAP11"));

    }

    public static void loadDelimiter(){
        MessageDelimiter.setRequestBegin(properties.getProperty("dtm.request.delimiter.begin"));
        MessageDelimiter.setRequestEnd(properties.getProperty("dtm.request.delimiter.end"));
        MessageDelimiter.setResponseBegin(properties.getProperty("dtm.response.delimiter.begin"));
        MessageDelimiter.setResponseEnd(properties.getProperty("dtm.response.delimiter.end"));
    }

}