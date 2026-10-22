package com.gestion.recetas.utils;

import com.gestion.recetas.model.Categoria;
import com.gestion.recetas.model.Dificultad;
import com.gestion.recetas.model.Ingrediente;
import com.gestion.recetas.model.Receta;
import com.gestion.recetas.repo.CategoriaRepository;
import com.gestion.recetas.repo.IngredienteRepository;
import com.gestion.recetas.repo.RecetaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Datos de ejemplo para poder probar la API nada más arrancar. */
@Component
@RequiredArgsConstructor
public class DataSeed {

    private final CategoriaRepository categoriaRepository;
    private final IngredienteRepository ingredienteRepository;
    private final RecetaRepository recetaRepository;

    @PostConstruct
    public void initData() {
        Categoria postres = Categoria.builder()
                .nombre("Postres").descripcion("Dulces y postres").build();
        Categoria principales = Categoria.builder()
                .nombre("Platos principales").descripcion("Platos fuertes para comer o cenar").build();
        categoriaRepository.saveAll(List.of(postres, principales));

        Ingrediente harina = Ingrediente.builder().nombre("Harina").build();
        Ingrediente huevos = Ingrediente.builder().nombre("Huevos").build();
        Ingrediente azucar = Ingrediente.builder().nombre("Azúcar").build();
        Ingrediente arroz = Ingrediente.builder().nombre("Arroz").build();
        ingredienteRepository.saveAll(List.of(harina, huevos, azucar, arroz));

        Receta tortitas = Receta.builder()
                .nombre("Tortitas")
                .tiempoPreparacionMin(20)
                .dificultad(Dificultad.FACIL)
                .categoria(postres)
                .ingredientes(new HashSet<>(Set.of(harina, huevos)))
                .build();
        Receta paella = Receta.builder()
                .nombre("Paella")
                .tiempoPreparacionMin(60)
                .dificultad(Dificultad.DIFICIL)
                .categoria(principales)
                .ingredientes(new HashSet<>(Set.of(arroz)))
                .build();
        recetaRepository.saveAll(List.of(tortitas, paella));
    }
}

