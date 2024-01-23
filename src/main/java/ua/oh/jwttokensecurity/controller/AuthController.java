package ua.oh.jwttokensecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.oh.jwttokensecurity.dto.AuthRequestDto;
import ua.oh.jwttokensecurity.dto.AuthResponseDto;
import ua.oh.jwttokensecurity.model.User;
import ua.oh.jwttokensecurity.security.jwt.JwtAuthenticationException;
import ua.oh.jwttokensecurity.security.jwt.JwtTokenProvider;
import ua.oh.jwttokensecurity.security.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  @GetMapping("/hello")
  public ResponseEntity<String> echo(Authentication authentication) {

    return ResponseEntity.ok(
        "Hello, " + authentication.getPrincipal() + authentication.getAuthorities());

  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto requestDto) {

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(requestDto.getUsername(),
            requestDto.getPassword()));

    User user = userService.findByUsername(requestDto.getUsername())
        .orElseThrow(() -> new JwtAuthenticationException("User not found"));

    String token = jwtTokenProvider.createToken(user.getUsername());

    return ResponseEntity.ok(AuthResponseDto.builder()
        .username(user.getUsername())
        .token(token)
        .build());

  }

}
