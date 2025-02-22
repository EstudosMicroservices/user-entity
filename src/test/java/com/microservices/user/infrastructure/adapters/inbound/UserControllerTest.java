package com.microservices.user.infrastructure.adapters.inbound;

import com.microservices.user.application.services.dto.UserDto;
import com.microservices.user.domain.services.UserServiceImpl;
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
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;
    private String userId;

    @BeforeEach
    void setup(){
        this.userDto = UserTestFactory.createUserDto();
        this.userId = "1";
    }

    @Test
    void findAllTest(){
        when(userServiceImpl.findAll()).thenReturn(List.of(userDto));

        ResponseEntity<List<UserDto>> result = userController.findAll();

        assertThat(result.getBody()).isEqualTo(List.of(userDto));
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody().getFirst().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userServiceImpl).findAll();
    }

    @Test
    void findUserByEmailTest(){
        when(userServiceImpl.findByEmail(userDto.email())).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.findByEmail(userDto.email());

        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(userDto);
        assertThat(result.getBody().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userServiceImpl).findByEmail(userDto.email());
    }

    @Test
    void createUserTest(){
        when(userServiceImpl.createUser(userDto)).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.create(userDto);

        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(userDto);
        assertThat(result.getBody().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(userServiceImpl).createUser(userDto);
    }

    @Test
    void updateUserTest(){
        when(userServiceImpl.updateuser(userId, userDto)).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.update(userId, userDto);

        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(userDto);
        assertThat(result.getBody().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userServiceImpl).updateuser(userId, userDto);
    }

    @Test
    void deleteUserTest(){
        doNothing().when(userServiceImpl).deleteUser(userId);

        ResponseEntity<Void> result = userController.delete(userId);

        assertNull(result.getBody());
        assertNotNull(result.getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(userServiceImpl).deleteUser(userId);
    }

}
