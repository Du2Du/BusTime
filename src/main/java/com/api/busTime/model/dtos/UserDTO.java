package com.api.busTime.model.dtos;

import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.PermissionsGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class UserDTO {
    @NotBlank
    private String name;
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", permissionsGroup=" + permissionsGroup.getName() +
                '}';
    }
}
