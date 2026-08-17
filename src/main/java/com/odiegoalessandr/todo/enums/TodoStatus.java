package com.odiegoalessandr.todo.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status possíveis de um todo")
public enum TodoStatus {
  @Schema(description = "Todo ainda não iniciado")
  TODO,

  @Schema(description = "Todo em andamento")
  DOING,

  @Schema(description = "Todo concluído")
  DONE
}
