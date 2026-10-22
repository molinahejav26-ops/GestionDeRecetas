package com.gestion.recetas.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gestion.recetas.error.notfound.EntidadNotFoundException;
import lombok.Builder;
import org.jspecify.annotations.Nullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EntidadNotFoundException.class)
    public ProblemDetail handleNoEncontrada(EntidadNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Recurso no encontrado");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/not-found"));
        return problem;
    }


    @ExceptionHandler(IngredienteAddedException.class)
    public ProblemDetail handleIngredienteYaAnadido(IngredienteAddedException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Ingrediente ya añadido");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/ingrediente-ya-anadido"));
        return problem;
    }


    @ExceptionHandler(TiempoInvalidoException.class)
    public ProblemDetail handleTiempoInvalido(TiempoInvalidoException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Tiempo de preparación inválido");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("https://example.com/errors/tiempo-invalido"));
        return problem;
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleIntegridad(DataIntegrityViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Conflicto de integridad");
        problem.setDetail("La operación viola una restricción de datos (nombre duplicado o recurso en uso)");
        problem.setType(URI.create("https://example.com/errors/data-integrity"));
        return problem;
    }


    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Error de validación");
        result.setTitle("Datos inválidos");

        List<ApiValidationSubError> subErrors = ex.getAllErrors().stream()
                .map(ApiValidationSubError::from)
                .toList();
        result.setProperty("invalid-params", subErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @Builder
    record ApiValidationSubError(
            String object,
            String message,
            @JsonInclude(JsonInclude.Include.NON_NULL) String field,
            @JsonInclude(JsonInclude.Include.NON_NULL) Object rejectedValue) {

        static ApiValidationSubError from(ObjectError error) {
            if (error instanceof FieldError fe) {
                return ApiValidationSubError.builder()
                        .object(fe.getObjectName())
                        .message(fe.getDefaultMessage())
                        .field(fe.getField())
                        .rejectedValue(fe.getRejectedValue())
                        .build();
            }
            return ApiValidationSubError.builder()
                    .object(error.getObjectName())
                    .message(error.getDefaultMessage())
                    .build();
        }
    }
}
