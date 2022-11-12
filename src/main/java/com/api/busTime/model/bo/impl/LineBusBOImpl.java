package com.api.busTime.model.bo.impl;

import com.api.busTime.exceptions.EntityExistsException;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.bo.LineBusBO;
import com.api.busTime.model.dao.LineBusDAO;
import com.api.busTime.model.dtos.CreateLineBusDTO;
import com.api.busTime.model.dtos.LineBusDTO;
import com.api.busTime.model.dtos.UpdateLineBusDTO;
import com.api.busTime.model.entities.LineBus;
import com.api.busTime.utils.FormatEntityToDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineBusBOImpl implements LineBusBO {

    @Autowired
    private LineBusDAO lineDAO;

    @Override
    public ResponseEntity<LineBusDTO> create(CreateLineBusDTO lineBusDTO) {
        Optional<LineBus> lineBus = this.lineDAO.findLineForName(lineBusDTO.getLineName());
        if (lineBus.isPresent()) throw new EntityExistsException("Essa linha de ônibus já existe!");
        LineBus lineBus1 = new LineBus();
        BeanUtils.copyProperties(lineBusDTO, lineBus1);
        return ResponseEntity.ok(FormatEntityToDTO.formatEntityToDto(this.lineDAO.save(lineBus1), LineBusDTO::new));
    }

    @Override
    public ResponseEntity<LineBusDTO> update(Long lineId, UpdateLineBusDTO updateBusDTO) {
        LineBus lineBus = this.lineDAO.findById(lineId).orElseThrow(() -> new ResourceNotFoundException("Linha não encontrada!"));
        BeanUtils.copyProperties(updateBusDTO, lineBus);
        return ResponseEntity.ok(FormatEntityToDTO.formatEntityToDto(this.lineDAO.save(lineBus), LineBusDTO::new));
    }

    @Override
    public ResponseEntity<LineBusDTO> getLineByName(String lineName) {
        LineBus lineBus = this.lineDAO.findLineForName(lineName).orElseThrow(() -> new ResourceNotFoundException("Linha não encontrada!"));
        return ResponseEntity.ok(FormatEntityToDTO.formatEntityToDto(lineBus, LineBusDTO::new));
    }

    @Override
    public ResponseEntity<List<LineBusDTO>> listAll() {
        return ResponseEntity.ok(FormatEntityToDTO.formatListEntityToListDTO(this.lineDAO.findAll(), LineBusDTO::new));
    }
}
