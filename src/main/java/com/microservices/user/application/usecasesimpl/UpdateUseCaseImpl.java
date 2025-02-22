package com.microservices.user.application.usecasesimpl;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.application.usecases.UpdateUseCase;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
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
