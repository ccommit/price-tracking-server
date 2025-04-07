package com.ccommit.price_tracking_server.annotation;

import com.ccommit.price_tracking_server.enums.UserStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메서드에만 적용
@Retention(RetentionPolicy.RUNTIME) // 런타임에 어노테이션 정보를 반영
public @interface CheckToken {
    UserStatus[] roles() default {};
}