package com.softconf.confera.security;

import com.softconf.confera.identity.users.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class AuthTokenProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

  private final UserDetailsService userService;

  public AuthTokenProcessingFilter(UserDetailsService userService) {
    this.userService = userService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws ServletException, IOException {
    HttpServletRequest httpRequest = this.getAsHttpRequest(request);

    String authToken = AuthTokenUtil.extractAuthTokenFromRequest(httpRequest);
    String userId = AuthTokenUtil.getUserIdFromToken(authToken);

    if (StringUtils.isNotEmpty(userId)) {
      UserDetails user = userService.loadUserByUsername(userId);
      if (AuthTokenUtil.validateToken(authToken, (User) user)) {
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    chain.doFilter(request, response);
  }

  private HttpServletRequest getAsHttpRequest(ServletRequest request) throws ServletException {
    if (!(request instanceof HttpServletRequest)) {
      throw new ServletException("Expecting an HTTP request");
    }
    return (HttpServletRequest) request;
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest hsr) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest hsr) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
