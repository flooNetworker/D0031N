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

    // This bean is required to make HTTP calls to other internal API endpoints
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
