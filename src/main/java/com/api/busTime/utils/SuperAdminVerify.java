package com.api.busTime.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( ElementType.METHOD )
@Retention( RetentionPolicy.RUNTIME)
public @interface SuperAdminVerify {
    ValidationType validationType() default ValidationType.SUPER_ADMIN;

}
