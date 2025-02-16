package com.microservices.user.application.services.usecasesimpl;

import com.microservices.user.application.usecases.FindByEmailUseCase;
import com.microservices.user.domain.user.User;
import com.microservices.user.domain.user.UserDto;
import com.microservices.user.infrastructure.exceptions.UserNotFoundException;
import com.microservices.user.infrastructure.mappers.UserMapper;
import com.microservices.user.ports.outbound.UserRepositoryPort;
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
