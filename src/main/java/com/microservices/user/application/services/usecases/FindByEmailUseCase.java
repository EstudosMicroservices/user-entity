package com.microservices.user.application.services.usecases;

import com.microservices.user.application.services.dto.UserDto;

public interface FindByEmailUseCase {
    UserDto findByEmail(String email);
}
