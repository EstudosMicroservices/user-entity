package com.microservices.user.application.services;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.services.UserServiceImpl;
import com.microservices.user.application.usecasesimpl.*;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock
    private CreateUseCaseImpl createUseCaseImpl;

    @Mock
    private FindByIdUseCaseImpl findByIdUseCaseImpl;

    @Mock
    private FindByEmailUseCaseImpl findByEmailUseCaseImpl;

    @Mock
    private FindAllUseCaseImpl findAllUseCaseImpl;

    @Mock
    private DeleteByIdUseCaseImpl deleteByIdUseCaseImpl;

    @Mock
    private UpdateUseCaseImpl updateUseCaseImpl;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserDto userDto;
    private String userId;

    @BeforeEach
    void setup(){
        this.userDto = UserTestFactory.createUserDto();
        this.userId = "1";
    }

    @Test
    void findAllUsersTest(){
        when(findAllUseCaseImpl.findAll()).thenReturn(List.of(userDto));

        List<UserDto> result = userServiceImpl.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDto.email(), result.getFirst().email());
        verify(findAllUseCaseImpl).findAll();
    }

    @Test
    void findByIdTest(){

        when(findByIdUseCaseImpl.findById(userDto.id())).thenReturn(userDto);

        UserDto result = userServiceImpl.findById(userDto.id());

        assertNotNull(result);
        assertEquals(result.id(), userDto.id());

        verify(findByIdUseCaseImpl).findById(userDto.id());
    }

    @Test
    void findUsersByEmailTest(){

        when(findByEmailUseCaseImpl.findByEmail(userDto.email())).thenReturn(userDto);

        UserDto result = userServiceImpl.findByEmail(userDto.email());

        assertNotNull(result);
        assertEquals(result.email(), userDto.email());

        verify(findByEmailUseCaseImpl).findByEmail(userDto.email());
    }

    @Test
    void createUserTest(){

        when(createUseCaseImpl.createUser(userDto)).thenReturn(userDto);

        UserDto result = userServiceImpl.createUser(userDto);

        assertNotNull(result);
        assertEquals(result.email(), userDto.email());
        verify(createUseCaseImpl).createUser(userDto);
    }

    @Test
    void updateUserTest(){
        when(updateUseCaseImpl.updateUser(userId, userDto)).thenReturn(userDto);

        UserDto result = userServiceImpl.updateuser(userId, userDto);

        assertNotNull(result);
        assertEquals(result.email(), userDto.email());
        verify(updateUseCaseImpl).updateUser(userId, userDto);
    }

    @Test
    void deleteUserTest(){

        doNothing().when(deleteByIdUseCaseImpl).deleteUser(userId);

        userServiceImpl.deleteUser(userId);

        verify(deleteByIdUseCaseImpl).deleteUser(userId);
    }
}
