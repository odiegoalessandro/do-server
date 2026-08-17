package com.odiegoalessandr.todo.controller;

import com.odiegoalessandr.todo.dto.LoginRequest;
import com.odiegoalessandr.todo.dto.RegisterRequest;
import com.odiegoalessandr.todo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints públicos para cadastro e login")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  @Operation(summary = "Autenticar usuário", description = "Autentica o usuário por e-mail e senha e retorna um token JWT.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = @Content(schema = @Schema(type = "string", description = "Token JWT", example = "eyJhbGciOiJIUzI1NiJ9..."))),
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content),
    @ApiResponse(responseCode = "500", description = "Usuário não encontrado", content = @Content)
  })
  public String login(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciais de login", required = true)
    @RequestBody @Valid LoginRequest loginRequest
  ){
    return this.authService.login(loginRequest);
  }

  @PostMapping("/register")
  @Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário e retorna um token JWT para autenticação.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso", content = @Content(schema = @Schema(type = "string", description = "Token JWT", example = "eyJhbGciOiJIUzI1NiJ9..."))),
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
    @ApiResponse(responseCode = "500", description = "Username ou e-mail já cadastrado", content = @Content)
  })
  public String register(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do novo usuário", required = true)
    @RequestBody @Valid RegisterRequest registerRequest
  ){
    return this.authService.register(registerRequest);
  }
}
