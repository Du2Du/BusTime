package com.api.busTime.model.dtos;

import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.PermissionsGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
    @NotBlank
    private String name;
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
    @NotNull
    private Long id;

    @NotNull
    private PermissionsGroup permissionsGroup;

    @NotNull
    private List<Bus> favoriteBus;
}