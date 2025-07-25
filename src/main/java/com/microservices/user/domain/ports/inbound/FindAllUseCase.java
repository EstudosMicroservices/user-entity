package com.microservices.user.domain.ports.inbound;

import com.microservices.user.application.dto.UserDto;

import java.util.List;

public interface FindAllUseCase {
    List<UserDto> findAll();
}
