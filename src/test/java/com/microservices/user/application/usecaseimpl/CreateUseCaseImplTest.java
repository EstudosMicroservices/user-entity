package com.microservices.user.application.usecaseimpl;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserAlreadyExistsException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.application.usecasesimpl.CreateUseCaseImpl;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CreateUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUseCaseImpl createUseCaseImpl;

    private User user;
    private UserDto userDto;
    private UserDto userDtoEncoded;
    private String userEmail;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.userDto = UserTestFactory.createUserDto();
        this.userDtoEncoded = UserTestFactory.createUserDtoEncoded();
        this.userEmail = user.getEmail();
    }

    @Test
    void createUserTest() {
        when(userRepositoryPort.findUserByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepositoryPort.createUser(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        ArgumentCaptor<UserDto> userDtoCaptor = ArgumentCaptor.forClass(UserDto.class);

        UserDto createdUser = createUseCaseImpl.createUser(userDto);

        verify(userMapper, times(1)).toUser(userDtoCaptor.capture());
        verify(userRepositoryPort, times(1)).createUser(user);
        verify(userMapper, times(1)).toDto(user);
        assertEquals(userDto.email(), userDtoCaptor.getValue().email());
        assertEquals(userDto.email(), createdUser.email());
        when(passwordEncoder.encode(userDto.senha())).thenReturn("Senha Teste Encoded");


        assertEquals("Senha Teste Encoded", userDtoEncoded.senha());
        assertEquals(userDto.email(), createdUser.email());

    }

    @Test
    void createUserThrowsExceptionTest() {
        when(userRepositoryPort.findUserByEmail(userEmail)).thenReturn(Optional.of(user));

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> createUseCaseImpl.createUser(userDto)
        );

        assertEquals("Email already exists!", exception.getDetail());
        assertEquals(HttpStatus.CONFLICT.value(), Integer.parseInt(exception.getHttpStatusCode()));

        verify(userRepositoryPort).findUserByEmail(userEmail);
        verifyNoInteractions(userMapper);
        verify(userRepositoryPort, never()).createUser(any());
    }
}