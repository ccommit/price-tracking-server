package com.ccommit.price_tracking_server.validation;

import com.ccommit.price_tracking_server.exception.InvalidCategoryLevelException;
import com.ccommit.price_tracking_server.validation.anotaion.ValidCategoryLevel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class CategoryLevelValidator implements ConstraintValidator<ValidCategoryLevel, String> {

    @Override
    public boolean isValid(String categoryLevel, ConstraintValidatorContext context) {
        if (categoryLevel == null) {
            throw new InvalidCategoryLevelException();
        }
        return true;
    }
}