package com.microservices.user.application.services.usecases;

import com.microservices.user.application.services.dto.UserDto;

import java.util.List;

public interface FindAllUseCase {
    List<UserDto> findAll();
}
