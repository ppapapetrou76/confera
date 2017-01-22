package com.softconf.confera.exceptions;

import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Profile("!api-tests")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private ResourceBundle bundle;

  public GlobalExceptionHandler() {
    bundle = ResourceBundle.getBundle("bundles.events", Locale.US);
  }

  @ExceptionHandler(BasicException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleBasicException(BasicException basicException) {
    ErrorCode errCode = basicException.getErrorCode();
    String errMessage = getBundleString(errCode.name());

    return new ResponseEntity(new ErrorResponse(errCode.name(), errMessage, errCode.getStatus().value()), errCode.getStatus());
  }

  private String getBundleString(String key) {
    if (bundle.containsKey(key)) {
      return bundle.getString(key);
    }
    return key;
  }
}
