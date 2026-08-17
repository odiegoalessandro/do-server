package com.odiegoalessandr.todo.service;

import com.odiegoalessandr.todo.dto.CreateTodoDto;
import com.odiegoalessandr.todo.dto.RequestTodo;
import com.odiegoalessandr.todo.entity.Todo;
import com.odiegoalessandr.todo.entity.User;
import com.odiegoalessandr.todo.enums.TodoPriority;
import com.odiegoalessandr.todo.enums.TodoStatus;
import com.odiegoalessandr.todo.repository.TodoRepository;
import com.odiegoalessandr.todo.specification.TodoSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TodoService {
  private final TodoRepository todoRepository;

  public TodoService(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  public RequestTodo create(CreateTodoDto createTodoDto, User user){
    var todo = Todo.builder()
      .title(createTodoDto.title())
      .description(createTodoDto.description().orElse(""))
      .status(createTodoDto.status().orElse(TodoStatus.TODO))
      .priority(createTodoDto.priority().orElse(TodoPriority.LOW))
      .owner(user)
      .build();

    setParent(todo, createTodoDto.parentId().orElse(null), user);

    return RequestTodo.from(todoRepository.save(todo));
  }

  public List<RequestTodo> findAll(
    User user,
    TodoStatus status,
    TodoPriority priority,
    UUID parentId
  ) {
    var specifications = new ArrayList<Specification<Todo>>();

    specifications.add(TodoSpecification.hasOwnerId(user.getId()));

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

  public RequestTodo findById(UUID id, User user) {
    return todoRepository.findByIdAndOwnerId(id, user.getId())
      .map(RequestTodo::from)
      .orElseThrow(() -> new RuntimeException("Todo not found"));
  }

  public RequestTodo update(UUID id, CreateTodoDto updateTodoDto, User user) {
    var todo = todoRepository.findByIdAndOwnerId(id, user.getId())
      .orElseThrow(() -> new RuntimeException("Todo not found"));

    todo.setTitle(updateTodoDto.title());
    todo.setDescription(updateTodoDto.description().orElse(""));
    todo.setStatus(updateTodoDto.status().orElse(TodoStatus.TODO));
    todo.setPriority(updateTodoDto.priority().orElse(TodoPriority.LOW));
    setParent(todo, updateTodoDto.parentId().orElse(null), user);

    return RequestTodo.from(todoRepository.save(todo));
  }

  public void delete(UUID id, User user) {
    var todo = todoRepository.findByIdAndOwnerId(id, user.getId())
      .orElseThrow(() -> new RuntimeException("Todo not found"));

    todoRepository.delete(todo);
  }

  private void setParent(Todo todo, UUID parentId, User user) {
    if (parentId == null) {
      todo.setParent(null);
      return;
    }

    var parentTodo = todoRepository.findByIdAndOwnerId(parentId, user.getId())
      .orElseThrow(() -> new RuntimeException("Parent todo not found"));

    if (todo.getId() != null && todo.getId().equals(parentId)) {
      throw new RuntimeException("Todo cannot be its own parent");
    }

    todo.setParent(parentTodo);
  }
}
