package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.dao.LogMessageDAO;
import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.dtos.LogMessageDTO;
import com.api.busTime.model.entities.LogMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class LogMessageBOImpl implements LogMessageBO {

    @Autowired
    LogMessageDAO logMessageDAO;

    private LogMessageDTO formatEntityToDto(LogMessage logMessage) {
        LogMessageDTO logMessageDTO = new LogMessageDTO();
        BeanUtils.copyProperties(logMessage, logMessageDTO);
        return logMessageDTO;
    }

    private Page<LogMessageDTO> formatPageEntityToPageDto(Pageable pageable) {
        Page<LogMessage> logs = logMessageDAO.listForDate(pageable);
        return logs.map(this::formatEntityToDto);
    }

    @Override
    public ResponseEntity<LogMessageDTO> create(CreateLogMessageDTO createLogMessageDTO) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("Brazil/East"));
        LogMessage logMessage = new LogMessage();
        BeanUtils.copyProperties(createLogMessageDTO, logMessage);
        logMessage.setTime(time);
        return ResponseEntity.ok(formatEntityToDto(logMessageDAO.save(logMessage)));
    }

    @Override
    public ResponseEntity<LogMessageDTO> getById(Long logId) {
        LogMessage logMessage = logMessageDAO.getById(logId);
        return ResponseEntity.ok(formatEntityToDto(logMessage));
    }

    @Override
    public ResponseEntity<Page<LogMessageDTO>> getAllLogs(Pageable pageable) {
        return ResponseEntity.ok(formatPageEntityToPageDto(pageable));
    }
}
