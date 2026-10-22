package com.gestion.recetas.error.notfound;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(String message) {
        super(message);
    }

    public CategoriaNotFoundException() {
        super("Categorias no encontradas");
    }
}
