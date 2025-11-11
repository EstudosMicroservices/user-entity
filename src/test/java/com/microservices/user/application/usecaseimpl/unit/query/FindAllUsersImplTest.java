package com.microservices.user.application.usecaseimpl.unit.query;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.application.implementations.user.query.FindAllUsersImpl;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.outbound.user.UserRepositoryPort;
import com.microservices.user.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class FindAllUsersImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private FindAllUsersImpl findAllUseCaseImpl;

    private User user, user2;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.user2 = UserTestFactory.createUser();
        this.userDto = UserTestFactory.createUserDto();
        this.userDto2 = UserTestFactory.createUserDtoTwo();
        this.user2.setId("2");
    }

    @Test
    @DisplayName("Unit: Find All Users")
    void findAllUsersTest() {

        List<User> userList = List.of(user, user2);

        when(userRepositoryPort.listUsers()).thenReturn(userList);

        List<User> listResult = findAllUseCaseImpl.findAll();

        assertNotNull(listResult);
        assertThat(listResult).hasSize(2);
        assertThat(listResult.get(0).getEmail()).isEqualTo("teste@teste.com");
        assertThat(listResult.get(1).getEmail()).isEqualTo("teste@teste.com");

        verify(userRepositoryPort, times(1)).listUsers();

    }

    @Test
    @DisplayName("Unit: 'Find ALl User' throws exception if returned list is empty.")
    void findAllUserExceptionTest() {

        List<User> userList = List.of();

        when(userRepositoryPort.listUsers()).thenReturn(userList);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                findAllUseCaseImpl.findAll());

        assertNotNull(exception);
        assertThat(userList).isEmpty();
        assertEquals(HttpStatus.NO_CONTENT, exception.getHttpStatusCode());
        assertEquals("User's list is empty!", exception.getDetail());

        verify(userRepositoryPort).listUsers();
    }
}
