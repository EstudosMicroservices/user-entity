package com.microservices.user.domain.dto;

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
