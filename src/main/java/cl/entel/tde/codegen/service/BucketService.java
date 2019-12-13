package cl.entel.tde.codegen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

@Service
public class BucketService {

    @Value("${parameter-bucket-config}")
    private String urlObjectBucket;

    public InputStream getObject(String key) throws Exception {
        return new BufferedInputStream(new URL(this.urlObjectBucket + "/" + key).openStream());
    }
}