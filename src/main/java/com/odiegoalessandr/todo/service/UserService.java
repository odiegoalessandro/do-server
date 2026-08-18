package com.odiegoalessandr.todo.service;

import com.odiegoalessandr.todo.entity.User;
import com.odiegoalessandr.todo.repository.UserRepository;
import com.odiegoalessandr.todo.security.SecurityUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public SecurityUserDetails loadUserByUsername(String username) {
    var user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return SecurityUserDetails.from(user);
  }
}
