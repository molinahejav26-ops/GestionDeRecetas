package com.gestion.recetas.dto;

import com.gestion.recetas.model.Dificultad;
import com.gestion.recetas.model.Receta;
import com.gestion.recetas.validation.UniqueRecetaNombre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "CreateRecetaDto", description = "Datos para crear una receta")
public record CreateRecetaDto(
        @Schema(example = "Tortitas")
        @NotBlank(message = "{createRecetaDto.nombre.notBlank}")
        @UniqueRecetaNombre(message = "{createRecetaDto.nombre.UniqueRecetaNombre}")
        String nombre,

        @Schema(description = "Minutos de preparación (debe ser > 0)", example = "20")
        @NotNull(message = "{createRecetaDto.tiempo.notNull}")
        Integer tiempoPreparacionMin,

        @Schema(example = "FACIL")
        @NotNull(message = "{createRecetaDto.dificultad.notNull}")
        Dificultad dificultad,

        @Schema(description = "Id de una categoría existente", example = "1")
        @NotNull(message = "{createRecetaDto.categoriaId.notNull}")
        Long categoriaId
) {
    public Receta to() {
        return Receta.builder()
                .nombre(nombre)
                .tiempoPreparacionMin(tiempoPreparacionMin)
                .dificultad(dificultad)
                .build();
    }
}
