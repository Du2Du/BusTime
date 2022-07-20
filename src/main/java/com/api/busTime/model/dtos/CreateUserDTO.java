package com.api.busTime.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class CreateUserDTO {

    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 6, message = "A senha deve ter mais de 6 caracteres.")
    private String password;
    @NotBlank
    @CPF
    private String cpf;
    @NotBlank
    @Email
    private String email;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

}
