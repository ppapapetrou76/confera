package com.softconf.confera.identity.users;

import com.softconf.confera.CheckedTransactional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
@CheckedTransactional
public class UserService {

  @Inject
  private UserRepository userRepo;

  public User save(User user) {
    return userRepo.save(user);
  }

}
