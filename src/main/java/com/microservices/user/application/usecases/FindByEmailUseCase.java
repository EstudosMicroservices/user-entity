package com.microservices.user.application.usecases;

import com.microservices.user.domain.user.UserDto;

public interface FindByEmailUseCase {
    UserDto findByEmail(String email);
}
