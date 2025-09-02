package com.microservices.user.application.implementations.user.store;

import com.microservices.user.application.events.UserRegisteredEvent;
import com.microservices.user.application.exceptions.user.UserAlreadyExistsException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.user.store.CreateUserFromEventPort;
import com.microservices.user.domain.ports.outbound.user.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserFromEventImpl implements CreateUserFromEventPort {

    private static final Logger log = LoggerFactory.getLogger(CreateUserFromEventImpl.class);
    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Override
    public void createUserFromEvent(UserRegisteredEvent event) {
        log.info("[CreateUserFromEventImpl] Creating user from messaging event.");
        User newUser = userMapper.toDomain(event);
        if (userRepositoryPort.findById(event.getId()).isPresent()) {
            log.error("[CreateUserFromEventImpl] User already registered!");
            throw new UserAlreadyExistsException("User's id already exists!");
        }
        log.info("[CreateUserFromEventImpl] Successfully registered user!");
        userRepositoryPort.createUser(newUser);
    }
}
