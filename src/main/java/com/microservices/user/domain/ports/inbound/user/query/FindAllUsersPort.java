package com.microservices.user.domain.ports.inbound.user.query;

import com.microservices.user.application.dto.UserDto;

import java.util.List;

public interface FindAllUsersPort {
    List<UserDto> findAll();
}
