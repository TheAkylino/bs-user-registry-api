package com.theakylino.presentation.mapper;

import com.theakylino.data.entity.PhoneEntity;
import com.theakylino.presentation.dto.request.PhoneRequest;
import com.theakylino.presentation.dto.response.PhoneResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PhoneMapper {
  PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

  PhoneEntity toEntity(PhoneRequest phoneRequest);

  PhoneResponse toResponse(PhoneEntity phoneEntity);
}
