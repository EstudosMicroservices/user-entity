package com.microservices.user.application.services.usecasesimpl.usecaseimpl;

import com.microservices.user.application.services.dto.UserDto;
import com.microservices.user.application.services.usecasesimpl.UpdateUseCaseImpl;
import com.microservices.user.domain.exceptions.user.UserNotFoundException;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.infrastructure.mappers.UserMapper;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void setup(){
        this.user = UserTestFactory.createUser();
        this.userDto = UserTestFactory.createUserDto();
        this.userId = user.getId();
    }

    @Test
    void updateUserUseCaseTest(){

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserWithId(userId, userDto)).thenReturn(user);
        when(userRepositoryPort.updateUser(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = updateUseCaseImpl.updateUser(userId, userDto);

        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo(userDto.email());

        verify(userRepositoryPort).findById(userId);
        verify(userMapper).toUserWithId(userId, userDto);
        verify(userRepositoryPort).updateUser(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void updateUserNotFoundExceptionTest() {

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> updateUseCaseImpl.updateUser(userId, userDto));

        assertThat(exception.getDetail()).isEqualTo("User's email not found!");

        verify(userRepositoryPort).findById(userId);
        verify(userRepositoryPort, never()).updateUser(any());
        verifyNoInteractions(userMapper);
    }
}
