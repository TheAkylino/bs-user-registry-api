package com.theakylino.presentation.controller;

import com.theakylino.business.UserService;
import com.theakylino.presentation.dto.request.RegisterUserRequest;
import com.theakylino.presentation.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;


  @PostMapping("/userRegistration")
  public ResponseEntity<UserResponse> userCreation(
      @Valid @RequestBody RegisterUserRequest request) {
    return Optional.ofNullable(request)
        .map(service::createUser)
        .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user))
        .orElseThrow(() -> new RuntimeException("Ha ocurrido un error creando la User"));
  }
}
