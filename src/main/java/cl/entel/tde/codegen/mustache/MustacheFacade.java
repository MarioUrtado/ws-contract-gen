package cl.entel.tde.codegen.mustache;


import cl.entel.tde.codegen.context.Context;
import cl.entel.tde.codegen.domain.ConfigurationResource;
import cl.entel.tde.codegen.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
public class MustacheFacade {

    @Autowired
    private BucketService bucketService;

    @Autowired
    private MustacheExecutor mustacheExecutor;

    public void write(String key, Context context, String path, ConfigurationResource resource){
        try{
            InputStream in = bucketService.getObject(key);
            String fileName = this.mustacheExecutor.build(resource.getResource(), context);
            String pathName = this.mustacheExecutor.build( path + File.separatorChar + resource.getPath(), context);
            new File(pathName).mkdirs();
            this.mustacheExecutor.build(in, context, pathName + File.separatorChar + fileName);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}