package com.magic233;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.magic233.mapper")
public class MultithreadingHecoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultithreadingHecoApplication.class, args);
    }

}
