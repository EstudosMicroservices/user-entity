package com.microservices.user.application.services.usecases;

import com.microservices.user.domain.dto.UserDto;

public interface UpdateUseCase {
    UserDto updateUser(String id, UserDto userDto);
}
