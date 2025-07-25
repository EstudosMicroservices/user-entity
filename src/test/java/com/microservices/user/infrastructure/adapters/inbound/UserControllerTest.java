package com.microservices.user.infrastructure.adapters.inbound;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.domain.ports.inbound.*;
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
    private FindAllUseCase findAllUseCase;

    @Mock
    private FindByIdUseCase findByIdUseCase;

    @Mock
    private FindByEmailUseCase findByEmailUseCase;

    @Mock
    private CreateUseCase createUseCase;

    @Mock
    private UpdateUseCase updateUseCase;

    @Mock
    private DeleteByIdUseCase deleteByIdUseCase;

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
        when(findAllUseCase.findAll()).thenReturn(List.of(userDto));

        ResponseEntity<List<UserDto>> result = userController.findAll();

        assertThat(result.getBody()).isEqualTo(List.of(userDto));
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody().getFirst().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(findAllUseCase).findAll();
    }
    @Test
    void findUserByIdTest(){
        when(findByIdUseCase.findById(userDto.id())).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.findById(userDto.id());

        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(userDto);
        assertThat(result.getBody().id()).isEqualTo(userDto.id());
        assertThat(result.getBody().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(findByIdUseCase).findById(userDto.id());    }

    @Test
    void findUserByEmailTest(){
        when(findByEmailUseCase.findByEmail(userDto.email())).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.findByEmail(userDto.email());

        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(userDto);
        assertThat(result.getBody().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(findByEmailUseCase).findByEmail(userDto.email());
    }

    @Test
    void createUserTest(){
        when(createUseCase.createUser(userDto)).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.create(userDto);

        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(userDto);
        assertThat(result.getBody().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(createUseCase).createUser(userDto);
    }

    @Test
    void updateUserTest(){
        when(updateUseCase.updateUser(userDto)).thenReturn(userDto);

        ResponseEntity<UserDto> result = userController.update(userDto);

        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(userDto);
        assertThat(result.getBody().email()).isEqualTo(userDto.email());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(updateUseCase).updateUser(userDto);
    }

    @Test
    void deleteUserTest(){
        doNothing().when(deleteByIdUseCase).deleteUser(userId);

        ResponseEntity<Void> result = userController.delete(userId);

        assertNull(result.getBody());
        assertNotNull(result.getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(deleteByIdUseCase).deleteUser(userId);
    }

}
