package com.microservices.user.domain.model;

import com.microservices.user.utils.UserTestFactory;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class UserTest {

    @Test
    public void testFields(){
        User user = UserTestFactory.createUser();

        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getNomeCompleto()).isEqualTo("Nome Teste");
        assertThat(user.getEmail()).isEqualTo("teste@teste.com");
        assertThat(user.getSenha()).isEqualTo("Senha Teste");
        assertThat(user.getDataNascimento()).isEqualTo("25-03-2001");
        assertThat(user.getRua()).isEqualTo("Rua Teste");
        assertThat(user.getBairro()).isEqualTo("Bairro Teste");
        assertThat(user.getCidade()).isEqualTo("Cidade Teste");
        assertThat(user.getEstado()).isEqualTo("Estado Teste");
    }



}
