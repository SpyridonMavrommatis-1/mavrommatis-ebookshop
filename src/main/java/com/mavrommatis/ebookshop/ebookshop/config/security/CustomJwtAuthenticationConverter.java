package com.mavrommatis.ebookshop.ebookshop.config.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Custom JWT Authentication Converter that extracts authorities from the "scope" claim of a JWT token.
 * <p>
 * This class is responsible for translating JWT scopes (e.g., "ROLE_ADMIN ROLE_EMPLOYEE")
 * into {@link GrantedAuthority} objects that Spring Security uses for role-based authorization.
 * </p>
 *
 * Example:
 * <pre>
 * scope: "ROLE_ADMIN ROLE_CUSTOMER"
 * → [new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_CUSTOMER")]
 * </pre>
 */
public class CustomJwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    /**
     * Converts the "scope" claim from a JWT into a collection of {@link GrantedAuthority} instances.
     *
     * @param jwt the decoded JWT
     * @return a collection of authorities derived from the "scope" claim, or an empty list if missing
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Object scope = jwt.getClaims().get("scope");

        if (scope instanceof String s) {
            return Stream.of(s.split(" "))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return List.of(); // No authorities if no scope claim
    }

    /**
     * Wraps this converter inside a {@link JwtAuthenticationConverter} and sets it as the authority extractor.
     *
     * @return a configured {@link JwtAuthenticationConverter} using this custom scope parser
     */
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this);
        return converter;
    }
}
