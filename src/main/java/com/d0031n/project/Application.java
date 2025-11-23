package com.d0031n.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Den här bönan bean behövs för att göra HTTP-anrop till andra interna API-endpoints
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
