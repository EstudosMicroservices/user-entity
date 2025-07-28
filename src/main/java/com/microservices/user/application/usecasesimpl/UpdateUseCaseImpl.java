package com.microservices.user.application.usecasesimpl;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.UpdateUseCase;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUseCaseImpl implements UpdateUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Override
    public UserDto updateUser(UserDto userDto) {
        User existingUser = userRepositoryPort.findById(userDto.id()).orElseThrow(() ->
                new UserNotFoundException("User's id not found!"));
        userMapper.updateUserEntity(userDto, existingUser);
        return userMapper.toDto(userRepositoryPort.updateUser(existingUser));
    }
}
