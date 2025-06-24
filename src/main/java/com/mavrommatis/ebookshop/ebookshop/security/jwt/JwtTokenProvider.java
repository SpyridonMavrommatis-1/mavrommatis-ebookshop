package com.mavrommatis.ebookshop.ebookshop.security.jwt;

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
 * Utility class responsible for generating signed JWT tokens for authenticated users.
 *
 * <p>This provider encapsulates logic related to creating JWTs with embedded user identity and role information.
 * It is used typically after a successful authentication event to issue a token that can be included in future
 * API requests for stateless authorization.</p>
 *
 * <p>The generated JWT includes the following claims:</p>
 * <ul>
 *   <li>{@code sub} — the username of the authenticated principal</li>
 *   <li>{@code preferred_username} — a redundant user identifier (optional claim for compatibility)</li>
 *   <li>{@code scope} — space-delimited list of authorities (e.g., {@code ROLE_ADMIN ROLE_EMPLOYEE})</li>
 *   <li>{@code iat} — issue timestamp</li>
 *   <li>{@code exp} — expiration timestamp</li>
 * </ul>
 *
 * <p>The secret key used to sign the token is injected via the {@code jwt.secret} property in the
 * application configuration. Tokens are signed using the HS256 algorithm (HMAC-SHA256).</p>
 *
 * @see org.springframework.security.core.Authentication
 * @see io.jsonwebtoken.Jwts
 */
@Component
public class JwtTokenProvider {

    /** HMAC secret key used to sign the JWTs (injected from application properties). */
    @Value("${jwt.secret}")
    private String secret;

    /** Token validity duration in milliseconds (1 hour). */
    private static final long VALIDITY_MS = 60 * 60 * 1000;

    /**
     * Generates a signed JWT token for the provided authenticated user.
     *
     * @param authentication the authentication object representing the authenticated user
     * @return a signed JWT string containing user identity and roles
     */
    public String createToken(Authentication authentication) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        // Extract roles/authorities from the authenticated principal
        List<String> roles = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        Date now = new Date();
        Date expiry = new Date(now.getTime() + VALIDITY_MS);

        // Build and sign the JWT
        return Jwts.builder()
                .setSubject(authentication.getName())                      // "sub"
                .claim("preferred_username", authentication.getName())     // optional for UI use
                .claim("scope", String.join(" ", roles))                   // space-delimited authorities
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
