package ua.oh.jwttokensecurity.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.oh.jwttokensecurity.security.JwtUserDetailService;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  public static final String BEARER_PREFIX = "Bearer ";
  @Value("${jwt.issuer}")
  private String issuer;
  @Value("${jwt.expiration}")
  private Long expiration;
  @Value("${jwt.secret}")
  private String secret;

  private final JwtUserDetailService jwtUserDetailService;

  @PostConstruct
  private void init() {
    secret = Base64.getEncoder().encodeToString(secret.getBytes());
  }

  public String createToken(String username) {

    JwtUser user = (JwtUser) jwtUserDetailService.loadUserByUsername(username);
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + expiration * 1000);

    Claims claims = Jwts.claims().setSubject(user.getUsername());
    claims.put("roles", user.getAuthorities().toArray());

    return Jwts.builder()
        .setClaims(claims)
        .setIssuer(issuer)
        .setIssuedAt(now)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();

  }

  public Authentication getAuthentication(String token) {

    UserDetails userDetails = jwtUserDetailService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

  }

  private String getUsername(String token) {

    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();

  }

  public String resolveToken(HttpServletRequest servletRequest) {

    String bearerToken = servletRequest.getHeader("Authentication");

    if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(BEARER_PREFIX.length());
    }

    return null;
  }

  public boolean validateToken(String token) {

    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    return claims.getExpiration().before(new Date());

  }
}
