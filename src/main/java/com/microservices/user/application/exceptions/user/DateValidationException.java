package com.microservices.user.application.exceptions.user;

import com.microservices.user.application.exceptions.BaseException;
import org.springframework.http.HttpStatusCode;

public class DateValidationException extends BaseException {

    public DateValidationException(String detail) {
        super(HttpStatusCode.valueOf(400), "Time validation error!", detail);
    }
}
