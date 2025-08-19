package com.theakylino.presentation.exception;

import java.io.Serial;

public class EmailAlreadyExistsException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 6984222943148703738L;

  public EmailAlreadyExistsException(String message) {
    super(message);
  }
}
