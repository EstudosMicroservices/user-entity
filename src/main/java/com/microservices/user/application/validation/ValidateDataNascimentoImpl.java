package com.microservices.user.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class ValidateDataNascimentoImpl implements ConstraintValidator<ValidateDataNascimento, LocalDate> {


    private static final Logger log = LoggerFactory.getLogger(ValidateDataNascimentoImpl.class);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        log.info("[ValidateDataNascimentoImpl] Validating birthdate");

        if (value.getYear() > LocalDate.now().getYear()) {
            return false;
        }
        log.info("[ValidateDataNascimentoImpl] Successfully validated birthdate!");
        LocalDate currentDate = LocalDate.now();
        return value.isBefore(currentDate);
    }
}
