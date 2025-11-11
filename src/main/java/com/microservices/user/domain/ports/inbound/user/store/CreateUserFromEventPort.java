package com.microservices.user.domain.ports.inbound.user.store;

import com.microservices.user.application.events.UserRegisteredEvent;

public interface CreateUserFromEventPort {

    void createUserFromEvent(UserRegisteredEvent event);
}
