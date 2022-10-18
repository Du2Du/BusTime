package com.api.busTime.model.bo.impl;

import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.bo.LineBusBO;
import com.api.busTime.model.dao.LineBusDAO;
import com.api.busTime.model.dtos.CreateLineBusDTO;
import com.api.busTime.model.dtos.LineBusDTO;
import com.api.busTime.model.dtos.UpdateLineBusDTO;
import com.api.busTime.model.entities.LineBus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LineBusBOImpl implements LineBusBO {

    @Autowired
    private LineBusDAO lineDAO;

    @Override
    public ResponseEntity<LineBusDTO> create(CreateLineBusDTO lineBusDTO) {
        Optional<LineBus> lineBus = this.lineDAO.findLineForName(lineBusDTO.getLineName());

        if (lineBus.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        LineBus lineBus1 = new LineBus();
        LineBusDTO lineBusReturn = new LineBusDTO();

        lineBusDTO.setSavedQuantity(0L);

        BeanUtils.copyProperties(lineBusDTO, lineBus1);
        BeanUtils.copyProperties(this.lineDAO.save(lineBus1), lineBusReturn);
        return ResponseEntity.ok(lineBusReturn);
    }

    @Override
    public ResponseEntity<LineBusDTO> update(Long lineId, UpdateLineBusDTO updateBusDTO) {
        LineBus lineBus = this.lineDAO.findById(lineId).orElseThrow(() -> new ResourceNotFoundException("Linha não encontrada."));

        BeanUtils.copyProperties(updateBusDTO, lineBus);

        LineBusDTO lineBusDTO = new LineBusDTO();
        BeanUtils.copyProperties(this.lineDAO.save(lineBus), lineBusDTO);


        return ResponseEntity.ok(lineBusDTO);
    }

    @Override
    public ResponseEntity<LineBusDTO> getById(Long lineId) {
        LineBus lineBus = this.lineDAO.findById(lineId).orElseThrow(() -> new ResourceNotFoundException("Linha não encontrada."));

        LineBusDTO lineBusDTO = new LineBusDTO();
        BeanUtils.copyProperties(lineBus, lineBusDTO);

        return ResponseEntity.ok(lineBusDTO);
    }

    @Override
    public ResponseEntity<LineBusDTO> getLineByName(String lineName) {
        Optional<LineBus> lineBus = this.lineDAO.findLineForName(lineName);

        if (!lineBus.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        LineBusDTO lineBusDTO = new LineBusDTO();

        BeanUtils.copyProperties(lineBus, lineBusDTO);

        return ResponseEntity.ok(lineBusDTO);
    }

    //Método que retorna a quantidade de onibus criados em um mes
    @Override
    public ResponseEntity<List<LineBusDTO>> listBusStatistics() {
        List<LineBus> lineBus = this.lineDAO.findAllOrdenable();


        List<LineBusDTO> lineBusDTOS = lineBus.stream().map(bus -> {
            LineBusDTO lineBusDTO = new LineBusDTO();

            lineBusDTO.setSavedQuantity(bus.getSavedQuantity());
            lineBusDTO.setLineName(bus.getLineName().toUpperCase());

            return lineBusDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(lineBusDTOS);
    }
}
