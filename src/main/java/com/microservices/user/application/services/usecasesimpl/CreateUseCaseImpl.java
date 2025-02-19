package com.microservices.user.application.services.usecasesimpl;

import com.microservices.user.application.usecases.CreateUseCase;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.dto.UserDto;
import com.microservices.user.infrastructure.exceptions.user.UserAlreadyExistsException;
import com.microservices.user.infrastructure.mappers.UserMapper;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class CreateUseCaseImpl implements CreateUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        userRepositoryPort.findUserByEmail(userDto.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(HttpStatus.NO_CONTENT, "Email not found!");
                });
        User newUser = userMapper.toUser(userDto);
        User savedUser = userRepositoryPort.createUser(newUser);

        return userMapper.toDto(savedUser);
    }
}
