package com.microservices.user.domain.user;

public record UserDto(
        String nomeCompleto,
        String email,
        String senha,
        String dataNascimento,
        String rua,
        String bairro,
        String cidade,
        String estado
) {
}
