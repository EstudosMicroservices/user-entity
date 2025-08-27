package com.microservices.user.application.implementations.user.query;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByEmailPort;
import com.microservices.user.domain.ports.outbound.user.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserByEmailImpl implements FindUserByEmailPort {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;
    private final Logger log = LoggerFactory.getLogger(FindUserByEmailImpl.class);

    @Override
    public UserDto findByEmail(String email) {
        log.info("[FindUserByEmailImpl] Finding user by email: {}", email);
        User user = userRepositoryPort.findUserByEmail(email).orElseThrow(() ->{
                log.error("[FindUserByEmailImpl] Couldn't find user by email!");
                return new UserNotFoundException("User's email not found!");
        });
        log.info("[FindUserByEmailImpl] Successfully found user with email: {}", email);
        return userMapper.toDto(user);
    }
}
