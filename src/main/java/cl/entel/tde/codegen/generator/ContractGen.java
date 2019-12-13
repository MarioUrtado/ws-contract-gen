package cl.entel.tde.codegen.generator;

import cl.entel.tde.codegen.context.Context;
import cl.entel.tde.codegen.context.Service;
import cl.entel.tde.codegen.contract.xsd.FactoryNXSD;
import cl.entel.tde.codegen.contract.xsd.FactoryXSD;
import cl.entel.tde.codegen.contract.xsd.XMLWriter;
import cl.entel.tde.codegen.domain.Configuration;
import cl.entel.tde.codegen.mustache.MustacheExecutor;
import cl.entel.tde.codegen.mustache.MustacheFacade;
import cl.entel.tde.codegen.Parser.Parser;
import cl.entel.tde.codegen.service.BucketService;
import cl.entel.tde.codegen.util.ExportFile;
import cl.entel.tde.codegen.util.NodeTree;
import cl.entel.tde.codegen.util.NodeTreeType;
import cl.entel.tde.codegen.util.ProjectPath;
import cl.entel.tde.codegen.xsd.cardinality.Cardinality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ContractGen {

    @Value("${dtm.input}")
    private String input;

    @Autowired
    private Parser parser;

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

    @Autowired
    private BucketService bucketService;

    @Autowired
    private MustacheExecutor mustacheExecutor;

    @Autowired
    private MustacheFacade mustacheFacade;

    @PostConstruct
    public void build() throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        InputStream configurationBucket = bucketService.getObject("eusb-service-builder-config-1.0");
        Configuration configuration = mapper.readValue(configurationBucket, Configuration.class);

        XMLWriter writer = new XMLWriter();
        context.setService(new Service(parser.getServiceName(input), parser.getServiceVersion(input), ""));
        context.setDirectoryTarget(dirTarget);
        context.setConfiguration(configuration);

        configuration.getNamespaces().forEach((x, y)-> {
            String value = this.mustacheExecutor.build(y.getValue(), context);
            y.setValue(value);
        });

        configuration.getResources().forEach((x, y)-> {
            String value = this.mustacheExecutor.build(y.getResource(), context);
            String path = this.mustacheExecutor.build(y.getPath(), context);
            y.setResource(value);
            y.setPath(path);
            y.getProperties().forEach((a,b)->{
                String prop = this.mustacheExecutor.build(b, context);
                y.getProperties().put(a, prop);
            });
        });

        //String filename = excelPath;
        Map<String, NodeTree> nodes = new HashMap<String, NodeTree>();

        nodes.put("REQ", parser.parse(input));
        nodes.put("RSP", parser.parseForResponse(input));
        nodes.put("FRSP", new NodeTree("Body", "complexType", new Cardinality(1, 1), NodeTreeType.COMPLEX));

        Map<String, Object> map = new HashMap<>();
        map.put("context", context);

        Document ebm = factoryXSD.getInstance(nodes, context);
        Document nxsd = factoryNXSD.getInstance(nodes, context);

        writer.write(ebm,dirTarget + File.separatorChar + mustacheExecutor.build(context.getConfiguration().getResources().get("ebm-primary").getPath(),context) ,mustacheExecutor.build(context.getConfiguration().getResources().get("ebm-primary").getResource(),context));
        writer.write(nxsd, dirTarget + File.separatorChar + mustacheExecutor.build(context.getConfiguration().getResources().get("ebm-secondary").getPath(), context) , mustacheExecutor.build(context.getConfiguration().getResources().get("ebm-secondary").getResource(), context));

        mustacheFacade.write(configuration.getResources().get("esc-primary").getBucket(), context,configuration.getResources().get("esc-primary").getPath(),  configuration.getResources().get("esc-primary"));
        mustacheFacade.write(configuration.getResources().get("esc-secondary").getBucket(), context,configuration.getResources().get("esc-secondary").getPath(),  configuration.getResources().get("esc-secondary"));

        exportFile.ExportResource("/dependencies/Contract/Primary/ESH/Dictionary_v1_EDD.xsd", ProjectPath.concat(dirTarget, configuration.getResources().get("ebm-primary").getPath()+ "/ESH"), "Dictionary_v1_EDD.xsd");
        exportFile.ExportResource("/dependencies/Contract/Primary/ESH/Error_v1_ESO.xsd", ProjectPath.concat(dirTarget, configuration.getResources().get("ebm-primary").getPath()+ "/ESH"), "Error_v1_ESO.xsd");
        exportFile.ExportResource("/dependencies/Contract/Primary/ESH/Exception_v1_ESO.xsd", ProjectPath.concat(dirTarget,  configuration.getResources().get("ebm-primary").getPath()+ "/ESH"), "Exception_v1_ESO.xsd");
        exportFile.ExportResource("/dependencies/Contract/Primary/ESH/MessageHeader_v1_ESO.xsd", ProjectPath.concat(dirTarget,  configuration.getResources().get("ebm-primary").getPath()+ "/ESH"), "MessageHeader_v1_ESO.xsd");
        exportFile.ExportResource("/dependencies/Contract/Primary/ESH/Result_v2_ESO.xsd", ProjectPath.concat(dirTarget,  configuration.getResources().get("ebm-primary").getPath()+ "/ESH"), "Result_v2_ESO.xsd");
        exportFile.ExportResource("/dependencies/Contract/Secondary/ESH/Exception_v1_ESOJ.xsd", ProjectPath.concat(dirTarget,  configuration.getResources().get("ebm-secondary").getPath()+ "/ESH"), "Exception_v1_ESOJ.xsd");
        exportFile.ExportResource("/dependencies/Contract/Secondary/ESH/MessageHeader_v1_ESOJ.xsd", ProjectPath.concat(dirTarget,  configuration.getResources().get("ebm-secondary").getPath()+ "/ESH"), "MessageHeader_v1_ESOJ.xsd");
    }

}
