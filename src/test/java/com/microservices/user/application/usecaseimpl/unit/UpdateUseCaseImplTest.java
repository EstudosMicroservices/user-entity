package com.microservices.user.application.usecaseimpl.unit;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.application.usecasesimpl.UpdateUseCaseImpl;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UpdateUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UpdateUseCaseImpl updateUseCaseImpl;

    private User user;
    private UserDto userDto;
    private String userId;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.userDto = UserTestFactory.createUserDto();
        this.userId = user.getId();
    }

    @Test
    @DisplayName("Unit: Update User")
    void updateUserUseCaseTest() {

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepositoryPort.updateUser(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = updateUseCaseImpl.updateUser(userDto);

        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo(userDto.email());

        verify(userRepositoryPort).findById(userId);
        verify(userRepositoryPort).updateUser(user);
        verify(userMapper).toDto(user);
    }

    @Test
    @DisplayName("Unit: 'Update User' throws exception if User's id isn't found.")
    void updateUserNotFoundExceptionTest() {

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> updateUseCaseImpl.updateUser(userDto));

        assertThat(exception.getDetail()).isEqualTo("User's id not found!");
        assertEquals(HttpStatus.NO_CONTENT, exception.getHttpStatusCode());

        verify(userRepositoryPort).findById(userId);
        verify(userRepositoryPort, never()).updateUser(any());
        verifyNoInteractions(userMapper);
    }
}
