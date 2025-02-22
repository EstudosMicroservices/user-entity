package com.microservices.user.application.exceptions.user;

import com.microservices.user.application.exceptions.BaseException;

public class DateValidationException extends BaseException {

    public DateValidationException(String detail) {
        super("400", "Time validation error!", detail);
    }
}
