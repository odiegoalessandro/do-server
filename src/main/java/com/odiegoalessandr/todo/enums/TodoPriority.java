package com.odiegoalessandr.todo.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Prioridades possíveis de um todo")
public enum TodoPriority {
  @Schema(description = "Baixa prioridade")
  LOW,

  @Schema(description = "Média prioridade")
  MEDIUM,

  @Schema(description = "Alta prioridade")
  HIGH
}
