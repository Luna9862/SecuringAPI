package org.carolina.securingapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    // Secret key for signing the JWT token (using HS512 algorithm)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Token validity (in milliseconds)
    private static final long TOKEN_VALIDITY = 1000 * 60 * 60 * 10; // 10 hours

    // Retrieve username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieve expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Retrieve any claim from JWT token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // For retrieving any information from token, the secret key is required
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Set the signing key
                .build() // Build the parser
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the claims (payload) from the token
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate token for the user
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    // Create the token
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Set claims (payload)
                .setSubject(subject) // Set the subject (usually the username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the current date as the issued date
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY)) // Set the expiration time
                .signWith(key, SignatureAlgorithm.HS512) // Sign the token with the secret key and algorithm
                .compact(); // Build and serialize the JWT token as a compact string
    }

    // Validate token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = getUsernameFromToken(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
