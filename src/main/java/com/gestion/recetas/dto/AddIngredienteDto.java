package com.gestion.recetas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "AddIngredienteDto", description = "Ingrediente existente que se quiere añadir a una receta")
public record AddIngredienteDto(
        @Schema(description = "Id del ingrediente a añadir", example = "2")
        @NotNull(message = "{addIngredienteDto.ingredienteId.notNull}")
        Long ingredienteId
) {
}
