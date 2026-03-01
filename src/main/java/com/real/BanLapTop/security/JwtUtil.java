package com.real.BanLapTop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey key;   // 🔥 ĐỔI THÀNH SecretKey

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 🔹 Tạo token
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    // 🔹 Lấy username
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 🔹 Lấy role
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // 🔹 Validate
    public boolean isValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 🔹 Parse
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)   // ✅ giờ đúng type
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}