package com.microservices.user.application.usecaseimpl.integration;

import com.microservices.user.AbstractIntegrationTest;
import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.FindAllUseCase;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.utils.UserTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class FindAllUseCasesImplIntegrationTest extends AbstractIntegrationTest {

    private final FindAllUseCase findAllUseCase;
    private final UserRepositoryPort userRepositoryPort;

    private User user;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.user.setId(null);
    }

    @AfterEach
    void cleanup() {
        userRepositoryPort.deleteAll();
    }

    @Test
    @DisplayName("Integration: Find All Users")
    void findAllUsersTest() {

        User checkCreate = userRepositoryPort.createUser(user);

        assertNotNull(checkCreate);
        assertNotNull(checkCreate.getId());

        List<UserDto> userList = findAllUseCase.findAll();

        assertNotNull(userList);
        assertEquals(checkCreate.getId(), userList.getFirst().id());
        assertEquals(checkCreate.getEmail(), userList.getFirst().email());
    }

    @Test
    @DisplayName("Integration: 'Find All Users' throws exception if returned list is empty.")
    void findAllUsersListNotFoundTest() {

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                findAllUseCase::findAll);


        assertNotNull(exception);
        assertEquals("User's list is empty!", exception.getDetail());
        assertEquals(HttpStatusCode.valueOf(204), exception.getHttpStatusCode());
    }

}
