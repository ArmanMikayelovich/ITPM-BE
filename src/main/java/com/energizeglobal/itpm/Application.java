package com.energizeglobal.itpm;

import com.energizeglobal.itpm.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication()
@EnableConfigurationProperties(value = ApplicationConfig.class)

public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
