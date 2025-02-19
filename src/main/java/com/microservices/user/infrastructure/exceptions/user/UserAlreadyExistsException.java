package com.microservices.user.infrastructure.exceptions.user;

import com.microservices.user.infrastructure.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(HttpStatus httpStatus, String detail) {
        super(httpStatus, "User not found!", detail);
    }
}
