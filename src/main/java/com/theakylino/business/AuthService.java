package com.theakylino.business;

import com.theakylino.presentation.dto.request.LoginRequest;
import com.theakylino.presentation.dto.response.LoginResponse;

public interface AuthService {
  LoginResponse authenticate(LoginRequest loginRequest);

  boolean validateToken(String token);
}
