package com.microservices.user.application.services.validation;

import com.microservices.user.domain.validation.ValidateDataNascimentoImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidateDataNascimentoImpl.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateDataNascimento {
    String message() default "This field broke the law of time and space or incorrect date format)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
