package com.microservices.user.application.validation;

import com.microservices.user.application.exceptions.user.DateValidationException;
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
        if (value == null) {
//          I don't think the Dto validation will let this be thrown though.
            log.error("[ValidateDataNascimentoImpl] Birthdate value is null");
            throw new DateValidationException("Date value is null!");
        }

        if (value.getYear() > LocalDate.now().getYear()) {
            log.error("[ValidateDataNascimentoImpl] Birthdate value is in future");
            throw new DateValidationException("I don't think you're a time traveller.");
        }
        log.info("[ValidateDataNascimentoImpl] Successfully validated birthdate!");
        LocalDate currentDate = LocalDate.now();
        return value.isBefore(currentDate);
    }
}
