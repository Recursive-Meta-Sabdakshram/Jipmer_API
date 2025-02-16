package com.example.jipmerapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.jipmer.*"})
@EnableJpaRepositories(basePackages = "com.jipmer.repository")
@EntityScan(basePackages = "com.jipmer.entity")
public class JipmerApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JipmerApiServiceApplication.class, args);
    }

}
