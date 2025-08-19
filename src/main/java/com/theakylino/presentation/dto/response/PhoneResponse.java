package com.theakylino.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneResponse {

  private String number;

  @JsonProperty("citycode")
  private String citycode;

  @JsonProperty("contrycode")
  private String contrycode;
}
