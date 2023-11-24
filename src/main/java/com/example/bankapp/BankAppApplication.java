package com.example.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAppApplication.class, args);
    }

    //TODO: логгирование, пример в лекции от 10.10 (вторая часть урока)
    //TODO: refactor code - open all classes and look at sonar quality
    //TODO: frontend: http://localhost:8080 - "bank manager working space"
    //TODO: write documentation
}
