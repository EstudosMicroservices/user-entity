package com.microservices.user.domain.ports.inbound;

import com.microservices.user.application.dto.UserDto;

public interface FindByEmailUseCase {
    UserDto findByEmail(String email);
}
