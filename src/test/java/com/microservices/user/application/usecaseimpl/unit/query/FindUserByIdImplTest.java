package com.microservices.user.application.usecaseimpl.unit.query;

import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.implementations.user.query.FindUserByIdImpl;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.user.UserRepositoryPort;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserByIdImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private FindUserByIdImpl findByIdUseCase;

    private User user;
    private String userId;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.userId = user.getId();
    }


    @Test
    @DisplayName("Unit: Find User by Id")
    void findByIdTest() {

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(user));

        User result = findByIdUseCase.findById(user.getId());

        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(userId);

        verify(userRepositoryPort).findById(userId);
    }

    @Test
    @DisplayName("Unit: 'Find User by Id' throws exception if User's id isn't found.")
    void findByIdExceptionTest() {

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> findByIdUseCase.findById(userId));

        assertNotNull(exception);
        assertEquals(HttpStatus.NO_CONTENT, exception.getHttpStatusCode());
        assertEquals("User not found!", exception.getTitle());
        assertEquals("User's id not found!", exception.getDetail());

        verify(userRepositoryPort).findById(userId);
    }
}
