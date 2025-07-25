package com.microservices.user.domain.ports.inbound;

import com.microservices.user.application.dto.UserDto;

public interface CreateUseCase {
    UserDto createUser(UserDto userDto);
}
