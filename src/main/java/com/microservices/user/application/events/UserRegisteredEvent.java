package com.microservices.user.application.events;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRegisteredEvent {

    private String id;
    private String email;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
}