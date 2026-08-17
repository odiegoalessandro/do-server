package com.odiegoalessandr.todo.repository;

import com.odiegoalessandr.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, UUID>, JpaSpecificationExecutor<Todo> {
  Optional<Todo> findByIdAndOwnerId(UUID id, UUID ownerId);
}
