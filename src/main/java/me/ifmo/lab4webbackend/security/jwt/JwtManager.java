package me.ifmo.lab4webbackend.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtManager {
    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private int expirationMs;

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
    }
    
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    public String getUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch(SignatureException exception){
            log.error("Invalid JWT signature: {}", exception.getMessage());
        }catch(MalformedJwtException exception){
            log.error("Invalid JWT token: {}", exception.getMessage());
        }catch(ExpiredJwtException exception){
            log.error("JWT token is expired: {}", exception.getMessage());
        }catch(UnsupportedJwtException exception){
            log.error("JWT token is unsupported: {}", exception.getMessage());
        }catch(IllegalArgumentException exception){
            log.error("JWT claims string is empty: {}", exception.getMessage());
        }
        return false;
    }
}
