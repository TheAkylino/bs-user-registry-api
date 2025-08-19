package com.theakylino.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class PhoneRequest {
  @NotBlank(message = "Número es requerido")
  private String number;

  @NotBlank(message = "Código de ciudad es requerido")
  private String citycode;

  @NotBlank(message = "Código de país es requerido")
  private String contrycode;
}
