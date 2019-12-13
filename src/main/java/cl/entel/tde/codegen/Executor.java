package cl.entel.tde.codegen;

import cl.entel.tde.codegen.context.Context;
import cl.entel.tde.codegen.context.Service;
import cl.entel.tde.codegen.Parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Executor {

    @Autowired
    private Parser parser;

    @Value("${dtm.input}")
    private String input;

    @Value("${contract.output.directory}")
    private String dirTarget;

    public void run(String [] args){

        try {
            File excel = new File(input);
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
            Context context = new Context();
            context.setService(new Service(parser.getServiceName(input), parser.getServiceVersion(input), ""));
            context.setDirectoryTarget(dirTarget);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
