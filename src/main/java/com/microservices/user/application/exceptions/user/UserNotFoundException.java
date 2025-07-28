package com.microservices.user.application.exceptions.user;

import com.microservices.user.application.exceptions.BaseException;
import org.springframework.http.HttpStatusCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String detail) {
        super(HttpStatusCode.valueOf(204), "User not found!", detail);
    }
}
