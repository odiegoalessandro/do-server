package com.odiegoalessandr.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para cadastro de usuário")
public record RegisterRequest(
  @Schema(description = "Nome de usuário", example = "diego", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  String username,

  @Schema(description = "E-mail do usuário", example = "diego@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
  @Email
  @NotBlank
  String email,

  @Schema(description = "Nome completo do usuário", example = "Diego Alessandr", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  String name,

  @Schema(description = "Senha do usuário", example = "minhaSenha123", requiredMode = Schema.RequiredMode.REQUIRED, format = "password")
  @NotBlank
  String password
) {
}
