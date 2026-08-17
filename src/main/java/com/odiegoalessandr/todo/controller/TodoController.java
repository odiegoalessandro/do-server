package com.odiegoalessandr.todo.controller;

import com.odiegoalessandr.todo.dto.CreateTodoDto;
import com.odiegoalessandr.todo.dto.RequestTodo;
import com.odiegoalessandr.todo.dto.UpdateTodoDto;
import com.odiegoalessandr.todo.entity.User;
import com.odiegoalessandr.todo.enums.TodoPriority;
import com.odiegoalessandr.todo.enums.TodoStatus;
import com.odiegoalessandr.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
@Tag(name = "Todos", description = "Endpoints para gerenciar todos do usuário autenticado")
@SecurityRequirement(name = "bearerAuth")
public class TodoController {
  private final TodoService todoService;

  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @PostMapping
  @Operation(summary = "Criar todo", description = "Cria um novo todo para o usuário autenticado.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Todo criado com sucesso", content = @Content(schema = @Schema(implementation = RequestTodo.class))),
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
    @ApiResponse(responseCode = "401", description = "JWT ausente ou inválido", content = @Content)
  })
  public RequestTodo create(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do novo todo", required = true)
    @RequestBody @Valid CreateTodoDto createTodoDto,
    @Parameter(hidden = true) @AuthenticationPrincipal User user
  ){
    return todoService.create(createTodoDto, user);
  }

  @GetMapping
  @Operation(summary = "Listar todos", description = "Lista todos os todos do usuário autenticado com filtros opcionais.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Todos encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RequestTodo.class)))),
    @ApiResponse(responseCode = "401", description = "JWT ausente ou inválido", content = @Content)
  })
  public List<RequestTodo> findAll(
    @Parameter(hidden = true) @AuthenticationPrincipal User user,
    @Parameter(description = "Filtra pelo status do todo", example = "TODO")
    @RequestParam(required = false) TodoStatus status,
    @Parameter(description = "Filtra pela prioridade do todo", example = "HIGH")
    @RequestParam(required = false) TodoPriority priority,
    @Parameter(description = "Filtra pelo ID do todo pai", example = "7f3a2d5c-0f5f-4b72-8b98-3fd6f6d7d5e1")
    @RequestParam(required = false) UUID parentId
  ) {
    return todoService.findAll(user, status, priority, parentId);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar todo por ID", description = "Busca um todo do usuário autenticado pelo ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Todo encontrado", content = @Content(schema = @Schema(implementation = RequestTodo.class))),
    @ApiResponse(responseCode = "401", description = "JWT ausente ou inválido", content = @Content),
    @ApiResponse(responseCode = "500", description = "Todo não encontrado", content = @Content)
  })
  public RequestTodo findById(
    @Parameter(description = "ID do todo", example = "7f3a2d5c-0f5f-4b72-8b98-3fd6f6d7d5e1") @PathVariable UUID id,
    @Parameter(hidden = true) @AuthenticationPrincipal User user
  ) {
    return todoService.findById(id, user);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualizar todo", description = "Atualiza um todo existente do usuário autenticado.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Todo atualizado com sucesso", content = @Content(schema = @Schema(implementation = RequestTodo.class))),
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
    @ApiResponse(responseCode = "401", description = "JWT ausente ou inválido", content = @Content),
    @ApiResponse(responseCode = "500", description = "Todo não encontrado ou todo pai inválido", content = @Content)
  })
  public RequestTodo update(
    @Parameter(description = "ID do todo", example = "7f3a2d5c-0f5f-4b72-8b98-3fd6f6d7d5e1") @PathVariable UUID id,
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados de atualização do todo", required = true)
    @RequestBody @Valid UpdateTodoDto updateTodoDto,
    @Parameter(hidden = true) @AuthenticationPrincipal User user
  ) {
    return todoService.update(id, updateTodoDto, user);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Excluir todo", description = "Exclui um todo existente do usuário autenticado.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Todo excluído com sucesso", content = @Content),
    @ApiResponse(responseCode = "401", description = "JWT ausente ou inválido", content = @Content),
    @ApiResponse(responseCode = "500", description = "Todo não encontrado", content = @Content)
  })
  public void delete(
    @Parameter(description = "ID do todo", example = "7f3a2d5c-0f5f-4b72-8b98-3fd6f6d7d5e1") @PathVariable UUID id,
    @Parameter(hidden = true) @AuthenticationPrincipal User user
  ) {
    todoService.delete(id, user);
  }
}
