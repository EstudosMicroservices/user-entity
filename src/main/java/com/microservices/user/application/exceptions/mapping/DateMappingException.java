package com.microservices.user.application.exceptions.mapping;

import com.microservices.user.application.exceptions.BaseException;

public class DateMappingException extends BaseException {

    public DateMappingException(String detail) {
        super("422", "Can't mapping the date!", detail);
    }
}
