package com.api.busTime.controllers;

import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.dtos.LogMessageDTO;
import com.api.busTime.utils.AdminVerify;
import com.api.busTime.utils.ValidationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/logs")
public class LogController {

    @Autowired
    private LogMessageBO logMessageBO;

    @AdminVerify(validationType = ValidationType.SUPER_ADMIN)
    @GetMapping
    public ResponseEntity<List<LogMessageDTO>> getAllLogs() {
        return logMessageBO.getAllLogs();
    }
}
