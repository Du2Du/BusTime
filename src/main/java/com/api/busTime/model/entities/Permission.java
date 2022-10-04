package com.api.busTime.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "permissions")
public class Permission {

    public Permission() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String permissionName;

    @Column(nullable = false)
    private String permissionId;
}
