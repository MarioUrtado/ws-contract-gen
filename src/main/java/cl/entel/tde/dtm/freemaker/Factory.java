package cl.entel.tde.dtm.freemaker;

import cl.entel.tde.dtm.util.Service;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Factory {

    public void generate(String fullPath, String relativePath, String templateName, String targetFile){
        try{
            cl.entel.tde.dtm.freemaker.model.Service service = new cl.entel.tde.dtm.freemaker.model.Service(Service.getName(), Service.getVersion());
            Configuration configuration = ConfigurationFactory.getInstance();
            Template template = configuration.getTemplate("/dependencies"+ relativePath + "/"+ templateName+ ".ftl");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("service", service);
            Writer fileWriter = new FileWriter(new File(fullPath, relativePath+ "/"+targetFile));
            template.process(map, fileWriter);
            fileWriter.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
