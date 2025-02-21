package com.microservices.user.application.services.usecasesimpl;

import com.microservices.user.application.services.usecases.UpdateUseCase;
import com.microservices.user.domain.dto.UserDto;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.infrastructure.exceptions.user.UserNotFoundException;
import com.microservices.user.infrastructure.mappers.UserMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUseCaseImpl implements UpdateUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User's email not found!"));

        User newUser = userMapper.toUserWithId(id, userDto);
        User savedUser = userRepositoryPort.updateUser(newUser);

        return userMapper.toDto(savedUser);
    }
}
