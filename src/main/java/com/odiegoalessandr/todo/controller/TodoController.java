package com.odiegoalessandr.todo.controller;

import com.odiegoalessandr.todo.dto.CreateTodoDto;
import com.odiegoalessandr.todo.dto.RequestTodo;
import com.odiegoalessandr.todo.entity.User;
import com.odiegoalessandr.todo.enums.TodoPriority;
import com.odiegoalessandr.todo.enums.TodoStatus;
import com.odiegoalessandr.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @PostMapping
  public RequestTodo create(@RequestBody @Valid CreateTodoDto createTodoDto, @AuthenticationPrincipal User user){
    return todoService.create(createTodoDto, user);
  }

  @GetMapping
  public List<RequestTodo> findAll(
    @AuthenticationPrincipal User user,
    @RequestParam(required = false) TodoStatus status,
    @RequestParam(required = false) TodoPriority priority,
    @RequestParam(required = false) UUID parentId
  ) {
    return todoService.findAll(user, status, priority, parentId);
  }

  @GetMapping("/{id}")
  public RequestTodo findById(@PathVariable UUID id, @AuthenticationPrincipal User user) {
    return todoService.findById(id, user);
  }

  @PutMapping("/{id}")
  public RequestTodo update(
    @PathVariable UUID id,
    @RequestBody @Valid CreateTodoDto updateTodoDto,
    @AuthenticationPrincipal User user
  ) {
    return todoService.update(id, updateTodoDto, user);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
    todoService.delete(id, user);
  }
}
