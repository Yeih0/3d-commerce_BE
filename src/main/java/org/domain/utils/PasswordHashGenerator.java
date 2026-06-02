package org.domain.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordHashGenerator {

    @Bean
    public CommandLineRunner generateHash() {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode("admin123");
            System.out.println("====================================");
            System.out.println("HASH per 'admin123': " + hash);
            System.out.println("====================================");
        };
    }
}