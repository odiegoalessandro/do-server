package com.odiegoalessandr.todo.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

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

  public Optional<String> extractUsernameFromValidJwtToken(String token) {
    try {
      var username = Jwts.parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();

      return Optional.ofNullable(username);
    } catch (JwtException e) {
      return Optional.empty();
    }
  }
}
