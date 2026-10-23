package com.gestion.recetas.dto;

import com.gestion.recetas.model.Ingrediente;
import jakarta.validation.constraints.NotBlank;

public record EditIngredienteDto(
        @NotBlank(message = "{editIngredienteDto.nombre.notBlank}")
        String nombre
) {
    public Ingrediente to() {
        return Ingrediente.builder().nombre(nombre).build();
    }
}
