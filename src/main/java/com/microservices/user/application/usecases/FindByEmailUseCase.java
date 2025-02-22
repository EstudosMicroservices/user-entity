package com.microservices.user.application.usecases;

import com.microservices.user.application.dto.UserDto;

public interface FindByEmailUseCase {
    UserDto findByEmail(String email);
}
