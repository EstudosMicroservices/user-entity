package com.microservices.user.infrastructure.rabbitmq.listener;

import com.microservices.user.application.events.UserRegisteredEvent;
import com.microservices.user.domain.ports.inbound.user.store.CreateUserFromEventPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileListener {

    private static final Logger log = LoggerFactory.getLogger(UserProfileListener.class);
    private final CreateUserFromEventPort createProfileFromEvent;

    @RabbitListener(queues = "user.profile.queue")
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        log.info("[UserProfileListener] Received UserRegisteredEvent with id: {}", event.getId());
        createProfileFromEvent.createUserFromEvent(event);
    }

}