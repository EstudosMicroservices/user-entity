package com.microservices.user.application.usecaseimpl;

import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.usecasesimpl.DeleteByIdUseCaseImpl;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DeleteByIdUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private DeleteByIdUseCaseImpl deleteByIdUseCaseImpl;

    private User user;
    private String userId;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.userId = user.getId();
    }

    @Test
    void deleteUserTest() {
        when(userRepositoryPort.findById(userId)).thenReturn(java.util.Optional.of(user));

        deleteByIdUseCaseImpl.deleteUser(userId);

        verify(userRepositoryPort, times(1)).deleteUser(userId);
    }

    @Test
    void deleteUserThrowsExceptionTest() {
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> deleteByIdUseCaseImpl.deleteUser(userId)
        );

        assertNotNull(exception);
        assertEquals(HttpStatus.NO_CONTENT.value(), Integer.parseInt(exception.getHttpStatusCode()));
        assertEquals("User not found!", exception.getTitle());
        assertEquals("User's email not found!", exception.getDetail());

        verify(userRepositoryPort, never()).deleteUser(userId);

    }

}
