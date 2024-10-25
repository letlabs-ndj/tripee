package com.lameute.chat_service.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JWTUtils {

  @Value("${secret.signing_key}")
  private String jwtSecret;

  private Key getSignInKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  // Extract userEmail (subject) from token
  public String extractUserEmail(String token){
    return extractClaim(token, Claims::getSubject);
  }

  // Extract expiration date from token
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Extract a particular claim from token
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // Extract all claims from token
  private Claims extractAllClaims(String token){
    return Jwts
            .parser()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getPayload();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(getSignInKey()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public String parseJwt(StompHeaderAccessor accessor) {
    String token = accessor.getFirstNativeHeader("Authorization");
    String jwt = null;
    if (token != null) {
      jwt = token.substring(7);
    }
    System.out.println(jwt);
    return jwt;
  }
}
