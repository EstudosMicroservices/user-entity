package com.microservices.user.application.implementations.user.store;

import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.domain.ports.inbound.user.store.DeleteUserPort;
import com.microservices.user.domain.ports.outbound.user.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DeleteUserImpl implements DeleteUserPort {

    private final UserRepositoryPort userRepositoryPort;

    private static final Logger log = LoggerFactory.getLogger(DeleteUserImpl.class);

    @Override
    public void deleteUser(String id) {
        log.info("[DeleteUserImpl] Deleting user with id: {}", id);

        userRepositoryPort.findById(id).orElseThrow(() -> {
            log.error("[DeleteUserImpl] Couldn't find user with id: {}", id);
            return new UserNotFoundException("User's id not found!");

        });

        log.info("[DeleteUserImpl] User successfully deleted!");
        userRepositoryPort.deleteUser(id);
    }
}
