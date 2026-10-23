package com.gestion.recetas.service;

import com.gestion.recetas.dto.CreateRecetaDto;
import com.gestion.recetas.dto.UpdateRecetaDto;
import com.gestion.recetas.error.IngredienteAddedException;
import com.gestion.recetas.error.TiempoInvalidoException;
import com.gestion.recetas.error.notfound.*;
import com.gestion.recetas.model.Categoria;
import com.gestion.recetas.model.Ingrediente;
import com.gestion.recetas.model.Receta;
import com.gestion.recetas.repo.CategoriaRepository;
import com.gestion.recetas.repo.IngredienteRepository;
import com.gestion.recetas.repo.RecetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository recetaRepository;
    private final CategoriaRepository categoriaRepository;
    private final IngredienteRepository ingredienteRepository;

    @Transactional(readOnly = true)
    public List<Receta> getAll() {
        List<Receta> recetas = recetaRepository.findAll();
        if (recetas.isEmpty()) {
            throw new RecetaNotFoundException();
        }
        return recetas;
    }

    @Transactional(readOnly = true)
    public List<Receta> getByCategoria(Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new CategoriaNotFoundException(categoriaId);
        }
        List<Receta> recetas = recetaRepository.findByCategoriaId(categoriaId);
        if (recetas.isEmpty()) {
            throw new RecetaNotFoundException("No hay recetas en la categoría con id " + categoriaId);
        }
        return recetas;
    }

    @Transactional(readOnly = true)
    public Receta getById(Long id) {
        return recetaRepository.findById(id)
                .orElseThrow(() -> new RecetaNotFoundException(id));
    }

    @Transactional
    public Receta add(CreateRecetaDto dto) {
        validarTiempo(dto.tiempoPreparacionMin());
        Categoria categoria = buscarCategoria(dto.categoriaId());

        Receta receta = dto.to();
        receta.setCategoria(categoria);
        return recetaRepository.save(receta);
    }

    @Transactional
    public Receta update(Long id, UpdateRecetaDto dto) {
        Receta receta = getById(id);
        validarTiempo(dto.tiempoPreparacionMin());

        receta.setNombre(dto.nombre());
        receta.setTiempoPreparacionMin(dto.tiempoPreparacionMin());
        receta.setDificultad(dto.dificultad());
        receta.setCategoria(buscarCategoria(dto.categoriaId()));
        return recetaRepository.save(receta);
    }

    @Transactional
    public void delete(Long id) {
        if (!recetaRepository.existsById(id)) {
            throw new RecetaNotFoundException(id);
        }
        recetaRepository.deleteById(id);
    }


    @Transactional
    public Receta addIngrediente(Long recetaId, Long ingredienteId) {
        Receta receta = getById(recetaId);
        Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId)
                .orElseThrow(() -> new IngredienteNotFoundException(ingredienteId));

        boolean yaAnadido = receta.getIngredientes().stream()
                .anyMatch(i -> i.getId().equals(ingredienteId));
        if (yaAnadido) {
            throw new IngredienteAddedException(recetaId, ingredienteId);
        }

        receta.getIngredientes().add(ingrediente);
        return recetaRepository.save(receta);
    }

    private Categoria buscarCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNotFoundException(categoriaId));
    }

    private void validarTiempo(Integer tiempo) {
        if (tiempo == null || tiempo <= 0) {
            throw new TiempoInvalidoException(tiempo);
        }
    }
}

