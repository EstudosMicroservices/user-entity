package com.microservices.user.application.usecases;

import com.microservices.user.application.dto.UserDto;

public interface UpdateUseCase {
    UserDto updateUser(String id, UserDto userDto);
}
