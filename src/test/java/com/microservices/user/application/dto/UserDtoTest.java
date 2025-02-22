package com.microservices.user.application.dto;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoTest {

    @Test
    public void testFields(){
        LocalDate dataNascimento = LocalDate.of(2001, 3, 25);
        UserDto userDto = new UserDto(
                "Nome Teste",
                "teste@teste.com",
                "Senha Teste",
                dataNascimento,
                "Rua Teste",
                "Bairro Teste",
                "Cidade Teste",
                "Estado Teste");

        assertThat(userDto.nomeCompleto()).isEqualTo("Nome Teste");
        assertThat(userDto.email()).isEqualTo("teste@teste.com");
        assertThat(userDto.senha()).isEqualTo("Senha Teste");
        assertThat(userDto.dataNascimento()).isEqualTo(LocalDate.of(2001, 3, 25));
        assertThat(userDto.rua()).isEqualTo("Rua Teste");
        assertThat(userDto.bairro()).isEqualTo("Bairro Teste");
        assertThat(userDto.cidade()).isEqualTo("Cidade Teste");
        assertThat(userDto.estado()).isEqualTo("Estado Teste");

    }

}
