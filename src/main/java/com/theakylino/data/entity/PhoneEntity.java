package com.theakylino.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "phones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 20)
  private String number;

  @Column(name = "city_code", nullable = false, length = 10)
  private String citycode;

  @Column(name = "country_code", nullable = false, length = 10)
  private String contrycode;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;
}