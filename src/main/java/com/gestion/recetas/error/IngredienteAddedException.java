package com.gestion.recetas.error;


public class IngredienteAddedException extends RuntimeException {
    public IngredienteAddedException(Long recetaId, Long ingredienteId) {
        super("El ingrediente " + ingredienteId + " ya está añadido a la receta " + recetaId);
    }
}
