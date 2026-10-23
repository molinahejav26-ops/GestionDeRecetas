package com.gestion.recetas.dto;

import com.gestion.recetas.model.Dificultad;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "UpdateRecetaDto", description = "Datos para modificar una receta")
public record UpdateRecetaDto(
        @Schema(example = "Tortitas esponjosas")
        @NotBlank(message = "{createRecetaDto.nombre.notBlank}")
        String nombre,

        @Schema(example = "25")
        @NotNull(message = "{createRecetaDto.tiempo.notNull}")
        Integer tiempoPreparacionMin,

        @Schema(example = "MEDIA")
        @NotNull(message = "{createRecetaDto.dificultad.notNull}")
        Dificultad dificultad,

        @Schema(example = "1")
        @NotNull(message = "{createRecetaDto.categoriaId.notNull}")
        Long categoriaId
) {
}
