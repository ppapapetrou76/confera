package com.softconf.confera.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

public enum ErrorCode {

  // This is reserved for internal errors (uncaught exceptions which indicate there is a bug)
  ERR_0000(true),
  // Unknown id (same as HTTP 404)
  ERR_0001(NOT_FOUND),
  // Permission denied (same as HTTP 403)
  ERR_0002(FORBIDDEN),
  // Permission denied (same as HTTP 401)
  ERR_0003(UNAUTHORIZED),
  // Invalid attribute (usually during a create or an update)
  ERR_0004,
  // Invalid email address
  ERR_0005,
  // Minimum length violated
  ERR_0006,
  // New password cannot be same as old password
  ERR_0007;

  private transient HttpStatus status;

  @JsonIgnore
  private boolean notifyDevelopers;

  private String message;

  private ErrorCode() {
    this(BAD_REQUEST);
  }

  private ErrorCode(HttpStatus status) {
    this(status, false);
  }

  private ErrorCode(boolean notifyDevelopers) {
    this(INTERNAL_SERVER_ERROR, notifyDevelopers);
  }

  private ErrorCode(HttpStatus status, boolean notifyDevelopers) {
    this.status = status;
    this.notifyDevelopers = notifyDevelopers;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public boolean isNotifyDevelopers() {
    return notifyDevelopers;
  }

}
