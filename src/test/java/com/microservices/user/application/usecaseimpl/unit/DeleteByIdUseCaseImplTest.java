package com.microservices.user.application.usecaseimpl.unit;

import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.usecasesimpl.DeleteByIdUseCaseImpl;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Unit: Delete User with User's Id")
    void deleteUserTest() {
        when(userRepositoryPort.findById(userId)).thenReturn(java.util.Optional.of(user));

        deleteByIdUseCaseImpl.deleteUser(userId);

        verify(userRepositoryPort, times(1)).deleteUser(userId);
    }

    @Test
    @DisplayName("Unit: 'Delete User' throws exception if User's id isn't found.")
    void deleteUserThrowsExceptionTest() {
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> deleteByIdUseCaseImpl.deleteUser(userId)
        );

        assertNotNull(exception);
        assertEquals(HttpStatus.NO_CONTENT, exception.getHttpStatusCode());
        assertEquals("User not found!", exception.getTitle());
        assertEquals("User's id not found!", exception.getDetail());

        verify(userRepositoryPort, never()).deleteUser(userId);

    }

}
