package com.odiegoalessandr.todo.controller;

import com.odiegoalessandr.todo.dto.LoginRequest;
import com.odiegoalessandr.todo.dto.RegisterRequest;
import com.odiegoalessandr.todo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public String login(@RequestBody @Valid LoginRequest loginRequest){
    return this.authService.login(loginRequest);
  }

  @PostMapping("/register")
  public String register(@RequestBody @Valid RegisterRequest registerRequest){
    return this.authService.register(registerRequest);
  }
}
