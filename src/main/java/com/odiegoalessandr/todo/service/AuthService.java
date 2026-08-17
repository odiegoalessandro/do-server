package com.odiegoalessandr.todo.service;

import com.odiegoalessandr.todo.dto.LoginRequest;
import com.odiegoalessandr.todo.dto.RegisterRequest;
import com.odiegoalessandr.todo.entity.User;
import com.odiegoalessandr.todo.repository.UserRepository;
import com.odiegoalessandr.todo.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthService(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
    this.jwtUtils = jwtUtils;
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public String login(LoginRequest loginRequest) {
    var user =  userRepository.findByEmail(loginRequest.email())
      .orElseThrow(() -> new IllegalArgumentException("User not found"));

    // Caution: the password must come from loginRequest; otherwise, authentication will always fail.
    var authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.password())
    );

    return this.jwtUtils.generateJwtToken(authentication.getName());
  }

  @Transactional
  public String register(RegisterRequest registerRequest) {
    if (userRepository.existsByUsername(registerRequest.username())) {
      throw new IllegalArgumentException("Username already exists");
    }

    if (userRepository.existsByEmail(registerRequest.email())) {
      throw new IllegalArgumentException("Email already exists");
    }

    var user = User.builder()
      .username(registerRequest.username())
      .email(registerRequest.email())
      .fullName(registerRequest.name())
      .passwordHash(passwordEncoder.encode(registerRequest.password()))
      .build();

    userRepository.save(user);

    return jwtUtils.generateJwtToken(user.getUsername());
  }
}
