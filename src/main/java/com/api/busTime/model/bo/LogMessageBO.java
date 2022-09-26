package com.api.busTime.model.bo;

import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.dtos.LogMessageDTO;
import org.springframework.http.ResponseEntity;

public interface LogMessageBO {

    ResponseEntity<LogMessageDTO> create(CreateLogMessageDTO createLogMessageDTO);

    ResponseEntity<LogMessageDTO> getById(Long logId);
    
}
