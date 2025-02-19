package com.microservices.user.infrastructure.exceptions.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message){
        super(message);
    }
}
