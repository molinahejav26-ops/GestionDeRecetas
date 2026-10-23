package com.gestion.recetas.dto;

import com.gestion.recetas.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditCategoriaDto(
        @NotBlank(message = "{editCategoriaDto.nombre.notBlank}")
        String nombre,

        @Size(max = 255, message = "{editCategoriaDto.descripcion.size}")
        String descripcion
) {
    public Categoria to() {
        return Categoria.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .build();
    }
}
