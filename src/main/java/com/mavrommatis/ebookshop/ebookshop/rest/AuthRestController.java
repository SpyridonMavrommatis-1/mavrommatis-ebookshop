package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.config.security.JwtTokenProvider;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for issuing JWT tokens to authenticated users.
 */
@RestController
@RequestMapping("/api")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthRestController(AuthenticationManager authenticationManager,
                              JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Authenticates user credentials and returns a JWT on success.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(
            @RequestBody AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String jwt = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(Map.of("token", jwt));
    }

    /**
     * DTO for capture username and password in authentication requests.
     */
    @Data
    static class AuthRequest {
        private String username;
        private String password;
    }
}