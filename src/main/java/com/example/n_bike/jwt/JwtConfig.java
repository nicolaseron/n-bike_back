package com.example.n_bike.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String getSecret() {
    return secret;
  }

  public Long getExpiration() {
    return expiration;
  }
}
