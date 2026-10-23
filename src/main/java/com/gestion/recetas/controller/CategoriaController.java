package com.gestion.recetas.controller;

import com.gestion.recetas.dto.EditCategoriaDto;
import com.gestion.recetas.dto.GetCategoriaDto;
import com.gestion.recetas.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
@Validated
@Tag(name = "Categorías", description = "CRUD de categorías de recetas")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Operation(summary = "Listar todas las categorías")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de categorías",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCategoriaDto.class)))),
            @ApiResponse(responseCode = "404", description = "No hay categorías",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<List<GetCategoriaDto>> getAll() {
        return ResponseEntity.ok(
                categoriaService.getAll().stream().map(GetCategoriaDto::of).toList());
    }

    @Operation(summary = "Obtener una categoría por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetCategoriaDto.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetCategoriaDto> getById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(GetCategoriaDto.of(categoriaService.getById(id)));
    }

    @Operation(summary = "Crear una categoría")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoría creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetCategoriaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe una categoría con ese nombre",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<GetCategoriaDto> add(@Valid @RequestBody EditCategoriaDto dto) {
        return ResponseEntity.status(201).body(GetCategoriaDto.of(categoriaService.add(dto)));
    }

    @Operation(summary = "Modificar una categoría")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría modificada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetCategoriaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Nombre de categoría duplicado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetCategoriaDto> update(@PathVariable @Min(1) Long id,
                                                  @Valid @RequestBody EditCategoriaDto dto) {
        return ResponseEntity.ok(GetCategoriaDto.of(categoriaService.update(id, dto)));
    }

    @Operation(summary = "Eliminar una categoría")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoría eliminada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "La categoría tiene recetas asociadas",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
