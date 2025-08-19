package com.theakylino.presentation.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class LoginResponse {
  private String token;
  private LocalDateTime expiration;
  private String email;
}
