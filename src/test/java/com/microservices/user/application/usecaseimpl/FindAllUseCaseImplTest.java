package com.microservices.user.application.usecaseimpl;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.mappers.UserMapper;
import com.microservices.user.application.usecasesimpl.FindAllUseCaseImpl;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FindAllUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private FindAllUseCaseImpl findAllUseCaseImpl;

    private User user, user2;
    private UserDto userDto, userDto2;

    @BeforeEach
    void setup(){
        this.user = UserTestFactory.createUser();
        this.user2 = UserTestFactory.createUser();
        this.userDto = UserTestFactory.createUserDto();
        this.userDto2 = UserTestFactory.createUserDto();
        this.user2.setId("2");
    }

    @Test
    void findAllUsersTest(){

        List<User> userList = List.of(user, user2);
        List<UserDto> userDtoList = List.of(userDto, userDto2);

        when(userRepositoryPort.listUsers()).thenReturn(List.of(user, user2));
        when(userMapper.toListDto(userList)).thenReturn(userDtoList);

        List<UserDto> listResult = findAllUseCaseImpl.findAll();

        assertThat(listResult).hasSize(2);
        assertThat(listResult.get(0).email()).isEqualTo("teste@teste.com");
        assertThat(listResult.get(1).email()).isEqualTo("teste@teste.com");

        verify(userRepositoryPort, times(1)).listUsers();
        verify(userMapper, times(1)).toListDto(userList);

    }

    @Test
    void findAllUserExceptionTest(){

        List<User> userList = List.of();

        when(userRepositoryPort.listUsers()).thenReturn(userList);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                findAllUseCaseImpl.findAll());

        assertNotNull(exception);
        assertThat(userList).isEmpty();
        assertEquals(HttpStatus.NO_CONTENT.value(), Integer.parseInt(exception.getHttpStatusCode()));
        assertEquals("User's list is empty!", exception.getDetail());

        verify(userRepositoryPort).listUsers();
        verify(userMapper, never()).toListDto(any());
    }
}
