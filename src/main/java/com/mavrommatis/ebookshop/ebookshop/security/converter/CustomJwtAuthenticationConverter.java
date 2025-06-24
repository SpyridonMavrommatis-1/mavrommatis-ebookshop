package com.mavrommatis.ebookshop.ebookshop.security.converter;

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
 * Custom converter that extracts {@link GrantedAuthority} instances from the "scope" claim of a JWT.
 *
 * <p>This class implements {@link Converter} and is used to bridge JWT tokens with Spring Security
 * authorities. It reads the "scope" claim, which can be a space-delimited string or a JSON array,
 * and converts each entry into a {@link SimpleGrantedAuthority}.</p>
 *
 * <p>The extracted authorities are used by Spring Security to determine access rights.</p>
 *
 * <p>Example JWT claim structure:</p>
 * <pre>
 * {
 *   "sub": "admin",
 *   "scope": "ROLE_ADMIN ROLE_EMPLOYEE"
 * }
 * </pre>
 *
 * @see JwtAuthenticationConverter#setJwtGrantedAuthoritiesConverter(Converter)
 * @see org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
 */
public class CustomJwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    /**
     * Converts the "scope" claim into a collection of {@link GrantedAuthority} instances.
     *
     * <p>Supports both string and list representations for the scope claim.</p>
     *
     * @param jwt the JWT token containing claims
     * @return a list of authorities derived from the token's scope
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Object scope = jwt.getClaims().get("scope");

        if (scope instanceof String s) {
            return Stream.of(s.split(" "))
                    .map(String::trim)
                    .filter(auth -> !auth.isBlank())
                    .map(SimpleGrantedAuthority::new)
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

        return List.of(); // fallback if scope is null or malformed
    }

    /**
     * Returns a {@link JwtAuthenticationConverter} configured to use this converter.
     *
     * <p>This is useful when registering the converter in a security filter chain.</p>
     *
     * @return a {@link JwtAuthenticationConverter} using this scope-based authority extractor
     */
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this);
        return converter;
    }
}
