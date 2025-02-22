package com.microservices.user.application.services.usecases;

import com.microservices.user.application.services.dto.UserDto;

public interface UpdateUseCase {
    UserDto updateUser(String id, UserDto userDto);
}
