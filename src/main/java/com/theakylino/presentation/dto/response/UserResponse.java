package com.theakylino.presentation.dto.response;

import java.time.LocalDateTime;
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
public class UserResponse {
  private Long id;
  private String name;
  private String email;
  private List<PhoneResponse> phones;
  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime lastLogin;
  private String token;
  private boolean active;
}
