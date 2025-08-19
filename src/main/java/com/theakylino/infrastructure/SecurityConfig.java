package com.theakylino.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final RestAuthEntryPoint restAuthEntryPoint;       // 401
  private final RestAccessDeniedHandler accessDeniedHandler; // 403

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authz -> authz
            // ENDPOINTS PÚBLICOS
            .requestMatchers("/error", "/error/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/v1/api/auth/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/v1/api/auth/validate").permitAll()

            // SPRINGDOC RUTAS (las que necesitas)
            .requestMatchers("/v3/api-docs/**").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/swagger-ui.html").permitAll()

            // H2 CONSOLE
            .requestMatchers("/h2-console/**").permitAll()

            // ENDPOINTS PROTEGIDOS
            .requestMatchers("/v1/api/users/userRegistration").authenticated()

            // POLÍTICA GENERAL
            .anyRequest().denyAll()
        )
        .exceptionHandling(e -> e
            .authenticationEntryPoint(restAuthEntryPoint)  // 401 Unauthorized
            .accessDeniedHandler(accessDeniedHandler)      // 403 Forbidden
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .headers(headers -> headers
            .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
