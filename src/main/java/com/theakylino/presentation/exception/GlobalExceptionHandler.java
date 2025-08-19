package com.theakylino.presentation.exception;



import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final int INT = 403;

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleEmailExists(EmailAlreadyExistsException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("mensaje", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("mensaje", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String,String>> handleBodyMissing(
      HttpMessageNotReadableException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("mensaje", "El Request es Requerido, o puede esta vacio"));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
    );
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Map<String, Object>> handleForbidden(HttpServletRequest req, AccessDeniedException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("mensaje", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleException(Exception exception) {
    Map<String, Object> response = new HashMap<>();
    response.put("mensaje", exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleUnauthorized(HttpServletRequest req, Exception ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }
}
