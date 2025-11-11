package com.microservices.user.domain.ports.inbound.user.store;

import com.microservices.user.domain.command.CreateUserCommand;

public interface CreateUserFromEventPort {

    void createUserFromEvent(CreateUserCommand userCommand);
}
