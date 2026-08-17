package com.odiegoalessandr.todo.specification;

import com.odiegoalessandr.todo.entity.Todo;
import com.odiegoalessandr.todo.enums.TodoPriority;
import com.odiegoalessandr.todo.enums.TodoStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class TodoSpecification {

  private TodoSpecification() {
  }

  public static Specification<Todo> hasOwnerId(UUID ownerId) {
    return (root, query, cb) ->
      cb.equal(root.get("owner").get("id"), ownerId);
  }

  public static Specification<Todo> hasStatus(TodoStatus status) {
    return (root, query, cb) ->
      cb.equal(root.get("status"), status);
  }

  public static Specification<Todo> hasPriority(TodoPriority priority) {
    return (root, query, cb) ->
      cb.equal(root.get("priority"), priority);
  }

  public static Specification<Todo> hasParentId(UUID parentId) {
    return (root, query, cb) ->
      cb.equal(root.get("parent").get("id"), parentId);
  }
}
