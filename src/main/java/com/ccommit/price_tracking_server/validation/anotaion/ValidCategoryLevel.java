package com.ccommit.price_tracking_server.validation.anotaion;

import com.ccommit.price_tracking_server.validation.CategoryLevelValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryLevelValidator.class)
@Documented
public @interface ValidCategoryLevel {
    String message() default "해당 카테고리 레벨은 없는 레벨입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}