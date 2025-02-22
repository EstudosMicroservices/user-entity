package com.microservices.user.application.services.usecasesimpl;

import com.microservices.user.application.services.dto.UserDto;
import com.microservices.user.application.services.usecases.FindByEmailUseCase;
import com.microservices.user.domain.exceptions.user.UserNotFoundException;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.infrastructure.mappers.UserMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindByEmailUseCaseImpl implements FindByEmailUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Override
    public UserDto findByEmail(String email) {
        User existingUser = userRepositoryPort.findUserByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User's email not found!"));
        return userMapper.toDto(existingUser);
    }
}
