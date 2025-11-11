package com.microservices.user.application.usecaseimpl.unit.store;

import com.microservices.user.application.exceptions.user.UserAlreadyExistsException;
import com.microservices.user.application.implementations.user.store.CreateUserFromEventImpl;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.domain.command.CreateUserCommand;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserFromEventImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private CreateUserFromEventImpl createUserFromEvent;

    private User user;
    private CreateUserCommand createUserCommand;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.createUserCommand = UserTestFactory.createNewUserCommand();
    }

    @Test
    @DisplayName("Unit: Create user with rabbitmq event.")
    void createUserTest() {
        when(userMapper.toDomain(createUserCommand)).thenReturn(user);

        ArgumentCaptor<CreateUserCommand> eventCaptor= ArgumentCaptor.forClass(CreateUserCommand.class);

        createUserFromEvent.createUserFromEvent(createUserCommand);

        verify(userMapper, times(1)).toDomain(eventCaptor.capture());
        verify(userRepositoryPort, times(1)).createUser(user);

    }

    @Test
    @DisplayName("Unit: 'Create User' throws exception if User's id already exists.")
    void shouldThrowExceptionIfUserAlreadyExists() {
        when(userMapper.toDomain(createUserCommand)).thenReturn(user);

        doThrow(DataIntegrityViolationException.class).when(userRepositoryPort).createUser(user);

        assertThrows(UserAlreadyExistsException.class,
                () -> createUserFromEvent.createUserFromEvent(createUserCommand));

        verify(userRepositoryPort, times(1)).createUser(user);
    }
}
