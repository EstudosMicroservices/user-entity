package com.microservices.user.application.exceptions.user;

import com.microservices.user.application.exceptions.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String detail) {
        super("204", "User not found!", detail);
    }
}
