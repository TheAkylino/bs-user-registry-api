package com.theakylino.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

  private static final ObjectMapper MAPPER = new ObjectMapper();


  @Override public void handle(HttpServletRequest req,
                               HttpServletResponse res,
                               AccessDeniedException ex)
      throws IOException {
    String msg = Optional.ofNullable(ex.getMessage())
        .filter(Predicate.not(String::isBlank))
        .orElse("Autenticación requerida");

    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    res.setContentType("application/json");
    MAPPER.writeValue(res.getWriter(), java.util.Map.of("mensaje", msg));
  }
}
