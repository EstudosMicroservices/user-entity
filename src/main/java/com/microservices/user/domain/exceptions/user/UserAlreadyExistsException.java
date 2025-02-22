package com.microservices.user.domain.exceptions.user;

import com.microservices.user.domain.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String detail) {
        super(HttpStatus.CONFLICT, "User not found!", detail);
    }
}
