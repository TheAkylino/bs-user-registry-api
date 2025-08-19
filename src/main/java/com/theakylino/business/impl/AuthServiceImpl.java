package com.theakylino.business.impl;

import com.theakylino.business.AuthService;
import com.theakylino.data.entity.UserEntity;
import com.theakylino.data.repository.UserRepository;
import com.theakylino.infrastructure.JwtTokenUtil;
import com.theakylino.presentation.dto.request.LoginRequest;
import com.theakylino.presentation.dto.response.LoginResponse;
import com.theakylino.presentation.exception.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final JwtTokenUtil jwtTokenUtil;
  private final PasswordEncoder passwordEncoder;

  public AuthServiceImpl(UserRepository userRepository,
                         JwtTokenUtil jwtTokenUtil,
                         PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtTokenUtil = jwtTokenUtil;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public LoginResponse authenticate(LoginRequest request) {
    return Optional.ofNullable(request)
        .map(LoginRequest::getEmail)
        .flatMap(userRepository::findByEmail)
        .filter(user -> isValidCredentials(user, request.getPassword()))
        .filter(UserEntity::isActive)
        .map(this::updateLoginMetadata)
        .map(userRepository::save)
        .map(this::buildLoginResponse)
        .orElseThrow(() -> new InvalidCredentialsException("Credenciales inválidas o usuario inactivo"));
  }

  private boolean isValidCredentials(UserEntity user, String rawPassword) {
    if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
      throw new InvalidCredentialsException("Credenciales inválidas");
    }
    return true;
  }

  private UserEntity updateLoginMetadata(UserEntity user) {
    String newToken = jwtTokenUtil.generateToken(user.getEmail());
    user.setLastLogin(LocalDateTime.now());
    user.setToken(newToken);
    return user;
  }

  private LoginResponse buildLoginResponse(UserEntity user) {
    String token = user.getToken();
    LocalDateTime expiration = LocalDateTime.now()
        .plusSeconds(jwtTokenUtil.getExpirationFromToken(token));
    return new LoginResponse(token,expiration,user.getEmail());
  }

  @Override
  public boolean validateToken(String token) {
    return Optional.ofNullable(token)
        .map(jwtTokenUtil::validateToken)
        .orElse(false);
  }
}
