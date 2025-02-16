package com.microservices.user.application.services;

import com.microservices.user.application.services.usecasesimpl.*;
import com.microservices.user.domain.user.UserDto;
import com.microservices.user.ports.inbound.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public boolean deleteByEmail(String email) {
        return deleteByEmailUseCase.deleteUser(email);
    }
}
