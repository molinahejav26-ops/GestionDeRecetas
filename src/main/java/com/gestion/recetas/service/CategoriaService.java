package com.gestion.recetas.service;

import com.gestion.recetas.dto.EditCategoriaDto;
import com.gestion.recetas.error.notfound.CategoriaNotFoundException;
import com.gestion.recetas.model.Categoria;
import com.gestion.recetas.repo.CategoriaRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> getAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.isEmpty()) {
            throw new CategoriaNotFoundException();
        }
        return categorias;
    }

    public Categoria getById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    public Categoria add(EditCategoriaDto dto) {
        return categoriaRepository.save(dto.to());
    }

    public Categoria update(Long id, EditCategoriaDto dto) {
        Categoria c = getById(id);
        c.setNombre(dto.nombre());
        c.setDescripcion(dto.descripcion());
        return categoriaRepository.save(c);
    }

    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNotFoundException(id);
        }
        categoriaRepository.deleteById(id);
    }
}
