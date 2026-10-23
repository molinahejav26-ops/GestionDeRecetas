package com.gestion.recetas.dto;

import com.gestion.recetas.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetCategoriaDto", description = "Datos de una categoría")
public record GetCategoriaDto(
        @Schema(description = "Identificador de la categoría", example = "1")
        Long id,
        @Schema(description = "Nombre de la categoría", example = "Postres")
        String nombre,
        @Schema(description = "Descripción de la categoría", example = "Dulces y postres")
        String descripcion
) {
    public static GetCategoriaDto of(Categoria c) {
        return new GetCategoriaDto(c.getId(), c.getNombre(), c.getDescripcion());
    }
}
