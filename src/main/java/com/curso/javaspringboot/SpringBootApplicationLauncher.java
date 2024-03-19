package com.curso.javaspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationLauncher.class, args);
        System.out.println("aplicacion funcionando");
    }
}