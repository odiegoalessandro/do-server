package com.odiegoalessandr.todo.service;

import com.odiegoalessandr.todo.dto.CreateTodoDto;
import com.odiegoalessandr.todo.dto.RequestTodo;
import com.odiegoalessandr.todo.dto.UpdateTodoDto;
import com.odiegoalessandr.todo.entity.Todo;
import com.odiegoalessandr.todo.enums.TodoPriority;
import com.odiegoalessandr.todo.enums.TodoStatus;
import com.odiegoalessandr.todo.repository.TodoRepository;
import com.odiegoalessandr.todo.repository.UserRepository;
import com.odiegoalessandr.todo.specification.TodoSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TodoService {
  private final TodoRepository todoRepository;
  private final UserRepository userRepository;

  public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
    this.todoRepository = todoRepository;
    this.userRepository = userRepository;
  }

  public RequestTodo create(CreateTodoDto createTodoDto, UUID userId){
    var todo = Todo.builder()
      .title(createTodoDto.title())
      .description(createTodoDto.description().orElse(""))
      .status(createTodoDto.status().orElse(TodoStatus.TODO))
      .priority(createTodoDto.priority().orElse(TodoPriority.LOW))
      .owner(userRepository.getReferenceById(userId))
      .build();

    setParent(todo, createTodoDto.parentId().orElse(null), userId);

    return RequestTodo.from(todoRepository.save(todo));
  }

  public List<RequestTodo> findAll(
    UUID userId,
    TodoStatus status,
    TodoPriority priority,
    UUID parentId
  ) {
    var specifications = new ArrayList<Specification<Todo>>();

    specifications.add(TodoSpecification.hasOwnerId(userId));

    if (status != null) {
      specifications.add(TodoSpecification.hasStatus(status));
    }

    if (priority != null) {
      specifications.add(TodoSpecification.hasPriority(priority));
    }

    if (parentId != null) {
      specifications.add(TodoSpecification.hasParentId(parentId));
    }

    return todoRepository.findAll(
      Specification.allOf(specifications)
    )
      .stream()
      .map(RequestTodo::from)
      .toList();
  }

  public RequestTodo findById(UUID id, UUID userId) {
    return todoRepository.findByIdAndOwnerId(id, userId)
      .map(RequestTodo::from)
      .orElseThrow(() -> new RuntimeException("Todo not found"));
  }

  public RequestTodo update(UUID id, UpdateTodoDto updateTodoDto, UUID userId) {
    var todo = todoRepository.findByIdAndOwnerId(id, userId)
      .orElseThrow(() -> new RuntimeException("Todo not found"));

    todo.setTitle(updateTodoDto.title().orElse(todo.getTitle()));
    todo.setDescription(updateTodoDto.description().orElse(todo.getDescription()));
    todo.setStatus(updateTodoDto.status().orElse(todo.getStatus()));
    todo.setPriority(updateTodoDto.priority().orElse(todo.getPriority()));
    updateTodoDto.parentId().ifPresent(parentId -> updateParent(todo, parentId, userId));

    return RequestTodo.from(todoRepository.save(todo));
  }

  private void updateParent(Todo todo, UUID parentId, UUID userId) {
    UUID currentParentId = todo.getParent() != null
      ? todo.getParent().getId()
      : null;

    if (!parentId.equals(currentParentId)) {
      setParent(todo, parentId, userId);
    }
  }

  public void delete(UUID id, UUID userId) {
    var todo = todoRepository.findByIdAndOwnerId(id, userId)
      .orElseThrow(() -> new RuntimeException("Todo not found"));

    todoRepository.delete(todo);
  }

  private void setParent(Todo todo, UUID parentId, UUID userId) {
    if (parentId == null) {
      todo.setParent(null);
      return;
    }

    var parentTodo = todoRepository.findByIdAndOwnerId(parentId, userId)
      .orElseThrow(() -> new RuntimeException("Parent todo not found"));

    if (todo.getId() != null && todo.getId().equals(parentId)) {
      throw new RuntimeException("Todo cannot be its own parent");
    }

    todo.setParent(parentTodo);
  }
}
