package com.api.busTime.model.entities;

import com.api.busTime.utils.RequisitionStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "log_message")
public class LogMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime time;
    @Column(nullable = false)
    private String method;
    @Column(nullable = true)
    private String userForm;
    @Column(nullable = false)
    private String urlStatus;
    @Column(nullable = false)
    private String url;

}
