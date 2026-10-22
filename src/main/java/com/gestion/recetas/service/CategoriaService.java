package com.gestion.recetas.service;

import com.gestion.recetas.error.notfound.CategoriaNotFoundException;
import com.gestion.recetas.model.Categoria;
import com.gestion.recetas.repo.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> getAll(){
        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.isEmpty()){
            throw new CategoriaNotFoundException();
        }

        return categorias;
    }
    public Categoria getByID(Long id){
        return categoriaRepository.findById(id)
                .orElseThrow(CategoriaNotFoundException::new);
    }

    public Categoria addCategoria (Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(Long id, Categoria category){
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNotFoundException();
        }
        category.setNombre(category.getNombre());
        return categoriaRepository.save(category);
    }

    public void deleteCategoria(Long id){
        categoriaRepository.deleteById(id);
    }



}
