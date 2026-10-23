package com.gestion.recetas.validation;

import com.gestion.recetas.repo.RecetaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UniqueRecetaNombreValidator
        implements ConstraintValidator<UniqueRecetaNombre, String> {

    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return !StringUtils.hasText(value) || !recetaRepository.existsByNombre(value);
    }
}
