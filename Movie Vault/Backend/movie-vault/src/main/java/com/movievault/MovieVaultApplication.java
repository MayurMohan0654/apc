package com.movievault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MovieVaultApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieVaultApplication.class, args);
    }
}
