package com.odiegoalessandr.todo.dto;

import com.odiegoalessandr.todo.enums.TodoPriority;
import com.odiegoalessandr.todo.enums.TodoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;
import java.util.UUID;

@Schema(description = "Dados para atualizar um todo existente")
public record UpdateTodoDto(
  @Schema(description = "Título do todo", example = "Estudar Spring Doc")
  Optional<String> title,

  @Schema(description = "Descrição detalhada do todo", example = "Documentar todos os endpoints da API")
  Optional<String> description,

  @Schema(description = "ID do todo pai, quando este todo for uma subtarefa", example = "7f3a2d5c-0f5f-4b72-8b98-3fd6f6d7d5e1")
  Optional<UUID> parentId,

  @Schema(description = "Status do todo", example = "DOING")
  Optional<TodoStatus> status,

  @Schema(description = "Prioridade do todo", example = "HIGH")
  Optional<TodoPriority> priority
) {
}
