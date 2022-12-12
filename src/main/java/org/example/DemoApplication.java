package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ru.CryptoPro.JCP.JCP;
import java.security.Security;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("Loading providers...");
        Security.addProvider(new JCP());
        // Security.addProvider(new RevCheck());
        // Security.addProvider(new CryptoProvider());
        // Security.addProvider(new Provider());
        System.out.println("Providers loaded.");
    }
}
