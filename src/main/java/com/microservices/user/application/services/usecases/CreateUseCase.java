package com.microservices.user.application.services.usecases;

import com.microservices.user.domain.dto.UserDto;

public interface CreateUseCase {
    UserDto createUser(UserDto userDto);

}
