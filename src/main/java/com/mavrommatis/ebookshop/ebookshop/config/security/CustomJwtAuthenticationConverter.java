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
 */
public class CustomJwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Object scope = jwt.getClaims().get("scope");

        if (scope instanceof String s) {
            return Stream.of(s.split(" "))
                    .map(String::trim)
                    .filter(auth -> !auth.isBlank())
                    .map(SimpleGrantedAuthority::new) // already has "ROLE_" prefix
                    .collect(Collectors.toList());
        }

        if (scope instanceof List<?> list) {
            return list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .map(String::trim)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return List.of(); // no valid authorities
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this);
        return converter;
    }
}
