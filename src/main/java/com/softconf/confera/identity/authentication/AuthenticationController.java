package com.softconf.confera.identity.authentication;


import javax.inject.Inject;

import com.softconf.confera.CheckedTransactional;
import com.softconf.confera.Constants;
import com.softconf.confera.identity.users.CurrentUserService;
import com.softconf.confera.identity.users.User;
import com.softconf.confera.security.AuthTokenUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "identity")
@RestController
@RequestMapping(Constants.URI_API + "/identity/authentication")
@CheckedTransactional
public class AuthenticationController {

  @Inject
  private CurrentUserService currentUserService;

  @ApiOperation("Updates user activity data and returns the authentication token for user")
  @RequestMapping(method = RequestMethod.POST)
  public Token login() {
    // If the user has reached this point, then we can assume that the
    // BASIC auth has succeeded
    // Set the LinkedIn login flag to false
    User currentUser = currentUserService.getSessionUser();
    return createToken(currentUser);
  }

  @ApiOperation("Returns the authentication token for user")
  @RequestMapping(value = "token", method = RequestMethod.POST)
  public Token getNewToken() {
    return createToken(currentUserService.getSessionUser());
  }

  private Token createToken(User user) {
    return new Token(AuthTokenUtil.createToken(user));
  }
}
