package com.microservices.user.application.exceptions.user;

import com.microservices.user.application.exceptions.BaseException;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String detail) {
        super("409", "User not found!", detail);
    }
}
