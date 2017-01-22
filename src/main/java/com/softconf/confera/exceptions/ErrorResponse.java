package com.softconf.confera.exceptions;

public class ErrorResponse {

  private final String errorCode;
  private final String message;
  private final int httpStatusCode;

  public ErrorResponse(String errorCode, String message, int httpStatusCode) {
    this.errorCode = errorCode;
    this.message = message;
    this.httpStatusCode = httpStatusCode;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getMessage() {
    return message;
  }

  public int getHttpStatusCode() {
    return httpStatusCode;
  }
}
