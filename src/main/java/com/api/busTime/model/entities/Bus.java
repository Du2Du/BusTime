package com.api.busTime.model.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "bus")
@EntityListeners(AuditingEntityListener.class)
public class Bus {
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
    @CreatedDate
    private Date createAt;
    @Column(nullable = false)
    private String finalRoute;
    @Column(nullable = false)
    private Integer busNumber;
    @Column(nullable = false)
    private Long idUserAdmin;
    @Column(nullable = false)
    private Long busSavedQuantity;

}
