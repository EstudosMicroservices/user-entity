package com.microservices.user.application.usecasesimpl;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.application.usecases.FindAllUseCase;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
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
