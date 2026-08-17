package com.odiegoalessandr.todo.entity;

import com.odiegoalessandr.todo.enums.TodoPriority;
import com.odiegoalessandr.todo.enums.TodoStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "todos")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Todo extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Column(
    nullable = false,
    columnDefinition = "todo_status"
  )
  private TodoStatus status = TodoStatus.TODO;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Column(
    nullable = false,
    columnDefinition = "todo_priority"
  )
  private TodoPriority priority = TodoPriority.LOW;

  @ManyToOne(
    fetch = FetchType.LAZY,
    optional = false
  )
  @JoinColumn(
    name = "owner_id",
    nullable = false
  )
  private User owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Todo parent;

  @Builder.Default
  @OneToMany(mappedBy = "parent")
  private List<Todo> children = new ArrayList<>();

  @NotBlank
  @Size(max = 65)
  @Column(
    nullable = false,
    length = 65
  )
  private String title;

  @Size(max = 255)
  @Column(length = 255)
  private String description;
}