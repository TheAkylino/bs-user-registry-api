package com.theakylino.business;

import com.theakylino.presentation.dto.request.RegisterUserRequest;
import com.theakylino.presentation.dto.response.UserResponse;

@FunctionalInterface
public interface UserService {
  UserResponse createUser(RegisterUserRequest request);
}
