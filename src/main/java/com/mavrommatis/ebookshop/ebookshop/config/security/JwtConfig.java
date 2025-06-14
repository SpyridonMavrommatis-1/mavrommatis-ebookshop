package com.mavrommatis.ebookshop.ebookshop.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Defines a JwtDecoder bean that uses a shared HMAC secret to decode and verify JWT tokens.
 *
 * <p>The secret is injected from application.properties via @Value("${jwt.secret}").
 * Uses NimbusJwtDecoder under the hood.
 * </p>
 */
@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(
                secret.getBytes(),
                "HmacSHA256"
        );
        return NimbusJwtDecoder.withSecretKey(key).build();
    }
}


