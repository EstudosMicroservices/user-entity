package com.microservices.user.domain.command;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateUserCommand(
    String id,
    String email,
    String nomeCompleto,
    LocalDate dataNascimento,
    String rua,
    String bairro,
    String cidade,
    String estado
) {
    // O Record já fornece construtores, getters, equals, hashCode e toString.
}
