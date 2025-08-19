package com.theakylino.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserRequest {
  @NotBlank(message = "Nombre es requerido")
  private String name;

  @NotBlank(message = "Email es requerido")
  @Email(message = "Formato de email inválido")
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
      message = "Formato de email debe ser aaaaaaa@dominio.cl")
  private String email;

  @NotBlank(message = "Contraseña es requerida")
  private String password;

  @NotNull(message = "Teléfonos son requeridos")
  @Valid
  private List<PhoneRequest> phones;
}
