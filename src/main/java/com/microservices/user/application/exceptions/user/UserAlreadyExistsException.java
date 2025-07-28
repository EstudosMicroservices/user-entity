package com.microservices.user.application.exceptions.user;

import com.microservices.user.application.exceptions.BaseException;
import org.springframework.http.HttpStatusCode;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String detail) {
        super(HttpStatusCode.valueOf(409), "User already exists!", detail);
    }
}
