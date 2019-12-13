package cl.entel.tde.codegen.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ProjectPath {

    public static String concat(String path1, String path2){
        Path path = Paths.get(path1, path2);
        return path.toString();
    }

}
