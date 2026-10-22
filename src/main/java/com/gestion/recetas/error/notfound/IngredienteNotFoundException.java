package com.gestion.recetas.error.notfound;

public class IngredienteNotFoundException extends EntidadNotFoundException {

    public IngredienteNotFoundException() {
        super("Ingredientes no encontrados");
    }

    public IngredienteNotFoundException(Long id) {
        super("No se ha encontrado el ingrediente con id " + id);
    }

    public IngredienteNotFoundException(String message) {
        super(message);
    }
}