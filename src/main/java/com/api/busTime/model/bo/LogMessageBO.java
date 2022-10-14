package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.dtos.LogMessageDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LogMessageBO {

    ResponseEntity<LogMessageDTO> create(CreateLogMessageDTO createLogMessageDTO);

    ResponseEntity<LogMessageDTO> getById(Long logId);
    
    ResponseEntity<List<LogMessageDTO>> getAllLogs();
    
}
