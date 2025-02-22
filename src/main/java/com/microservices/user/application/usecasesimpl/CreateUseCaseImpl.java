package com.microservices.user.application.usecasesimpl;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserAlreadyExistsException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.application.usecases.CreateUseCase;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUseCaseImpl implements CreateUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        userRepositoryPort.findUserByEmail(userDto.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Email already exists!");
                });
        User newUser = userMapper.toUser(userDto);
        User savedUser = userRepositoryPort.createUser(newUser);

        return userMapper.toDto(savedUser);
    }
}
