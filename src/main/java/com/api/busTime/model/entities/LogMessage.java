package com.api.busTime.model.entities;

import com.api.busTime.utils.RequisitionStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "log_message")
public class LogMessage<T> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime time;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private String method;
    @Column(nullable = false)
    private String requisitionStatus;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String form;

}
