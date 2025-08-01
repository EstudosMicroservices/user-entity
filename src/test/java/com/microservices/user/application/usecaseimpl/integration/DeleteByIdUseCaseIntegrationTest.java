package com.microservices.user.application.usecaseimpl.integration;

import com.microservices.user.infrastructure.database.AbstractIntegrationTest;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.DeleteByIdUseCase;
import com.microservices.user.domain.ports.outbound.UserRepositoryPort;
import com.microservices.user.utils.UserTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class DeleteByIdUseCaseIntegrationTest extends AbstractIntegrationTest {

    private final UserRepositoryPort userRepositoryPort;
    private final DeleteByIdUseCase deleteByIdUseCase;

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
    @DisplayName("Integration: Delete User by Id")
    void deleteUserByIdTest() {
        User createdUser = userRepositoryPort.createUser(user);

        assertTrue(userRepositoryPort.findById(createdUser.getId()).isPresent());
        assertDoesNotThrow(() ->
                deleteByIdUseCase.deleteUser(createdUser.getId()));
        assertFalse(userRepositoryPort.findById(createdUser.getId()).isPresent());

    }

    @Test
    @DisplayName("Integration: 'Delete User by Id' throws exception if User's id isn't found.")
    void failedToDeleteUserNotFoundTest() {

        String userId = "1";

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                deleteByIdUseCase.deleteUser(userId));

        assertNotNull(exception);
        assertEquals("User's id not found!", exception.getDetail());
    }
}
