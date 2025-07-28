package com.microservices.user.utils;

import com.microservices.user.application.dto.UserDto;
import com.microservices.user.domain.model.User;
import com.microservices.user.infrastructure.persistence.UserEntity;

import java.time.LocalDate;

public class UserTestFactory {

    public static User createUser() {
        return User.builder()
                .id("1")
                .nomeCompleto("Nome Teste")
                .email("teste@teste.com")
                .senha("Senha Teste")
                .dataNascimento(LocalDate.of(2001, 3, 25))
                .rua("Rua Teste")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .estado("Estado Teste")
                .build();
    }


    public static UserDto createUserDto() {
        LocalDate dataNascimento = LocalDate.of(2001, 3, 25);
        return new UserDto(
                "1",
                "Nome Teste",
                "teste@teste.com",
                "Senha Teste",
                dataNascimento,
                "Rua Teste",
                "Bairro Teste",
                "Cidade Teste",
                "Estado Teste"
        );
    }

    public static UserDto createUpdatedDto(String id) {
        LocalDate dataNascimento = LocalDate.of(2001, 3, 25);
        return new UserDto(
                id,
                "Nome Updated",
                "teste@teste.com",
                "Senha Teste",
                dataNascimento,
                "Rua Teste",
                "Bairro Teste",
                "Cidade Teste",
                "Estado Teste"
        );
    }

    public static UserDto createUserDtoWithoutId() {
        LocalDate dataNascimento = LocalDate.of(2001, 3, 25);
        return new UserDto(
                null,
                "Nome Teste",
                "teste@teste.com",
                "Senha Teste",
                dataNascimento,
                "Rua Teste",
                "Bairro Teste",
                "Cidade Teste",
                "Estado Teste"
        );
    }

    public static UserDto createUserDtoTwo() {
        LocalDate dataNascimento = LocalDate.of(2001, 3, 25);
        return new UserDto(
                "2",
                "Nome Teste",
                "teste@teste.com",
                "Senha Teste",
                dataNascimento,
                "Rua Teste",
                "Bairro Teste",
                "Cidade Teste",
                "Estado Teste"
        );
    }

    public static UserDto createUserDtoEncoded() {
        LocalDate dataNascimento = LocalDate.of(2001, 3, 25);
        return new UserDto(
                "1",
                "Nome Teste",
                "teste@teste.com",
                "Senha Teste Encoded",
                dataNascimento,
                "Rua Teste",
                "Bairro Teste",
                "Cidade Teste",
                "Estado Teste"
        );
    }

    public static UserEntity createUserEntity() {
        return UserEntity.builder()
                .id("1")
                .nomeCompleto("Nome Teste")
                .email("teste@teste.com")
                .senha("Senha Teste")
                .dataNascimento(LocalDate.of(2001, 3, 25))
                .rua("Rua Teste")
                .bairro("Bairro Teste")
                .cidade("Cidade Teste")
                .estado("Estado Teste")
                .build();
    }
}
