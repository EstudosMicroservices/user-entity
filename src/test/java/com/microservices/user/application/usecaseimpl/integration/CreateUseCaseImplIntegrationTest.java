package com.microservices.user.application.usecaseimpl.integration;

import com.microservices.user.AbstractIntegrationTest;
import com.microservices.user.application.dto.UserDto;
import com.microservices.user.application.exceptions.user.UserAlreadyExistsException;
import com.microservices.user.domain.model.User;
import com.microservices.user.domain.ports.inbound.CreateUseCase;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CreateUseCaseImplIntegrationTest extends AbstractIntegrationTest {

    private final CreateUseCase createUseCase;

    private final UserRepositoryPort userRepositoryPort;

    private final PasswordEncoder passwordEncoder;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setup() {
        this.user = UserTestFactory.createUser();
        this.user.setId(null);
        this.userDto = UserTestFactory.createUserDtoWithoutId();
    }

    @AfterEach
    void cleanup() {
        userRepositoryPort.deleteAll();
    }

    @Test
    @DisplayName("Integration: Create user")
    void createUserTest() {

        UserDto createdUserDto = createUseCase.createUser(userDto);

        assertNotNull(createdUserDto);
        assertNotNull(createdUserDto.id());
        assertEquals("teste@teste.com", createdUserDto.email());

        Optional<User> savedUserOptional = userRepositoryPort.findUserByEmail("teste@teste.com");

        assertTrue(savedUserOptional.isPresent());

        User savedUser = savedUserOptional.get();
        assertTrue(passwordEncoder.matches("Senha Teste", savedUser.getSenha()));
    }

    @Test
    @DisplayName("Integration: 'Create user' throws exception if user already exists.")
    void failedToCreateUserAlreadyExistsTest() {
        createUseCase.createUser(userDto);

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> createUseCase.createUser(userDto)
        );


        assertNotNull(exception);
        assertEquals("Email already exists!", exception.getDetail());
        assertEquals(HttpStatusCode.valueOf(409), exception.getHttpStatusCode());
    }

}
