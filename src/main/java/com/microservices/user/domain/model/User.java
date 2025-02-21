package com.microservices.user.domain.model;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class User {

    private String id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String dataNascimento;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;

}
