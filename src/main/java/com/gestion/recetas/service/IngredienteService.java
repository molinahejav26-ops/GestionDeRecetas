package com.gestion.recetas.service;

import com.gestion.recetas.dto.EditIngredienteDto;
import com.gestion.recetas.error.notfound.IngredienteNotFoundException;
import com.gestion.recetas.model.Ingrediente;
import com.gestion.recetas.repo.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredienteService {
    public final IngredienteRepository ingredienteRepository;

    public List<Ingrediente> getAll() {
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();
        if (ingredientes.isEmpty()) {
            throw new IngredienteNotFoundException();
        }
        return ingredientes;
    }

    public Ingrediente getById(Long id) {
        return ingredienteRepository.findById(id)
                .orElseThrow(() -> new IngredienteNotFoundException(id));
    }

    public  Ingrediente add(EditIngredienteDto dto){
        return ingredienteRepository.save(dto.to());
    }

}
