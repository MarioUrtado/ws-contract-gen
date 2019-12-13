package cl.entel.tde.codegen.mustache;


import cl.entel.tde.codegen.context.Context;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class MustacheExecutor {

    public void build(InputStream stream, Context context, String output) throws Exception{
        MustacheFactory mf = MustacheFactoryFactory.getInstance();
        Reader reader = new InputStreamReader(stream, "utf-8");
        Mustache mustache = mf.compile(reader, "template");
        Writer writer = new OutputStreamWriter(new FileOutputStream(new File(output)));
        mustache.execute(writer, context).flush();
    }

    public String build(String stream, Context context) {
        try{
            MustacheFactory mustacheFactory = MustacheFactoryFactory.getInstance();
            StringWriter writer = new StringWriter();
            mustacheFactory.compile(new StringReader(stream), "cpu.template.email").execute(writer, context).flush();
            return writer.toString();
        } catch (Exception e ){
            e.printStackTrace();
            return "";
        }
    }
}
