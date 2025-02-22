package com.microservices.user.domain.exceptions.user;

import com.microservices.user.domain.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String detail) {
        super(HttpStatus.NO_CONTENT, "User not found!", detail);
    }
}
