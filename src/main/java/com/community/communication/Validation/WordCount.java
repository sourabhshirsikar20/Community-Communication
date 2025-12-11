package com.community.communication.Validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WordCountValidator.class)
public @interface WordCount {

    String message() default "Word count exceeded";

    int max();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
