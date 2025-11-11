package com.microservices.user.application.implementations.user.store;

import com.microservices.user.application.exceptions.user.UserAlreadyExistsException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.application.utils.LogSanitizer;
import com.microservices.user.domain.command.CreateUserCommand;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.user.store.CreateUserFromEventPort;
import com.microservices.user.domain.ports.outbound.user.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserFromEventImpl implements CreateUserFromEventPort {

    private static final Logger log = LoggerFactory.getLogger(CreateUserFromEventImpl.class);
    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Override
    public void createUserFromEvent(CreateUserCommand createUserCommand) {

        String sanitizedId = LogSanitizer.sanitizeId(createUserCommand.id());
        String sanitizedEmail = LogSanitizer.sanitizeEmail(createUserCommand.email());

        log.info("[CreateUserFromEventImpl] Creating user from event. id={}, email={}",
                sanitizedId, sanitizedEmail);

        User newUser = userMapper.toDomain(createUserCommand);

        String sanitizedNewUserId = LogSanitizer.sanitizeId(newUser.getId());
        String sanitizedNewUserEmail = LogSanitizer.sanitizeEmail(newUser.getEmail());

        try {
            userRepositoryPort.createUser(newUser);
            log.info("[CreateUserFromEventImpl] Successfully registered user. id={}, email={}",
                    sanitizedNewUserId, sanitizedNewUserEmail);
        } catch (DataIntegrityViolationException ex) {
            log.warn("[CreateUserFromEventImpl] Duplicate detected on create. id={}, email={}",
                    sanitizedNewUserId, sanitizedNewUserEmail);
            throw new UserAlreadyExistsException("User's id or email already exists!");
        }
    }
}
