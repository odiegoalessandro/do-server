package com.odiegoalessandr.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais para autenticação")
public record LoginRequest(
  @Schema(description = "E-mail do usuário", example = "diego@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
  @Email
  @NotBlank
  String email,

  @Schema(description = "Senha do usuário", example = "minhaSenha123", requiredMode = Schema.RequiredMode.REQUIRED, format = "password")
  @NotBlank
  String password
) {
}
