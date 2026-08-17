package com.odiegoalessandr.todo.dto;

import com.odiegoalessandr.todo.entity.Todo;
import com.odiegoalessandr.todo.enums.TodoPriority;
import com.odiegoalessandr.todo.enums.TodoStatus;

import java.util.UUID;

public record RequestTodo(
  UUID id,
  String title,
  String description,
  TodoStatus status,
  TodoPriority priority,
  UUID ownerId,
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
