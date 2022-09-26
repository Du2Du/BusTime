package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.dao.LogMessageDAO;
import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.dtos.LogMessageDTO;
import com.api.busTime.model.entities.LogMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class LogMessageBOImpl implements LogMessageBO {

    @Autowired
    LogMessageDAO logMessageDAO;

    @Override
    public ResponseEntity<LogMessageDTO> create(CreateLogMessageDTO createLogMessageDTO) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("Brazil/East"));

        LogMessageDTO logMessageDTO = new LogMessageDTO();
        LogMessage logMessage = new LogMessage();

        BeanUtils.copyProperties(createLogMessageDTO, logMessage);
        logMessage.setTime(time);

        BeanUtils.copyProperties(logMessageDAO.save(logMessage), logMessageDTO);
        return ResponseEntity.ok(logMessageDTO);
    }

    @Override
    public ResponseEntity<LogMessageDTO> getById(Long logId) {
        LogMessage logMessage = logMessageDAO.getById(logId);

        LogMessageDTO logMessageDTO = new LogMessageDTO();

        BeanUtils.copyProperties(logMessage, logMessageDTO);

        return ResponseEntity.ok(logMessageDTO);
    }
}
