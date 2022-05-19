package com.api.busTime.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bus")
public class BusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String line;
    @Column(nullable = false)
    private String hour;
    @Column(nullable = false)
    private Float ticketPrice;
    @Column(nullable = false)
    private String inicialRoute;
    @Column(nullable = false)
    private String finalRoute;
    @Column(nullable = false)
    private Integer busNumber;
    @Column(nullable = false)
    private Long idUserAdmin;

}
