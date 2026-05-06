package com.eventra.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "eventra_super_secret_key_1234567890";
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generateToken(String username, String role, String userId) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getRoleFromToken(String token) {
        try {
            Claims claims = validateToken(token);
            return (String) claims.get("role");
        } catch (Exception e) {
            return null;
        }
    }

    public static String getUsernameFromToken(String token) {
        try {
            return validateToken(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
