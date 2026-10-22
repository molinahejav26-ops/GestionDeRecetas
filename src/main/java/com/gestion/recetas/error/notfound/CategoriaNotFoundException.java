package com.gestion.recetas.error.notfound;

public class CategoriaNotFoundException extends EntidadNotFoundException {

    public CategoriaNotFoundException() {
        super("Categorías no encontradas");
    }

    public CategoriaNotFoundException(Long id) {
        super("No se ha encontrado la categoría con id " + id);
    }

    public CategoriaNotFoundException(String message) {
        super(message);
    }
}
