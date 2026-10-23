package com.gestion.recetas.controller;

import com.gestion.recetas.dto.*;
import com.gestion.recetas.model.Receta;
import com.gestion.recetas.service.RecetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/recetas")
@RequiredArgsConstructor
@Validated
@Tag(name = "Recetas", description = "CRUD de recetas y gestión de sus ingredientes (relación M:N)")
public class RecetaController {

    private final RecetaService recetaService;

    @Operation(summary = "Listar recetas",
            description = "Devuelve todas las recetas o, con el parámetro categoriaId, solo las de esa categoría")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de recetas",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetRecetaListDto.class)))),
            @ApiResponse(responseCode = "404", description = "No hay recetas (o la categoría no existe)",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<List<GetRecetaListDto>> getAll(
            @Parameter(description = "Filtrar por id de categoría")
            @RequestParam(required = false) Long categoriaId) {

        List<Receta> result = (categoriaId == null)
                ? recetaService.getAll()
                : recetaService.getByCategoria(categoriaId);
        return ResponseEntity.ok(result.stream().map(GetRecetaListDto::of).toList());
    }

    @Operation(summary = "Obtener una receta con sus ingredientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receta con la lista de ingredientes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetRecetaDetailDto.class))),
            @ApiResponse(responseCode = "404", description = "Receta no encontrada",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{recetaId}")
    public ResponseEntity<GetRecetaDetailDto> getById(@PathVariable @Min(1) Long recetaId) {
        return ResponseEntity.ok(GetRecetaDetailDto.of(recetaService.getById(recetaId)));
    }

    @Operation(summary = "Crear una receta",
            description = "Requiere el id de una categoría existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Receta creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetRecetaDetailDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o tiempo de preparación <= 0",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "La categoría indicada no existe",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<GetRecetaDetailDto> add(@Valid @RequestBody CreateRecetaDto dto) {
        return ResponseEntity.status(201).body(GetRecetaDetailDto.of(recetaService.add(dto)));
    }

    @Operation(summary = "Modificar una receta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receta modificada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetRecetaDetailDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o tiempo de preparación <= 0",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Receta o categoría no encontrada",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Nombre de receta duplicado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{recetaId}")
    public ResponseEntity<GetRecetaDetailDto> update(@PathVariable @Min(1) Long recetaId,
                                                     @Valid @RequestBody UpdateRecetaDto dto) {
        return ResponseEntity.ok(GetRecetaDetailDto.of(recetaService.update(recetaId, dto)));
    }

    @Operation(summary = "Eliminar una receta")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Receta eliminada"),
            @ApiResponse(responseCode = "404", description = "Receta no encontrada",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{recetaId}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long recetaId) {
        recetaService.delete(recetaId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Añadir un ingrediente a una receta",
            description = "Relación M:N. El ingrediente debe existir y no estar ya en la receta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ingrediente añadido; devuelve la receta actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetRecetaDetailDto.class))),
            @ApiResponse(responseCode = "400", description = "Falta el id del ingrediente",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Receta o ingrediente no encontrado",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "El ingrediente ya estaba añadido a la receta",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/{recetaId}/ingredientes")
    public ResponseEntity<GetRecetaDetailDto> addIngrediente(@PathVariable @Min(1) Long recetaId,
                                                             @Valid @RequestBody AddIngredienteDto dto) {
        Receta receta = recetaService.addIngrediente(recetaId, dto.ingredienteId());
        return ResponseEntity.status(201).body(GetRecetaDetailDto.of(receta));
    }
}

