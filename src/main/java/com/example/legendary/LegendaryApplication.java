package com.example.legendary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author legendary
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class LegendaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegendaryApplication.class, args);
    }
}
