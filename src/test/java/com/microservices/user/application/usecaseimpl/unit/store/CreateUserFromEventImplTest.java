package com.microservices.user.application.usecaseimpl.unit.store;

import com.microservices.user.application.events.UserRegisteredEvent;
import com.microservices.user.application.exceptions.user.UserAlreadyExistsException;
import com.microservices.user.application.implementations.user.store.CreateUserFromEventImpl;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.user.UserRepositoryPort;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CreateUserFromEventImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private CreateUserFromEventImpl createUserFromEvent;

    private User user;
    private UserRegisteredEvent userRegisteredEvent;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.userRegisteredEvent = UserTestFactory.createUserEvent();
    }

    @Test
    @DisplayName("Unit: Create user with rabbitmq event.")
    void createUserTest() {
        when(userRepositoryPort.findById(anyString())).thenReturn(Optional.empty());
        when(userMapper.toDomain(userRegisteredEvent)).thenReturn(user);

        ArgumentCaptor<UserRegisteredEvent> eventCaptor= ArgumentCaptor.forClass(UserRegisteredEvent.class);

        createUserFromEvent.createUserFromEvent(userRegisteredEvent);

        verify(userMapper, times(1)).toDomain(eventCaptor.capture());
        verify(userRepositoryPort, times(1)).createUser(user);

    }

    @Test
    @DisplayName("Unit: 'Create User' throws exception if User's id already exists.")
    void shouldThrowExceptionIfUserAlreadyExists() {
        when(userRepositoryPort.findById(userRegisteredEvent.getId())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class,
                () -> createUserFromEvent.createUserFromEvent(userRegisteredEvent));

        verify(userRepositoryPort, never()).createUser(any(User.class));
    }
}