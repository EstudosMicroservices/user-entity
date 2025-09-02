package com.microservices.user.infrastructure.persistence;

import com.microservices.user.domain.model.User;
import com.microservices.user.utils.UserTestFactory;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
public class UserEntityTest {

    @Test
    public void testFields() {
        User user = UserTestFactory.createUser();

        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getNomeCompleto()).isEqualTo("Nome Teste");
        assertThat(user.getEmail()).isEqualTo("teste@teste.com");
        assertThat(user.getDataNascimento()).isEqualTo(LocalDate.of(2001, 3, 25));
        assertThat(user.getRua()).isEqualTo("Rua Teste");
        assertThat(user.getBairro()).isEqualTo("Bairro Teste");
        assertThat(user.getCidade()).isEqualTo("Cidade Teste");
        assertThat(user.getEstado()).isEqualTo("Estado Teste");
    }
}
