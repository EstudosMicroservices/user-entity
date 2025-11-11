package com.microservices.user.application.usecaseimpl.integration.query;


import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.user.query.FindUserByIdPort;
import com.microservices.user.domain.ports.outbound.user.UserRepositoryPort;
import com.microservices.user.infrastructure.database.AbstractIntegrationTest;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class FindUserByIdImplIntegrationTest extends AbstractIntegrationTest {

    private final FindUserByIdPort findUserByIdPort;
    private final UserRepositoryPort userRepositoryPort;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.userDto = UserTestFactory.createUserDto();
    }

    @AfterEach
    void cleanup() {
        userRepositoryPort.deleteAll();
    }

    @Test
    @DisplayName("Integration: Find User by Id")
    void findUserByIdTest() {

        User createdUser = userRepositoryPort.createUser(user);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());

        User userFound = findUserByIdPort.findById (createdUser.getId());

        assertNotNull(userFound);
        assertEquals(createdUser.getId(), userFound.getId());
        assertEquals(createdUser.getEmail(), userFound.getEmail());
    }

    @Test
    @DisplayName("Integration: 'Find User by Id' throws exception if User's id isn't found.")
    void findUserByIdNotFoundTest() {

        String userId = userDto.id();

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> findUserByIdPort.findById(userId));

        assertNotNull(exception);
        assertEquals("User's id not found!", exception.getDetail());
        assertEquals(HttpStatusCode.valueOf(204), exception.getHttpStatusCode());
    }
}
