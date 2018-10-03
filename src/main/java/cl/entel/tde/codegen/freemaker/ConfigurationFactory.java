package cl.entel.tde.codegen.freemaker;


import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import java.util.Locale;

public class ConfigurationFactory {

    private static Configuration configuration = null;

    public static synchronized Configuration getInstance(){
        if (configuration == null){
            configuration = new Configuration();
            configuration.setIncompatibleImprovements(new Version(2, 3, 20));
            configuration.setDefaultEncoding("UTF-8");
            configuration.setLocale(Locale.US);
            configuration.setClassForTemplateLoading(ConfigurationFactory.class, "/");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        }

        return configuration;
    }

}
