package com.theakylino.infrastructure;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final AuthenticationEntryPoint restAuthEntryPoint; // tu RestAuthEntryPoint

  private boolean skip(String uri) {
    return uri.startsWith("/user-registry/v3/api-docs")
        || uri.startsWith("/user-registry/swagger-ui")
        || uri.startsWith("/user-registry/h2-console")
        || uri.startsWith("/user-registry/v1/api/auth"); // login/validate
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {

    final String uri = request.getRequestURI();
    if (skip(uri)) { filterChain.doFilter(request, response); return; }

    final String header = request.getHeader("Authorization");
    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      final String token = header.substring(7);
      if (!jwtTokenUtil.validateToken(token)) {
        restAuthEntryPoint.commence(request, response,
            new BadCredentialsException("Token inválido"));
        return;
      }

      String username = jwtTokenUtil.extractUsername(token);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        var auth = new UsernamePasswordAuthenticationToken(username, null, null /* o List.of() */);
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
      }

      filterChain.doFilter(request, response);

    } catch (ExpiredJwtException e) {
      restAuthEntryPoint.commence(request, response,
          new BadCredentialsException("Token expirado", e));
    } catch (JwtException | IllegalArgumentException e) {
      restAuthEntryPoint.commence(request, response,
          new BadCredentialsException("Token inválido", e));
    }
  }
}
