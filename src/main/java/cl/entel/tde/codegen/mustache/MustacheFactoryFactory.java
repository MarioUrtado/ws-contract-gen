package cl.entel.tde.codegen.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;

public class MustacheFactoryFactory {

    private static MustacheFactory mustacheFactory = null;

    public synchronized static MustacheFactory getInstance(){
        if(mustacheFactory==null){
            mustacheFactory = new DefaultMustacheFactory();
        }
        return mustacheFactory;
    }

}
