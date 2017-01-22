package com.softconf.confera.identity.users;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.softconf.confera.CheckedTransactional;
import com.softconf.confera.exceptions.BasicException;
import com.softconf.confera.exceptions.ErrorCode;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
@CheckedTransactional
public class CurrentUserService {

  public User getSessionUser() {
    return Optional.ofNullable(getAuthenticatedUser()).orElseThrow(() -> new BasicException(ErrorCode.ERR_0003));
  }

  private User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return null;
    }
    Object principal = authentication.getPrincipal();
    if (principal instanceof User) {
      return (User) principal;
    }
    return null;
  }

}
