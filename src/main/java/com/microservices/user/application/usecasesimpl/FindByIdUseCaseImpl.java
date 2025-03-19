package com.microservices.user.application.usecasesimpl;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.application.usecases.FindByIdUseCase;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class FindByIdUseCaseImpl implements FindByIdUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Override
    public UserDto findById(String id) {
        User existingUser = userRepositoryPort.findById(id).orElseThrow(() ->
                new UserNotFoundException("User's id not found!"));
        return userMapper.toDto(existingUser);
    }
}
