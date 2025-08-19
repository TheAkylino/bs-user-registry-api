package com.theakylino.infrastructure;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {

  @Bean
  public Key jwtKey(JwtProperties p) {
    return Keys.hmacShaKeyFor(p.getSecret().getBytes(StandardCharsets.UTF_8));
  }
}