package cl.entel.tde.dtm.util.project;

import cl.entel.tde.dtm.Loader.Loader;
import cl.entel.tde.dtm.util.ProjectPath;

import java.io.File;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class ProjectFolder {

    public void createFolders(){
        //proyect.directory
        Properties properties = Loader.getProperties();
        Set<Object> keySet = properties.keySet();
        for (Object key : keySet ){
            if (key.toString().startsWith("proyect.directory.")){
                String value = properties.getProperty(key.toString());
                File aux = new File( ProjectPath.concat(ProjectPath.DIRRECTORY_TARGET, value) );
                if (! aux.isDirectory()){
                    aux.mkdirs();
                }
            }

        }
    }
}
