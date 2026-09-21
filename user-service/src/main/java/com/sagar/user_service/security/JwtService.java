package com.sagar.user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expiration;
    private final long refreshExpiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.refresh-expiration}") long refreshExpiration) {

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.expiration = expiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateToken(String username, String role) {
        return buildToken(username, role, expiration, "access");
    }

    public String generateRefreshToken(String username, String role) {
        return buildToken(username, role, refreshExpiration, "refresh");
    }

    private String buildToken(String username, String role, long ttl, String type) {

        Date now = new Date();

        Date expiry = new Date(now.getTime() + ttl);

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", type)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public long getExpiration() {
        return expiration;
    }

    public String extractUsername(String token) {

        return getClaims(token)
                .getSubject();
    }

    public String extractTokenType(String token) {
        return getClaims(token).get("type", String.class);
    }

    public boolean isTokenValid(String token) {

        try {

            Claims claims = getClaims(token);

            return claims.getExpiration()
                    .after(new Date());

        } catch (Exception e) {

            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            return "refresh".equals(extractTokenType(token));
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}