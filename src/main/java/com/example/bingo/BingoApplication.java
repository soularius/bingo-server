package com.example.bingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.bingo.configuration.MvcConfiguration.*;


@SpringBootApplication
public class BingoApplication {

    public static void main(String[] args) {
            SpringApplication.run(BingoApplication.class, args);
    }
}
