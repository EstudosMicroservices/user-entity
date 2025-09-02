package com.microservices.user.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservices.user.application.validation.ValidateDataNascimento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UserDto(
        @JsonIgnore
        String id,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[a-zA-Z ]{1,100}$", message = "This field must contain only letters and space.")
        String nomeCompleto,

        @NotBlank(message = "This field cannot be null or blank!")
        @Email
        String email,

        @NotNull(message = "'This field cannot be null or blank!")
        @ValidateDataNascimento(message = "This field broke the law of time and space or incorrect date format")
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataNascimento,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[a-zA-Z ]{1,50}$", message = "This field must contain only letters and space.")
        String rua,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[a-zA-Z ]{1,30}$", message = "This field must contain only letters and space.")
        String bairro,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[a-zA-Z ]{1,30}$", message = "This field must contain only letters and space.")
        String cidade,

        @NotBlank(message = "This field cannot be null or blank!")
        @Pattern(regexp = "^[a-zA-Z ]{1,30}$", message = "This field must contain only letters and space.")
        String estado
) {
}
