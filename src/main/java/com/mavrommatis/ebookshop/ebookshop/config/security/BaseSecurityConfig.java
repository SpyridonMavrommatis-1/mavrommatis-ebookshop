package com.mavrommatis.ebookshop.ebookshop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Application security configuration.
 * <p>
 * Defines HTTP endpoint protection, in-memory users with roles, BCrypt password encoding,
 * and Basic authentication for the REST API.
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // enables @PreAuthorize etc.
public abstract class BaseSecurityConfig {

    /**
     * Defines the application’s PasswordEncoder bean.
     * <p>
     * Uses the BCrypt strong hashing function to encode and verify passwords.
     * All raw passwords passed to {@link UserDetailsService} are encoded
     * through this bean before storage or comparison.
     * </p>
     *
     * @return a new BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates an in-memory user store pre-populated with three demo users:
     * <ul>
     *   <li><strong>customer</strong> / <em>customer</em> (ROLE_CUSTOMER)</li>
     *   <li><strong>employee</strong> / <em>employee</em> (ROLE_EMPLOYEE)</li>
     *   <li><strong>admin</strong>    / <em>admin</em>    (ROLE_ADMIN)</li>
     * </ul>
     * <p>
     * Passwords are encoded using the configured {@link PasswordEncoder} (BCrypt).
     * In production you would replace this with a database-backed {@link UserDetailsService}.
     * </p>
     *
     * @param encoder the PasswordEncoder bean used to hash the raw passwords
     * @return an InMemoryUserDetailsManager containing the demo users
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails customer = User.builder()
                .username("customer")
                .password(encoder.encode("customer"))
                .roles("CUSTOMER")
                .build();

        UserDetails employee = User.builder()
                .username("employee")
                .password(encoder.encode("employee"))
                .roles("EMPLOYEE")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(customer, employee, admin);
    }
}