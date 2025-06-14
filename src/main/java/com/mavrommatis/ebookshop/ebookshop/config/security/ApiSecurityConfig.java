package com.mavrommatis.ebookshop.ebookshop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures the security filter chain for REST API endpoints under /api/**.
 */
@Configuration
public class ApiSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/book-reviews/**").permitAll()
                        // other API rules...
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable())      // disable HTTP Basic
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());   // use JWT bearer
        return http.build();
    }
}
