package com.gestion.recetas.dto;

import com.gestion.recetas.model.Dificultad;
import com.gestion.recetas.model.Receta;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetRecetaListDto", description = "Elemento de un listado de recetas")
public record GetRecetaListDto(
        @Schema(example = "1") Long id,
        @Schema(example = "Tortitas") String nombre,
        @Schema(example = "20") Integer tiempoPreparacionMin,
        @Schema(example = "FACIL") Dificultad dificultad,
        @Schema(example = "Postres") String categoriaNombre
) {
    public static GetRecetaListDto of(Receta r) {
        return new GetRecetaListDto(
                r.getId(),
                r.getNombre(),
                r.getTiempoPreparacionMin(),
                r.getDificultad(),
                r.getCategoria() != null ? r.getCategoria().getNombre() : null
        );
    }
}
