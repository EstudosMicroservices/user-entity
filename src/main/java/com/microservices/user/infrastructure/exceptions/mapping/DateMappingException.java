package com.microservices.user.infrastructure.exceptions.mapping;

import com.microservices.user.infrastructure.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class DateMappingException extends BaseException {

    public DateMappingException(String detail) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "Can't mapping the date!", detail);
    }
}
