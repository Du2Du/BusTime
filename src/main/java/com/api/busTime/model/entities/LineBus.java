package com.api.busTime.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "line_bus")
public class LineBus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String lineName;
}
