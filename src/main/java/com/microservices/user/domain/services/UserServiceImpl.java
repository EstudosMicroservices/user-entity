package com.microservices.user.domain.services;

import com.microservices.user.domain.ports.inbound.UserServicePort;
import com.microservices.user.application.services.usecasesimpl.*;
import com.microservices.user.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class UserServiceImpl implements UserServicePort {

    private final CreateUseCaseImpl createUseCaseImpl;
    private final FindByEmailUseCaseImpl findByEmailUseCaseImpl;
    private final FindAllUseCaseImpl findAllUseCaseImpl;
    private final DeleteByIdUseCaseImpl deleteByIdUseCaseImpl;
    private final UpdateUseCaseImpl updateUseCaseImpl;

    @Override
    public List<UserDto> findAll() {
        return findAllUseCaseImpl.findAll();
    }

    @Override
    public UserDto findByEmail(String email) {
        return findByEmailUseCaseImpl.findByEmail(email);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return createUseCaseImpl.createUser(userDto);
    }

    @Override
    public UserDto updateuser(String id, UserDto userDto) {
        return updateUseCaseImpl.updateUser(id, userDto);
    }

    @Override
    public void deleteUser(String id) {
        deleteByIdUseCaseImpl.deleteUser(id);
    }
}
