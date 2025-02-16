package com.microservices.user.application.usecases;

import com.microservices.user.domain.dto.UserDto;

public interface UpdateUseCase {
    UserDto updateUser(UserDto userDto);
}
