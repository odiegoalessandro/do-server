package com.odiegoalessandr.todo.dto;

import com.odiegoalessandr.todo.entity.Todo;
import com.odiegoalessandr.todo.enums.TodoPriority;
import com.odiegoalessandr.todo.enums.TodoStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados retornados para um todo")
public record RequestTodo(
  @Schema(description = "ID do todo", example = "7f3a2d5c-0f5f-4b72-8b98-3fd6f6d7d5e1")
  UUID id,

  @Schema(description = "Título do todo", example = "Estudar Spring Doc")
  String title,

  @Schema(description = "Descrição detalhada do todo", example = "Documentar todos os endpoints da API")
  String description,

  @Schema(description = "Status do todo", example = "TODO")
  TodoStatus status,

  @Schema(description = "Prioridade do todo", example = "LOW")
  TodoPriority priority,

  @Schema(description = "ID do usuário dono do todo", example = "6c8b9f2a-ff3b-4ecf-9a2f-1aa9a221a111")
  UUID ownerId,

  @Schema(description = "ID do todo pai, quando existir", example = "7f3a2d5c-0f5f-4b72-8b98-3fd6f6d7d5e1", nullable = true)
  UUID parentId
) {
  public static RequestTodo from(Todo save) {
    return new RequestTodo(
      save.getId(),
      save.getTitle(),
      save.getDescription(),
      save.getStatus(),
      save.getPriority(),
      save.getOwner().getId(),
      save.getParent() != null ? save.getParent().getId() : null
    );
  }
}
