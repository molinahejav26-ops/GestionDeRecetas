package com.gestion.recetas.dto;

import com.gestion.recetas.model.Dificultad;
import com.gestion.recetas.model.Ingrediente;
import com.gestion.recetas.model.Receta;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Comparator;
import java.util.List;


@Schema(name = "GetRecetaDetailDto", description = "Detalle de una receta con sus ingredientes")
public record GetRecetaDetailDto(
        @Schema(example = "1") Long id,
        @Schema(example = "Tortitas") String nombre,
        @Schema(example = "20") Integer tiempoPreparacionMin,
        @Schema(example = "FACIL") Dificultad dificultad,
        @Schema(example = "1") Long categoriaId,
        @Schema(example = "Postres") String categoriaNombre,
        List<GetIngredienteDto> ingredientes
) {
    public static GetRecetaDetailDto of(Receta r) {
        return new GetRecetaDetailDto(
                r.getId(),
                r.getNombre(),
                r.getTiempoPreparacionMin(),
                r.getDificultad(),
                r.getCategoria() != null ? r.getCategoria().getId() : null,
                r.getCategoria() != null ? r.getCategoria().getNombre() : null,
                r.getIngredientes().stream()
                        .sorted(Comparator.comparing(Ingrediente::getId))
                        .map(GetIngredienteDto::of)
                        .toList()
        );
    }
}
