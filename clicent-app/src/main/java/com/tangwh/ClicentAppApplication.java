package com.tangwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClicentAppApplication {

    public static void main(String[] args) {

        SpringApplication.run(ClicentAppApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(){

        return new RestTemplate();
    }

}
