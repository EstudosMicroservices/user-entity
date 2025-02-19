package com.microservices.user.domain.model.validation;

import com.microservices.user.infrastructure.exceptions.user.DateValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidateDataNascimentoImpl implements ConstraintValidator<ValidateDataNascimento, LocalDate> {

    @Override
    public void initialize(ValidateDataNascimento constraintAnnotation) {
//  Although there's no need, neither for me, nor for this class to use this method
//  I'll leave it empty 'cause if I need it to initialize anything it'll be kinda handy.
//  Sonar forced me to say something about this...
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
//          I don't think the Dto validation will let this be thrown though.
            throw new DateValidationException("Date value is null!");
        }

        if (value.getYear() > LocalDate.now().getYear()) {
            throw new DateValidationException("I don't think you're a time traveller.");
        }

        LocalDate currentDate = LocalDate.now();
        return value.isBefore(currentDate);
    }
}
