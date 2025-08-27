package com.microservices.user.domain.ports.inbound.user.query;

import com.microservices.user.application.dto.UserDto;

public interface FindUserByIdPort {
    UserDto findById(String id);
}
