package com.microservices.user.infrastructure.rabbitmq.listener;

import com.microservices.user.domain.command.CreateUserCommand;
import com.microservices.user.domain.ports.inbound.user.store.CreateUserFromEventPort;
import com.microservices.user.infrastructure.database.AbstractIntegrationTest;
import com.microservices.user.utils.UserTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.concurrent.TimeUnit;

import static com.microservices.user.infrastructure.config.rabbitmq.RabbitMQConfig.USER_REGISTERED_EXCHANGE;
import static com.microservices.user.infrastructure.config.rabbitmq.RabbitMQConfig.USER_REGISTERED_ROUTING_KEY;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserProfileListenerIntegrationTest extends AbstractIntegrationTest{

    @MockitoSpyBean
    private final CreateUserFromEventPort createUserFromEventPort;
    private final RabbitTemplate rabbitTemplate;

    private CreateUserCommand createUserCommand;

    @BeforeEach
    void setup() {
        this.createUserCommand = UserTestFactory.createNewUserCommand();
    }


    @Test
    @DisplayName("Integration: Testing User rabbitmq listener")
    void testHandleUserRegisteredEvent(){


        rabbitTemplate.convertAndSend(USER_REGISTERED_EXCHANGE, USER_REGISTERED_ROUTING_KEY, createUserCommand);
        await().atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(createUserFromEventPort, times(1))
                        .createUserFromEvent((createUserCommand)));

    }
}

