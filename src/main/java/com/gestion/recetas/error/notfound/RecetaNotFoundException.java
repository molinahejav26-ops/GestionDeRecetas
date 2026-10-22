package com.gestion.recetas.error.notfound;

public class RecetaNotFoundException extends EntidadNotFoundException {

    public RecetaNotFoundException() {
        super("Recetas no encontradas");
    }

    public RecetaNotFoundException(Long id) {
        super("No se ha encontrado la receta con id " + id);
    }

    public RecetaNotFoundException(String message) {
        super(message);
    }
}
