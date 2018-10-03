package cl.entel.tde.dtm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.File;

@SpringBootApplication
public class App {

    public static void main(String [] args){
        ApplicationContext app = SpringApplication.run(App.class, args);
    }
}