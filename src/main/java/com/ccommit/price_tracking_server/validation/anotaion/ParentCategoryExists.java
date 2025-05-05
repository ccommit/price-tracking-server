package com.ccommit.price_tracking_server.validation.anotaion;

import com.ccommit.price_tracking_server.validation.ParentCategoryExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ParentCategoryExistsValidator.class)
@Documented
public @interface ParentCategoryExists {
    String message() default "해당 부모 카테고리가 존재하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
