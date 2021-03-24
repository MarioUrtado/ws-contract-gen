package cl.entel.tde.dtm;

import cl.entel.tde.dtm.Loader.Loader;
import cl.entel.tde.dtm.Parser.Parser;
import cl.entel.tde.dtm.freemaker.Factory;
import cl.entel.tde.dtm.util.*;
import cl.entel.tde.dtm.util.project.ProjectFolder;
import cl.entel.tde.dtm.util.project.ProjectProperties;
import cl.entel.tde.dtm.ws.xsd.cardinality.Cardinality;
import cl.entel.tde.dtm.xml.*;
import org.w3c.dom.Document;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String [] args){

        try {

            String filename = args[0];
            String dirTarget = args[1];

            File excel = new File(filename);
            if ( ! excel.exists() || excel.isDirectory()){
                System.out.println("Path of excel file is invalid");
                return;
            }
            File dirTargetPath = new File(dirTarget);
            if ( ! dirTargetPath.exists() ){
                if (! dirTargetPath.mkdirs()){
                    System.out.println("Can't access to: " + dirTarget + " . And Can't create the drectory.");
                    return;
                }
            }

            Loader.load();
            Loader.loadExcelDelimiters();
            Loader.loadProjectPaths();
            XMLWriter writer = new XMLWriter();
            ProjectPath.setDirrectoryTarget(dirTarget);

            ProjectFolder projectFolder = new ProjectFolder();
            projectFolder.createFolders();

            String serviceName = Parser.getServiceName(filename);
            String serviceVersion = Parser.getServiceVersion(filename);
            Service.setName(serviceName);
            Service.setVersion(serviceVersion);

            //String filename = excelPath;
            Map<String, NodeTree> nodes = new HashMap<String, NodeTree>();

            nodes.put("REQ", Parser.parse(filename));
            nodes.put("RSP", Parser.parseForResponse(filename));
            nodes.put("FRSP", new NodeTree("Body", "complexType", new Cardinality(1,1), NodeTreeType.COMPLEX));

            Document ebm= FactoryXSD.getInstance(nodes);
            String xsdFilename = Service.getName() + "_v" + Service.getVersion() + "_EBM.xsd";
            writer.write(ebm, (ProjectPath.concat(dirTarget, ProjectProperties.CONTRACT_PRIMARY)+File.separator+ xsdFilename));

            Factory factory = new Factory();
            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getContractPrimary(),
                    "wsdl" ,
                    Service.getName() + "_v" + Service.getVersion() + "_ESC.wsdl"
            );
            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getContractSecondary(),
                    "wadl" ,
                    Service.getName() + "_v" + Service.getVersion() + "_ESC.wadl"
            );

            Document nxsd= FactoryNXSD.getInstance(nodes);
            String nxsdFilename = Service.getName() + "_v" + Service.getVersion() + "_EBM.xsd";
            writer.write(nxsd, (ProjectPath.concat(dirTarget, ProjectProperties.CONTRACT_SECONDARY)+File.separator+ nxsdFilename));

            ExportFile.ExportResource("/dependencies" + ProjectProperties.CONTRACT_PRIMARY_ESH +"/Dictionary_v1_EDD.xsd", ProjectPath.concat(dirTarget,ProjectProperties.CONTRACT_PRIMARY_ESH), "Dictionary_v1_EDD.xsd");
            ExportFile.ExportResource("/dependencies" + ProjectProperties.CONTRACT_PRIMARY_ESH +"/Error_v1_ESO.xsd", ProjectPath.concat(dirTarget,ProjectProperties.CONTRACT_PRIMARY_ESH), "Error_v1_ESO.xsd");
            ExportFile.ExportResource("/dependencies" + ProjectProperties.CONTRACT_PRIMARY_ESH +"/Exception_v1_ESO.xsd", ProjectPath.concat(dirTarget,ProjectProperties.CONTRACT_PRIMARY_ESH), "Exception_v1_ESO.xsd");
            ExportFile.ExportResource("/dependencies" + ProjectProperties.CONTRACT_PRIMARY_ESH +"/MessageHeader_v1_ESO.xsd", ProjectPath.concat(dirTarget, ProjectProperties.CONTRACT_PRIMARY_ESH), "MessageHeader_v1_ESO.xsd");
            ExportFile.ExportResource("/dependencies" + ProjectProperties.CONTRACT_PRIMARY_ESH +"/Result_v2_ESO.xsd", ProjectPath.concat(dirTarget, ProjectProperties.CONTRACT_PRIMARY_ESH), "Result_v2_ESO.xsd");
            ExportFile.ExportResource("/dependencies" + ProjectProperties.CONTRACT_SECONDARY_ESH +"/Exception_v1_ESOJ.xsd", ProjectPath.concat(dirTarget,ProjectProperties.CONTRACT_SECONDARY_ESH), "Exception_v1_ESOJ.xsd");
            ExportFile.ExportResource("/dependencies" + ProjectProperties.CONTRACT_SECONDARY_ESH +"/MessageHeader_v1_ESOJ.xsd", ProjectPath.concat(dirTarget,ProjectProperties.CONTRACT_SECONDARY_ESH), "MessageHeader_v1_ESOJ.xsd");
            ExportFile.ExportResource("/dependencies" + ProjectProperties.GATEWAY_SECONDARY_XQUERY +"/get_UnexpectedErrorException.xqy", ProjectPath.concat(dirTarget,ProjectProperties.GATEWAY_SECONDARY_XQUERY), "get_UnexpectedErrorException.xqy");



            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getGatewayPrimaryXquery(),
                    "get_FRSP" ,
                    "get_" + Service.getName() + "_FRSP.xqy"
            );

            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getGatewaySecondaryXquery(),
                    "get_REQ" ,
                    "get_" + Service.getName() + "_REQ.xqy"
            );
            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getGatewaySecondaryXquery(),
                    "get_RSP" ,
                    "get_" + Service.getName() + "_RSP.xqy"
            );
            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getGatewaySecondaryXquery(),
                    "get_FRSP" ,
                    "get_" + Service.getName() + "_FRSP.xqy"
            );
            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getGatewaySecondary(),
                    "pipeline" ,
                    Service.getName() + "_REST.pipeline"
            );
            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getGatewaySecondary(),
                    "proxy" ,
                    Service.getName() + "_REST.proxy"
            );

            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getExpositionGatewayAdapterSoap11(),
                    "proxy" ,
                    Service.getName() + "_SOAP11.proxy"
            );
            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getExpositionGatewayAdapterSoap11(),
                    "pipeline" ,
                    Service.getName() + "_SOAP11.pipeline"
            );
            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getGatewayPrimary(),
                    "pipeline" ,
                    Service.getName() + ".pipeline"
            );
            factory.generate(
                    ProjectPath.DIRRECTORY_TARGET,
                    ProjectProperties.getGatewayPrimary(),
                    "proxy" ,
                    Service.getName() + ".proxy"
            );
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}