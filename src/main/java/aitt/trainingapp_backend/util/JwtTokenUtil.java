package aitt.trainingapp_backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSigningKey() {
        log.debug("Generating signing key");
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(UserDetails userDetails) {
        log.info("Generating token for user: {}", userDetails.getUsername());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSigningKey())
                .compact();
    }
    /*
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()//parser() //parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)//parseClaimsJws(token)
                .getBody();
    }

     */
    public Claims extractClaims(String token) {
        log.debug("Extracting claims from token");
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUsername(String token) {
        log.debug("Extracting username from token");
        return extractClaims(token).getSubject();
    }
    public boolean isTokenExpired(String token) {
        log.debug("Checking if token is expired");
        return extractClaims(token).getExpiration().before(new Date());
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        log.info("Validating token for user: {}", userDetails.getUsername());
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}