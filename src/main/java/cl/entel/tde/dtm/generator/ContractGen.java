package cl.entel.tde.dtm.generator;

import cl.entel.tde.codegen.context.Context;
import cl.entel.tde.codegen.context.Service;
import cl.entel.tde.codegen.contract.xsd.FactoryNXSD;
import cl.entel.tde.codegen.contract.xsd.FactoryXSD;
import cl.entel.tde.codegen.contract.xsd.XMLWriter;
import cl.entel.tde.codegen.freemaker.Factory;
import cl.entel.tde.dtm.Parser.Parser;
import cl.entel.tde.dtm.util.ExportFile;
import cl.entel.tde.dtm.util.NodeTree;
import cl.entel.tde.dtm.util.NodeTreeType;
import cl.entel.tde.dtm.util.ProjectPath;
import cl.entel.tde.dtm.ws.xsd.cardinality.Cardinality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class ContractGen {

    @Value("${dtm.input}")
    private String input;

    @Autowired
    private Parser parser;

    @Autowired
    private Factory factory;

    @Autowired
    private ExportFile exportFile;

    @Value("${contract.output.directory}")
    private String dirTarget;

    @Autowired
    private FactoryNXSD factoryNXSD;

    @Autowired
    private FactoryXSD factoryXSD;

    @Autowired
    private Context context;

    public void build() throws Exception{


        XMLWriter writer = new XMLWriter();
        context.setService(new Service(parser.getServiceName(input), parser.getServiceVersion(input), ""));
        context.setDirectoryTarget(dirTarget);

        //String filename = excelPath;
        Map<String, NodeTree> nodes = new HashMap<String, NodeTree>();

        nodes.put("REQ", parser.parse(input));
        nodes.put("RSP", parser.parseForResponse(input));
        nodes.put("FRSP", new NodeTree("Body", "complexType", new Cardinality(1, 1), NodeTreeType.COMPLEX));

        Document ebm = factoryXSD.getInstance(nodes, context);
        String xsdFilename = context.getService().getName() + "_v" + context.getService().getVersion() + "_EBM.xsd";
        writer.write(ebm, (ProjectPath.concat(context.getDirectoryTarget(), "/Primary/ESH") + File.separator + xsdFilename));

        Map<String, Object> map = new HashMap<>();
        map.put("context", context);

        factory.generate(
                context.getDirectoryTarget(),
                "/Primary/ESH",
                "wsdl",
                context.getService().getName() + "_v" + context.getService().getVersion() + "_ESC.wsdl",
                map
        );
        factory.generate(
                context.getDirectoryTarget(),
                "/Secondary/ESH",
                "wadl",
                context.getService().getName() + "_v" + context.getService().getVersion() + "_ESC.wadl",
                map
        );

        Document nxsd = factoryNXSD.getInstance(nodes, context);
        String nxsdFilename = context.getService().getName() + "_v" + context.getService().getVersion() + "_EBM.xsd";
        writer.write(nxsd, (ProjectPath.concat(context.getDirectoryTarget(), "/Secondary/ESH") + File.separator + nxsdFilename));

        exportFile.ExportResource("/dependencies/Contract/Primary/ESH/Dictionary_v1_EDD.xsd", ProjectPath.concat(dirTarget, "/Primary/ESH"), "Dictionary_v1_EDD.xsd");
        exportFile.ExportResource("/dependencies/Contract/Primary/ESH/Error_v1_ESO.xsd", ProjectPath.concat(dirTarget, "/Primary/ESH"), "Error_v1_ESO.xsd");
        exportFile.ExportResource("/dependencies/Contract/Primary/ESH/Exception_v1_ESO.xsd", ProjectPath.concat(dirTarget, "/Primary/ESH"), "Exception_v1_ESO.xsd");
        exportFile.ExportResource("/dependencies/Contract/Primary/ESH/MessageHeader_v1_ESO.xsd", ProjectPath.concat(dirTarget, "/Primary/ESH"), "MessageHeader_v1_ESO.xsd");
        exportFile.ExportResource("/dependencies/Contract/Primary/ESH/Result_v2_ESO.xsd", ProjectPath.concat(dirTarget, "/Primary/ESH"), "Result_v2_ESO.xsd");
        exportFile.ExportResource("/dependencies/Contract/Secondary/ESH/Exception_v1_ESOJ.xsd", ProjectPath.concat(dirTarget, "/Secondary/ESH"), "Exception_v1_ESOJ.xsd");
        exportFile.ExportResource("/dependencies/Contract/Secondary/ESH/MessageHeader_v1_ESOJ.xsd", ProjectPath.concat(dirTarget, "/Secondary/ESH"), "MessageHeader_v1_ESOJ.xsd");


    }
}
