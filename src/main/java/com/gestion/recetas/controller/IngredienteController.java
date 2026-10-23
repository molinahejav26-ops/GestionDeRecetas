package com.gestion.recetas.controller;

import com.gestion.recetas.dto.EditIngredienteDto;
import com.gestion.recetas.dto.GetIngredienteDto;
import com.gestion.recetas.repo.IngredienteRepository;
import com.gestion.recetas.service.IngredienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredientes")
@RequiredArgsConstructor
@Tag(name = "Ingredientes", description = "Crear y listar ingredientes")
public class IngredienteController {
    private final IngredienteService ingredienteService;
    @Operation(summary = "Listar todos los ingredientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de ingredientes",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetIngredienteDto.class)))),
            @ApiResponse(responseCode = "404", description = "No hay ingredientes",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<List<GetIngredienteDto>> getAll() {
        return ResponseEntity.ok(
                ingredienteService.getAll().stream().map(GetIngredienteDto::of).toList());
    }
    @Operation(summary = "Crear un ingrediente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ingrediente creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetIngredienteDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe un ingrediente con ese nombre",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<GetIngredienteDto> add(@Valid @RequestBody EditIngredienteDto dto) {
        return ResponseEntity.status(201).body(GetIngredienteDto.of(ingredienteService.add(dto)));
    }


}
