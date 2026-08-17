package com.odiegoalessandr.todo.dto;

import com.odiegoalessandr.todo.enums.TodoPriority;
import com.odiegoalessandr.todo.enums.TodoStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;
import java.util.UUID;

public record CreateTodoDto(@NotBlank String title, Optional<String> description,
                            Optional<UUID> parentId, Optional<TodoStatus> status, Optional<TodoPriority> priority) {
}
