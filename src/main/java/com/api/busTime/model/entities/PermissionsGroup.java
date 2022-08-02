package com.api.busTime.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "permissions_group")
public class PermissionsGroup {

    public PermissionsGroup() {

    }

    @Id
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private UserRoles name;

    @ManyToMany
    private List<Permission> permissionList;

}
