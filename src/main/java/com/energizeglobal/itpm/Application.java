package com.energizeglobal.itpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.HashMap;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        final HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

    }


}
