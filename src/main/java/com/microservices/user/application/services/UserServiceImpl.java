package com.microservices.user.application.services;

import com.microservices.user.application.services.usecasesimpl.*;
import com.microservices.user.domain.dto.UserDto;
import com.microservices.user.domain.ports.inbound.UserServicePort;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class UserServiceImpl implements UserServicePort {

    private final CreateUseCaseImpl createUseCase;
    private final FindByEmailUseCaseImpl findByEmailUseCase;
    private final FindAllUseCaseImpl findAllUseCase;
    private final DeleteByEmailUseCaseImpl deleteByEmailUseCase;
    private final UpdateUseCaseImpl updateUseCase;

    @Override
    public List<UserDto> findAll() {
        return findAllUseCase.findAll();
    }

    @Override
    public UserDto findByEmail(String email) {
        return findByEmailUseCase.findByEmail(email);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return createUseCase.createUser(userDto);
    }

    @Override
    public UserDto updateuser(UserDto userDto) {
        return updateUseCase.updateUser(userDto);
    }

    @Override
    public void deleteByEmail(String email) {
        deleteByEmailUseCase.deleteUser(email);
    }
}
