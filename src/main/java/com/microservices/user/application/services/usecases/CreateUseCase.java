package com.microservices.user.application.services.usecases;

import com.microservices.user.application.services.dto.UserDto;

public interface CreateUseCase {
    UserDto createUser(UserDto userDto);

}
