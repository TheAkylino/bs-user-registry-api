package com.theakylino.presentation.controller;

import com.theakylino.business.AuthService;
import com.theakylino.presentation.dto.request.LoginRequest;
import com.theakylino.presentation.dto.response.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {
  public static final int STATUS_401 = 401;
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    return Optional.ofNullable(loginRequest)
        .map(authService::authenticate)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new RuntimeException("Ha ocurrido un Obteniendo el login"));
  }

  @GetMapping("/validate")
  public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
    return Optional.ofNullable(authHeader)
        .filter(header -> header.startsWith("Bearer "))
        .map(header -> header.substring(7))
        .filter(authService::validateToken)
        .map(token -> ResponseEntity.ok("Token válido"))
        .orElseGet(() -> ResponseEntity.status(STATUS_401).body("Token inválido"));
  }
}
