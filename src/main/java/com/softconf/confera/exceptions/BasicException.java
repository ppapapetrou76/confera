package com.softconf.confera.exceptions;

public class BasicException extends RuntimeException {

  private static final long serialVersionUID = 50359901764733827L;

  private final ErrorCode errorCode;

  public BasicException(ErrorCode errorCode) {
    super();
    this.errorCode = errorCode;
  }

  public BasicException() {
    this(ErrorCode.ERR_0000);
  }

  public BasicException(ErrorCode errorCode, Throwable ex) {
    super(ex);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
