package com.microservices.user.application.usecaseimpl.integration.store;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.user.store.CreateUserFromEventPort;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserCreatePortImplIntegrationTest extends AbstractIntegrationTest {

    private final UserRepositoryPort userRepositoryPort;
    private final CreateUserFromEventPort createUserFromEventPort;

    private final PasswordEncoder passwordEncoder;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.userDto = UserTestFactory.createUserDtoWithoutId();
    }

    @AfterEach
    void cleanup() {
        userRepositoryPort.deleteAll();
    }

    @Test
    @DisplayName("Integration: Create user")
    void createUserTest() {

        User createdUser = userRepositoryPort.createUser(user);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals("teste@teste.com", createdUser.getEmail());

        Optional<User> savedUserOptional = userRepositoryPort.findUserByEmail("teste@teste.com");

        assertTrue(savedUserOptional.isPresent());
    }

}
