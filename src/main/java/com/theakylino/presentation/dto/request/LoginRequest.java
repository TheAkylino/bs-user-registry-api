package com.theakylino.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  @NotBlank(message = "Email es requerido")
  private String email;

  @NotBlank(message = "Contraseña es requerida")
  private String password;
}
