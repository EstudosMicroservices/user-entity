package com.microservices.user.infrastructure.adapters.inbound;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.domain.ports.inbound.user.query.FindAllUsersPort;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByEmailPort;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByIdPort;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Mock
    private FindAllUsersPort findAllUsersPort;

    @Mock
    private FindUserByIdPort findUserByIdPort;

    @Mock
    private FindUserByEmailPort findUserByEmailPort;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;
    @BeforeEach
    void setup() {
        this.userDto = UserTestFactory.createUserDto();
    }

    @Test
    void findAllTest() {
        when(findAllUsersPort.findAll()).thenReturn(List.of(userDto));

        ResponseEntity<List<UserDto>> result = userController.findAll();

        assertThat(result.getBody()).isEqualTo(List.of(userDto));
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody().getFirst().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(findAllUsersPort).findAll();
    }

    @Test
    void findUserByIdTest() {
        when(findUserByIdPort.findById(userDto.id())).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.findById(userDto.id());

        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(userDto);
        assertThat(result.getBody().id()).isEqualTo(userDto.id());
        assertThat(result.getBody().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(findUserByIdPort).findById(userDto.id());
    }

    @Test
    void findUserByEmailTest() {
        when(findUserByEmailPort.findByEmail(userDto.email())).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.findByEmail(userDto.email());

        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(userDto);
        assertThat(result.getBody().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(findUserByEmailPort).findByEmail(userDto.email());
    }
}
