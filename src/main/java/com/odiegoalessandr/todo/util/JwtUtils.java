package com.odiegoalessandr.todo.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtils {

  private final SecretKey signingKey;

  public JwtUtils(
    @Value("${jwt.secret:12345678901234567890123456789012}") String secret
  ) {
    this.signingKey = Keys.hmacShaKeyFor(
      Decoders.BASE64.decode(secret)
    );
  }

  public String generateJwtToken(String username) {
    var oneHourInMillis = 60 * 60 * 1000; // 1 hour in milliseconds
    return Jwts.builder()
      .subject(username)
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + oneHourInMillis))
      .signWith(signingKey)
      .compact();
  }

  public String extractUsernameFromJwtToken(String token) {
    return Jwts.parser()
      .verifyWith(signingKey)
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getSubject();
  }

  public Boolean validateJwtToken(String token) {
    try {
      Jwts.parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}
