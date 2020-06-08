package com.tan.springcloud2producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication
@EnableJpaRepositories
//@EnableResourceServer
public class Springcloud2ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springcloud2ProducerApplication.class, args);
    }
}