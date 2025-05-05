package com.ccommit.price_tracking_server.validation;

import com.ccommit.price_tracking_server.exception.CategoryNotFoundException;
import com.ccommit.price_tracking_server.repository.CategoryRepository;
import com.ccommit.price_tracking_server.validation.anotaion.CategoryExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryExistsValidator implements ConstraintValidator<CategoryExists, Long> {
    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid(Long categoryId, ConstraintValidatorContext context) {
        if (categoryId == null || !categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException();
        }
        return true;
    }

}
