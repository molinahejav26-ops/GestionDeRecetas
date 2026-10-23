package com.gestion.recetas.dto;

import com.gestion.recetas.model.Ingrediente;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetIngredienteDto", description = "Datos de un ingrediente")
public record GetIngredienteDto(
        @Schema(description = "Identificador del ingrediente", example = "1")
        Long id,
        @Schema(description = "Nombre del ingrediente", example = "Harina")
        String nombre
) {
    public static GetIngredienteDto of(Ingrediente i) {
        return new GetIngredienteDto(i.getId(), i.getNombre());
    }
}
