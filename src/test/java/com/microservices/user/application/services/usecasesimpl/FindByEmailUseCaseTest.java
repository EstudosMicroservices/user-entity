package com.microservices.user.application.services.usecasesimpl;

import com.microservices.user.domain.dto.UserDto;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.infrastructure.exceptions.user.UserNotFoundException;
import com.microservices.user.infrastructure.mappers.UserMapper;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FindByEmailUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private FindByEmailUseCaseImpl findByEmailUseCaseImpl;

    private User user;
    private UserDto userDto;
    private String userEmail;

    @BeforeEach
    void setup(){
        this.user = UserTestFactory.createUser();
        this.userDto = UserTestFactory.createUserDto();
        this.userEmail = user.getEmail();
    }


    @Test
    void findUserByEmailTest(){

        when(userRepositoryPort.findUserByEmail(userEmail)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = findByEmailUseCaseImpl.findByEmail(user.getEmail());

        assertNotNull(result);
        assertThat(result.email()).isEqualTo(userEmail);

        verify(userRepositoryPort).findUserByEmail(userEmail);
        verify(userMapper).toDto(user);
    }

    @Test
    void findByUserEmailExceptionTest(){

        when(userRepositoryPort.findUserByEmail(userEmail)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> findByEmailUseCaseImpl.findByEmail(userEmail));

        assertNotNull(exception);
        assertEquals(HttpStatus.NO_CONTENT, exception.getHttpStatus());
        assertEquals("User not found!", exception.getTitle());
        assertEquals("User's email not found!", exception.getDetail());

        verify(userRepositoryPort).findUserByEmail(userEmail);
        verify(userMapper, never()).toDto(any());
    }
}
