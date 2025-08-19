package com.theakylino.presentation.mapper;

import com.theakylino.data.entity.UserEntity;
import com.theakylino.presentation.dto.request.RegisterUserRequest;
import com.theakylino.presentation.dto.response.UserResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    uses = PhoneMapper.class,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "modified", ignore = true)
  @Mapping(target = "lastLogin", ignore = true)
  @Mapping(target = "token", ignore = true)
  @Mapping(target = "active", ignore = true)
  UserEntity toEntity(RegisterUserRequest registerUserRequest);
  UserResponse toResponse(UserEntity userEntity);
}
