package com.gestion.recetas.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receta {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private Integer tiempoPreparacionMin;

    @Enumerated(EnumType.STRING)
    private Dificultad dificultad;

    // Relación N:1 con Categoria
    @ManyToOne
    private Categoria categoria;

    // Relación N:M con Ingrediente
    @ManyToMany
    private Set<Ingrediente> ingredientes;
}
