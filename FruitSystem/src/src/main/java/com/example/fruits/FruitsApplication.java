package com.example.fruits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.fruits.mapper")
public class FruitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FruitsApplication.class, args);
    }

}
