package com.theakylino.business.impl;

import com.theakylino.business.UserService;
import com.theakylino.data.entity.UserEntity;
import com.theakylino.data.repository.UserRepository;
import com.theakylino.infrastructure.JwtTokenUtil;
import com.theakylino.presentation.dto.request.RegisterUserRequest;
import com.theakylino.presentation.dto.response.UserResponse;
import com.theakylino.presentation.exception.EmailAlreadyExistsException;
import com.theakylino.presentation.mapper.UserMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final JwtTokenUtil jwtTokenUtil;
  private final PasswordEncoder passwordEncoder;

  @Value("${app.regex.password}")
  private String passwordRegex;

  public UserServiceImpl(UserRepository userRepository,
                         UserMapper userMapper,
                         JwtTokenUtil jwtTokenUtil,
                         PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.jwtTokenUtil = jwtTokenUtil;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserResponse createUser(RegisterUserRequest request) {
    return Optional.of(request)
        .filter(this::emailNotExists)
        .filter(this::isValidPassword)
        .map(this::buildUserEntity)
        .map(userRepository::save)
        .map(userMapper::toResponse)
        .orElseThrow(() -> new IllegalArgumentException("Error al crear usuario"));
  }

  private boolean emailNotExists(RegisterUserRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new EmailAlreadyExistsException("El correo ya está registrado");
    }
    return true;
  }

  private boolean isValidPassword(RegisterUserRequest request) {
    if (!Pattern.matches(passwordRegex, request.getPassword())) {
      throw new IllegalArgumentException("Formato de contraseña inválido");
    }
    return true;
  }

  private UserEntity buildUserEntity(RegisterUserRequest request) {
    var now = LocalDateTime.now();
    var token = jwtTokenUtil.generateToken(request.getEmail());
    var encodedPassword = passwordEncoder.encode(request.getPassword());

    UserEntity entity = userMapper.toEntity(request);
    entity.setCreated(now);
    entity.setModified(now);
    entity.setLastLogin(now);
    entity.setToken(token);
    entity.setPassword(encodedPassword);
    entity.setActive(true);

    entity.getPhones().forEach(phone -> phone.setUser(entity));
    return entity;
  }

}
