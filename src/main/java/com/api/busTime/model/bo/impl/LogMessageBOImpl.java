package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.dao.LogMessageDAO;
import com.api.busTime.model.dtos.CreateLogMessageDTO;
import com.api.busTime.model.dtos.LogMessageDTO;
import com.api.busTime.model.entities.LogMessage;
import com.api.busTime.utils.FormatEntityToDTO;
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

    @Override
    public ResponseEntity<LogMessageDTO> create(CreateLogMessageDTO createLogMessageDTO) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("Brazil/East"));
        LogMessage logMessage = new LogMessage();
        BeanUtils.copyProperties(createLogMessageDTO, logMessage);
        logMessage.setTime(time);
        return ResponseEntity.ok(FormatEntityToDTO.formatEntityToDto(logMessageDAO.save(logMessage), LogMessageDTO::new));
    }

    @Override
    public ResponseEntity<LogMessageDTO> getById(Long logId) {
        LogMessage logMessage = logMessageDAO.getById(logId);
        return ResponseEntity.ok(FormatEntityToDTO.formatEntityToDto(logMessage, LogMessageDTO::new));
    }

    @Override
    public ResponseEntity<Page<LogMessageDTO>> getAllLogs(Pageable pageable) {
        return ResponseEntity.ok(FormatEntityToDTO.formatPageEntityToPageDto(logMessageDAO.listForDate(pageable), LogMessageDTO::new));
    }
}
