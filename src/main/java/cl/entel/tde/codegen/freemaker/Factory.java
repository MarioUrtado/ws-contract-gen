package cl.entel.tde.codegen.freemaker;

import cl.entel.tde.codegen.context.Context;
import cl.entel.tde.codegen.context.Service;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Factory {

    /**
     *
     * Create a new file from the template. The destination file is
     *
     * targetFullPath + relativePath + targetFile
     *
     * @param targetFullPath full path where the file is gonna be created
     * @param relativePath Relative path into the resources/dependencies where template is located
     * @param templateName name of file template for freemaker, withou extension .ftl
     * @param targetFile name of file to create
     * @param map Map with values
     */
    public void generate(String targetFullPath, String relativePath, String templateName, String targetFile, Map<String, Object> map ){
        this.generate(targetFullPath, relativePath, relativePath, templateName, targetFile, map);
    }


    /**
     *
     * Create a new file from the template. The destination file is
     *
     * targetFullPath + relativePath + targetFile
     *
     * @param targetFullPath full path where the file is gonna be created.
     * @param targetRelativePath relative path relative tu full path, where the file is gonna be created
     * @param relativePath Relative path into the resources/dependencies where template is located
     * @param templateName name of file template for freemaker, withou extension .ftl
     * @param targetFile name of file to create
     * @param map Map with values
     */
    public void generate(String targetFullPath, String targetRelativePath, String relativePath, String templateName, String targetFile, Map<String, Object> map ){
        try{
            Configuration configuration = ConfigurationFactory.getInstance();
            Template template = configuration.getTemplate("/dependencies"+ relativePath + "/"+ templateName+ ".ftl");
            Writer fileWriter = new FileWriter(new File(targetFullPath, relativePath+ "/"+targetFile));
            template.process(map, fileWriter);
            fileWriter.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
