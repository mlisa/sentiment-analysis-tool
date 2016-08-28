package v1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import v1.controller.SemanticAnalyzer;

/**
 * Created by albertogiunta on 20/06/16.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        System.out.println(SemanticAnalyzer.getAdverbScore("Lo trovo semplicemente fantasticissimo"));
        System.out.println(SemanticAnalyzer.getSuperlativeScore("Lo trovo semplicemente fantasticissimo"));
        SpringApplication.run(Application.class, args);

    }

}
