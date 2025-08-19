package com.theakylino.presentation.exception;

import java.io.Serial;

public class InvalidCredentialsException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -711552648982509441L;

  public InvalidCredentialsException(String message) {
    super(message);
  }
}
