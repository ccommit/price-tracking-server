package com.ccommit.price_tracking_server.validation;

import com.ccommit.price_tracking_server.exception.ParentCategoryNotFoundException;
import com.ccommit.price_tracking_server.repository.CategoryRepository;
import com.ccommit.price_tracking_server.validation.anotaion.ParentCategoryExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentCategoryExistsValidator implements ConstraintValidator<ParentCategoryExists, Long> {

    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid(Long parentCategoryId, ConstraintValidatorContext context) {
        if (parentCategoryId == null) return true; // nullable일 경우
        if (!categoryRepository.existsById(parentCategoryId)){
            throw new ParentCategoryNotFoundException();
        }
        return true;
    }
}