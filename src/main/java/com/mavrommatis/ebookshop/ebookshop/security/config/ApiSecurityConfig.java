package com.mavrommatis.ebookshop.ebookshop.security.config;

import com.mavrommatis.ebookshop.ebookshop.security.converter.CustomJwtAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration specific to API endpoints under "/api/**".
 *
 * <p>Applies stateless authentication and JWT-based authorization for REST resources.
 * Disables CSRF and HTTP Basic authentication, enforces JWT parsing via a custom converter,
 * and defines public and protected endpoint access rules.</p>
 */
@Configuration
public class ApiSecurityConfig extends BaseSecurityConfig {

    /**
     * Defines the security filter chain for API routes.
     *
     * <p>Key configurations:</p>
     * <ul>
     *   <li>CSRF disabled (stateless session)</li>
     *   <li>Session policy: STATELESS</li>
     *   <li>Permits: POST /api/authenticate, GET /api/book-reviews/**</li>
     *   <li>All other endpoints require authentication</li>
     *   <li>JWT-based authentication using a custom converter</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} object to configure
     * @return a configured {@link SecurityFilterChain} bean
     * @throws Exception if an error occurs during setup
     */
    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/authenticate").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/book-reviews/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter()))
                );

        return http.build();
    }

    /**
     * Defines the {@link JwtAuthenticationConverter} bean.
     *
     * <p>This converter extracts authorities from the "scope" claim of the JWT,
     * mapping them to Spring Security roles (e.g., ROLE_ADMIN, ROLE_CUSTOMER).</p>
     *
     * @return a configured {@link JwtAuthenticationConverter} instance
     */
    @Bean
    public JwtAuthenticationConverter customJwtAuthenticationConverter() {
        return new CustomJwtAuthenticationConverter().jwtAuthenticationConverter();
    }

    /**
     * Configures the {@link AuthenticationManager} bean used for authenticating users.
     *
     * <p>Sets up in-memory authentication using the base class's {@code userDetailsService}
     * and {@code passwordEncoder} beans.</p>
     *
     * @param http the {@link HttpSecurity} used to extract shared AuthenticationManagerBuilder
     * @return the configured {@link AuthenticationManager}
     * @throws Exception if configuration fails
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
                .userDetailsService(userDetailsService(passwordEncoder()))
                .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }
}
