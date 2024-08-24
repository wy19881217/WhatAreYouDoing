package com.wzh.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.wzh"})
@MapperScan("com.wzh.mybatis")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
