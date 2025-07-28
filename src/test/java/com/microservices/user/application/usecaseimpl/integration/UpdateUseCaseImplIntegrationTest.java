package com.microservices.user.application.usecaseimpl.integration;

import com.microservices.user.AbstractIntegrationTest;
import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserNotFoundException;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.UpdateUseCase;
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
class UpdateUseCaseImplIntegrationTest extends AbstractIntegrationTest {

    private final UpdateUseCase updateUseCase;
    private final UserRepositoryPort userRepositoryPort;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.user.setId(null);
        this.userDto = UserTestFactory.createUserDto();
    }

    @AfterEach
    void cleanup() {
        userRepositoryPort.deleteAll();
    }

    @Test
    @DisplayName("Integration: Update User")
    void updateUserTest() {

        User createdUser = userRepositoryPort.createUser(user);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());

        UserDto createdUpdatedUser = UserTestFactory.createUpdatedDto(createdUser.getId());

        UserDto updatedUser = updateUseCase.updateUser(createdUpdatedUser);

        assertNotNull(updatedUser);
        assertEquals(createdUser.getId(), updatedUser.id());
        assertEquals(createdUpdatedUser.nomeCompleto(), updatedUser.nomeCompleto());
    }

    @Test
    @DisplayName("Integration: 'Update User' throws exception if User's id isn't found.")
    void updateUserNotFoundTest() {

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> updateUseCase.updateUser(userDto));

        assertNotNull(exception);
        assertEquals("User's id not found!", exception.getDetail());
        assertEquals(HttpStatusCode.valueOf(204), exception.getHttpStatusCode());
    }
}
