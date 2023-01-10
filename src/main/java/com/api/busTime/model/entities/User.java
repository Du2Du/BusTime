package com.api.busTime.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private Date birthDate;
    @Column(nullable = false, unique = true)
    private String cpf;
    @ManyToMany
    private List<Bus> favoriteBus;
    @Column(nullable = false)
    private boolean isUsing2FA;
    @Column
    private String secret2FACode;

    @ManyToOne
    private PermissionsGroup permissionsGroup;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
}
