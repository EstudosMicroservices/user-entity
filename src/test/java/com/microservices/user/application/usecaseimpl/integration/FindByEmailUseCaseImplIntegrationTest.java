package com.microservices.user.application.usecaseimpl.integration;

import com.microservices.user.infrastructure.database.AbstractIntegrationTest;
import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.FindByEmailUseCase;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class FindByEmailUseCaseImplIntegrationTest extends AbstractIntegrationTest {


    private final FindByEmailUseCase findByEmailUseCase;
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
    @DisplayName("Integration: Find User by Email")
    void findByEmailTest() {

        User createdUser = userRepositoryPort.createUser(user);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getEmail());

        UserDto userFound = findByEmailUseCase.findByEmail(user.getEmail());

        assertNotNull(userFound);
        assertEquals(createdUser.getId(), userFound.id());
        assertEquals(createdUser.getEmail(), userFound.email());

    }

    @Test
    @DisplayName("Integration: 'Find User by Email' throws exception if User's email isn't found.")
    void findByEmailUserNotFoundTest() {

        String userEmail = user.getEmail();

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> findByEmailUseCase.findByEmail(userEmail));

        assertNotNull(exception);
        assertEquals("User's email not found!", exception.getDetail());
        assertEquals(HttpStatusCode.valueOf(204), exception.getHttpStatusCode());
    }

}