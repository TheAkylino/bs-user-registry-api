package com.theakylino.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

  private final Key key;
  private final JwtProperties props;
  private final Clock clock = Clock.systemUTC();

  public String generateToken(String username) {
    Instant now = Instant.now(clock);
    Instant exp = now.plus(Duration.ofSeconds(props.getExpirationSeconds()));
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(exp))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      parse(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public Claims parse(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .setAllowedClockSkewSeconds(30)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String extractUsername(String token) {
    return getClaim(token, Claims::getSubject);
  }

  public <T> T getClaim(String token, Function<Claims, T> resolver) {
    return resolver.apply(parse(token));
  }

  public long getSecondsUntilExpiration(String token) {
    Date exp = getClaim(token, Claims::getExpiration);
    long seconds = (exp.getTime() - Instant.now(clock).toEpochMilli()) / 1000;
    return Math.max(seconds, 0);
  }

  public boolean isTokenExpired(String token) {
    try {
      return getSecondsUntilExpiration(token) == 0;
    } catch (JwtException e) {
      return true;
    }
  }

  public long getExpirationFromToken(String token) {
    Date exp = getClaim(token, Claims::getExpiration);
    long seconds = (exp.getTime() - System.currentTimeMillis()) / 1000;
    return Math.max(seconds, 0);
  }

}
