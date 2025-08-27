package com.microservices.user.application.implementations.user.query;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByIdPort;
import com.microservices.user.domain.ports.outbound.user.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserByIdImpl implements FindUserByIdPort {

    private static final Logger log = LoggerFactory.getLogger(FindUserByIdImpl.class);
    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;


    @Override
    public UserDto findById(String id) {
        log.info("[FindUserByIdImpl] Finding user with id: {}", id);
        User user = userRepositoryPort.findById(id).orElseThrow(() ->{
            log.error("[FindUserByIdImpl] Couldn't find user by id!");
            return new UserNotFoundException("User's id not found!");
    });
        log.info("[FindUserByIdImpl] Successfully found user with id: {}", id);
        return userMapper.toDto(user);
    }
}
