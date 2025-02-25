package com.itheima.reggie.common;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for handling JWT operations such as token generation,
 * validation, and extraction of claims.
 */
@Component
public class JwtUtil {

    //  Secret key for signing the JWT token (Must be at least 256 bits for HS256)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Securely generated key

    //  Token expiration time (e.g., 24 hours)
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    /**
     * Generates a JWT token for a given user ID.
     *
     * @param userId The user's ID
     * @return A signed JWT token
     */
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId); // Subject (User ID)
        claims.put("issuedAt", new Date());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date()) // Set issue time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration
                .signWith(SECRET_KEY) // Use secure signing key
                .compact(); // Generate JWT token
    }

    /**
     * Parses and validates a JWT token.
     *
     * @param token The JWT token
     * @return The parsed claims if valid
     * @throws ExpiredJwtException if the token is expired
     * @throws MalformedJwtException if the token is malformed
     * @throws SignatureException if the signature is invalid
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Set signing key
                .build()
                .parseClaimsJws(token) // Parse token
                .getBody(); // Return claims
    }

    /**
     * Extracts the user ID from the JWT token.
     *
     * @param token The JWT token
     * @return The extracted user ID
     */
    public String getUserIdFromToken(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * Checks if a JWT token is valid.
     *
     * @param token The JWT token
     * @return true if valid, false otherwise
     */
    public boolean isValidToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Retrieves the expiration time of a JWT token.
     *
     * @param token The JWT token
     * @return Expiration time in milliseconds
     */
    public long getExpiration(String token) {
        Date expiration = parseToken(token).getExpiration();
        return expiration.getTime() - System.currentTimeMillis();
    }

}
