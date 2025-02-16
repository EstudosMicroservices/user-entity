package com.microservices.user.infrastructure.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
