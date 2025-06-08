package com.mavrommatis.ebookshop.ebookshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated() // Secure all /api routes
                        .anyRequest().permitAll()                  // Allow everything else
                )
                .httpBasic(Customizer.withDefaults())          // Enable Basic Auth
                .csrf(csrf -> csrf.disable());                 // Disable CSRF for testing Postman

        return http.build();
    }
}
