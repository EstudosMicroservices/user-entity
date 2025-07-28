package com.microservices.user.domain.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    private String id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;

}
