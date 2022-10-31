package com.api.busTime.model.bo.impl;

import com.api.busTime.exceptions.EntityExistsException;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.bo.LineBusBO;
import com.api.busTime.model.dao.LineBusDAO;
import com.api.busTime.model.dtos.CreateLineBusDTO;
import com.api.busTime.model.dtos.LineBusDTO;
import com.api.busTime.model.dtos.UpdateLineBusDTO;
import com.api.busTime.model.entities.LineBus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LineBusBOImpl implements LineBusBO {

    @Autowired
    private LineBusDAO lineDAO;

    private LineBusDTO formatEntityToDto(LineBus lineBus) {
        LineBusDTO lineBusDTO = new LineBusDTO();
        BeanUtils.copyProperties(lineBus, lineBusDTO);
        return lineBusDTO;
    }

    private List<LineBusDTO> formatListEntityToListDto() {
        List<LineBus> listLineBus = this.lineDAO.findAll();
        return listLineBus.stream().map(this::formatEntityToDto
        ).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<LineBusDTO> create(CreateLineBusDTO lineBusDTO) {
        Optional<LineBus> lineBus = this.lineDAO.findLineForName(lineBusDTO.getLineName());
        if (lineBus.isPresent()) throw new EntityExistsException("Essa linha de ônibus já existe!");
        LineBus lineBus1 = new LineBus();
        BeanUtils.copyProperties(lineBusDTO, lineBus1);
        return ResponseEntity.ok(formatEntityToDto(this.lineDAO.save(lineBus1)));
    }

    @Override
    public ResponseEntity<LineBusDTO> update(Long lineId, UpdateLineBusDTO updateBusDTO) {
        LineBus lineBus = this.lineDAO.findById(lineId).orElseThrow(() -> new ResourceNotFoundException("Linha não encontrada!"));
        BeanUtils.copyProperties(updateBusDTO, lineBus);
        return ResponseEntity.ok(formatEntityToDto(this.lineDAO.save(lineBus)));
    }

    @Override
    public ResponseEntity<LineBusDTO> getLineByName(String lineName) {
        LineBus lineBus = this.lineDAO.findLineForName(lineName).orElseThrow(() -> new ResourceNotFoundException("Linha não encontrada!"));
        return ResponseEntity.ok(formatEntityToDto(lineBus));
    }

    @Override
    public ResponseEntity<List<LineBusDTO>> listAll() {
        return ResponseEntity.ok(formatListEntityToListDto());
    }
}
