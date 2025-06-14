package com.mavrommatis.ebookshop.ebookshop.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Issues JWT tokens for authenticated users.
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    // Token validity in milliseconds (e.g., 1 hour)
    private static final long VALIDITY_MS = 60 * 60 * 1000;

    /**
     * Creates a signed JWT containing username and roles claims.
     */
    public String createToken(Authentication authentication) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        List<String> roles = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        Date now = new Date();
        Date expiry = new Date(now.getTime() + VALIDITY_MS);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
