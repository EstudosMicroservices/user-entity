package com.microservices.user.infrastructure.exceptions.user;

import com.microservices.user.infrastructure.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class DateValidationException extends BaseException {

    public DateValidationException(String detail) {
        super(HttpStatus.BAD_REQUEST, "Time validation error!", detail);
    }
}
