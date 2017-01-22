package com.softconf.confera.security;

import com.softconf.confera.identity.users.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.softconf.confera.identity.users.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;

@Component
public class ConferaUserDetailsService implements UserDetailsService {

  @Inject
  private UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String idStr) {
    if (StringUtils.isEmpty(idStr)) {
      throw new BadCredentialsException("Empty identification sent.");
    }
    try {
      Long id = Long.valueOf(idStr);
      return loadUserOrThrowException(userRepo.findById(id));
    } catch (NumberFormatException nfe) {
      return loadUserOrThrowException(userRepo.findByEmail(idStr));
    }
  }

  private User loadUserOrThrowException(User user) {
    return Optional.ofNullable(user).orElseThrow(() -> new BadCredentialsException("User not found."));
  }

}
