package com.api.busTime.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "USERS")
public class UserModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Date birth_date;
    @Column(nullable = false)
    private String cpf;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isAdmin;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
