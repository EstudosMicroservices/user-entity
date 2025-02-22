package com.microservices.user.application.usecasesimpl;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.application.usecases.FindByEmailUseCase;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
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
