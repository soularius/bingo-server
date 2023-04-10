package com.example.bingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.bingo.configuration.MvcConfiguration.*;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan(basePackages = "com.example.bingo.configuration")
public class BingoApplication {

    public static void main(String[] args) {
            SpringApplication.run(BingoApplication.class, args);
    }
}
