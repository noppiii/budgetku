package com.budgetku.backend.security;

import com.budgetku.backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class JwtService {

    @Value("${app.security.token.secret}")
    private String SECRET_KEY;

    @Value("${app.security.jwt.expiration}")
    private long JWT_EXPIRATION;

    @Value("${app.security.jwt.reset.password.expiration}")
    private long JWT_RESET_PASSWORD_EXPIRATION;

    @Value("${app.security.jwt.refresh-token.expiration}")
    private long JWT_REFRESH_TOKEN_EXPIRATION;

    public String generateResetPasswordToken(User user) {
        return createToken(user, JWT_RESET_PASSWORD_EXPIRATION);
    }

    public String generateToken(User user) {
        return createToken(user, JWT_EXPIRATION);
    }

    public String generateRefreshToken(User user) {
        return createToken(user, JWT_REFRESH_TOKEN_EXPIRATION);
    }

    public boolean isTokenValid(String authHeader, String expectedNif) {
        String token = authHeader.replace("Bearer ", "");
        String nif = extractNif(token);
        return (nif.equals(expectedNif) && !isTokenExpired(token));
    }

    public String extractNif(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    private String createToken(User user, long expirationTime) {
        return Jwts.builder().setSubject(user.getNif()).claim("roles", user.getRoles().stream().map(Enum::name).collect(Collectors.toList())).claim("email", user.getEmail()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(HS256, SECRET_KEY).compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

        return claims.getExpiration();
    }
}
