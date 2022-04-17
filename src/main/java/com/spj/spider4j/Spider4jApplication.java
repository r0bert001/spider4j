package com.spj.spider4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Spider4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spider4jApplication.class, args);
    }

}
