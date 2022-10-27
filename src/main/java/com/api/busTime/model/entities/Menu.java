package com.api.busTime.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {

    public Menu(){
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String iconName;
    @Column(nullable = false)
    private String menuName;
    @Column(nullable = false)
    private String permissionName;
}
