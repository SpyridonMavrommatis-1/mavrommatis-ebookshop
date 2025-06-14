package com.mavrommatis.ebookshop.ebookshop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures the security filter chain for MVC (Thymeleaf) endpoints.
 *
 * <p>This configuration:
 * <ul>
 *   <li>Allows anonymous access to public pages: /, /login, /common/**.</li>
 *   <li>Restricts /admin/** to users with role ADMIN.</li>
 *   <li>Restricts /user/** to users with roles CUSTOMER, EMPLOYEE, or ADMIN.</li>
 *   <li>Requires authentication for all other requests.</li>
 *   <li>Configures form-based login at /login with a default success URL.</li>
 *   <li>Configures logout behavior to redirect to /login?logout.</li>
 * </ul>
 * </p>
 *
 * <p>This class extends BaseSecurityConfig to reuse the PasswordEncoder and in-memory users.</p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Order(2)
public class WebSecurityConfig extends BaseSecurityConfig {

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/common/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/common/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}