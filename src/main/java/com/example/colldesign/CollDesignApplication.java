package com.example.colldesign;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.colldesign.**.dao")
public class CollDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollDesignApplication.class, args);
    }

}
