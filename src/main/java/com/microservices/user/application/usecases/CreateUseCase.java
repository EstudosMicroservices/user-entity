package com.microservices.user.application.usecases;

import com.microservices.user.domain.user.UserDto;

public interface CreateUseCase {
    UserDto createUser(UserDto userDto);

}
