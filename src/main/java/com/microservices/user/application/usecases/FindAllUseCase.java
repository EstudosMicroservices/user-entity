package com.microservices.user.application.usecases;

import com.microservices.user.domain.dto.UserDto;

import java.util.List;

public interface FindAllUseCase {
    List<UserDto> findAll();
}
