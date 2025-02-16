package com.microservices.user.application.services.usecasesimpl;

import com.microservices.user.application.usecases.FindAllUseCase;
import com.microservices.user.domain.user.User;
import com.microservices.user.domain.user.UserDto;
import com.microservices.user.infrastructure.exceptions.UserNotFoundException;
import com.microservices.user.infrastructure.mappers.UserMapper;
import com.microservices.user.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllUseCaseImpl implements FindAllUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll() {
        List<User> user = userRepositoryPort.listUsers();
        if(user.isEmpty()){
            throw new UserNotFoundException("User's list is empty!");
        }
        return userMapper.toListDto(user);
    }
}
