package org.example.starrepo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.example.starrepo.requests")
// Main class
public class StarRepo {
    public static void main(String[] args) {
        SpringApplication.run(StarRepo.class, args);
    }
}
