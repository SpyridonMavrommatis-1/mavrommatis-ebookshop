package com.mavrommatis.ebookshop.ebookshop.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * JWT decoding configuration class.
 *
 * <p>This class defines the {@link JwtDecoder} bean that is responsible for validating
 * incoming JWT tokens in the application. The decoder uses HMAC with SHA-256 algorithm,
 * and a shared secret key defined in the application's configuration file.</p>
 *
 * <p>The JWT tokens are validated by checking their signature and claims,
 * including expiration, issuer, subject, and any custom fields like {@code scope}.</p>
 *
 * <p>This configuration is typically used by Spring Security's OAuth2 resource server
 * to validate JWTs for incoming API requests.</p>
 *
 * Example application.properties configuration:
 * <pre>
 * jwt.secret=your-super-secret-signing-key
 * </pre>
 *
 * @see org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
 * @see org.springframework.security.oauth2.jwt.Jwt
 * @see org.springframework.security.oauth2.jwt.NimbusJwtDecoder
 */
@Configuration
public class JwtConfig {

    /**
     * The HMAC secret key for signing and verifying JWT tokens.
     * <p>Injected from application properties using the key {@code jwt.secret}.</p>
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Defines the {@link JwtDecoder} bean.
     *
     * <p>Uses the configured HMAC secret to construct a {@link NimbusJwtDecoder}
     * that validates the signature of incoming JWT tokens.</p>
     *
     * @return a configured {@link JwtDecoder} instance
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(key).build();
    }
}
