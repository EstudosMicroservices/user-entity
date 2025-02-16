package com.microservices.user.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "Nome_Completo", nullable = false)
    private String nomeCompleto;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Senha", nullable = false)
    private String senha;

    @Column(name = "Data_Nascimento", nullable = false)
    private String dataNascimento;

    @Column(name = "Rua", nullable = false)
    private String rua;

    @Column(name="Bairro", nullable = false)
    private String bairro;

    @Column(name = "Cidade", nullable = false)
    private String cidade;

    @Column(name = "Estado", nullable = false)
    private String estado;

}
