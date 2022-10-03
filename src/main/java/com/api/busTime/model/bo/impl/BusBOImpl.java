package com.api.busTime.model.bo.impl;

import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.bo.BusBO;
import com.api.busTime.model.bo.LogMessageBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.BusDAO;
import com.api.busTime.model.dtos.*;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.utils.RequisitionStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BusBOImpl implements BusBO {

    @Autowired
    private BusDAO busDAO;

    @Autowired
    private UsersBO userBO;

    @Autowired
    private LogMessageBO logMessageBO;

    //Método que cria o onibus
    @Override
    public ResponseEntity<BusDTO> create(CreateBusDTO createBusDTO) {
        Bus bus = new Bus();
        BusDTO busReturn = new BusDTO();
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO();


        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(createBusDTO, bus);
        BeanUtils.copyProperties(this.busDAO.save(bus), busReturn);
        createLogMessageDTO.setMessage("Um usuário criou um ônibus");
        createLogMessageDTO.setRequisitionStatus(RequisitionStatus.SUCCESS.getValue());
        createLogMessageDTO.setForm(createBusDTO.toString());
        createLogMessageDTO.setMethod("POST");
        createLogMessageDTO.setUrl("/api/v1/bus");

        return ResponseEntity.ok(busReturn);
    }

    //Método que atualiza as informações do onibus
    @Override
    public ResponseEntity<BusDTO> update(Long busId, UpdateBusDTO updateBusDTO) {
        UserDTO user = userBO.me();
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO();
        createLogMessageDTO.setForm(updateBusDTO.toString());
        createLogMessageDTO.setMethod("PUT");
        createLogMessageDTO.setUrl("/api/v1/bus");

        //Verificando se existe algum onibus com esse id
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        BusDTO busReturn = new BusDTO();

        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(user.getId())) {
            createLogMessageDTO.setMessage("Um usuário tentou atualizar um ônibus");
            createLogMessageDTO.setRequisitionStatus(RequisitionStatus.FAILURE.getValue());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(updateBusDTO, bus);

        BeanUtils.copyProperties(this.busDAO.save(bus), busReturn);

        createLogMessageDTO.setMessage("Um usuário atualizou um ônibus");
        createLogMessageDTO.setRequisitionStatus(RequisitionStatus.SUCCESS.getValue());


        return ResponseEntity.ok(busReturn);
    }

    //Método que irá deletar o onibus
    @Override
    public ResponseEntity<String> delete(Long busId) {
        UserDTO user = userBO.me();
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO();
        createLogMessageDTO.setForm(user.toString() + " - Bus ID: " + busId);
        createLogMessageDTO.setMethod("DELETE");
        createLogMessageDTO.setUrl("/api/v1/bus/{id}");

        //Verificando se existe algum onibus com esse id
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));

        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(user.getId())) {
            createLogMessageDTO.setMessage("Um usuário tentou deletar um ônibus");
            createLogMessageDTO.setRequisitionStatus(RequisitionStatus.FAILURE.getValue());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        this.busDAO.delete(bus);

        createLogMessageDTO.setMessage("Um usuário deletou um ônibus");
        createLogMessageDTO.setRequisitionStatus(RequisitionStatus.SUCCESS.getValue());

        return ResponseEntity.ok("Ônibus deletado com sucesso");
    }

    //Método que lista o onibus por id
    @Override
    public ResponseEntity<BusDTO> getById(Long busId) {
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO();
        createLogMessageDTO.setForm(String.valueOf(busId));
        createLogMessageDTO.setMethod("GET");
        createLogMessageDTO.setUrl("/api/v1/bus/{id}");

        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> {
            createLogMessageDTO.setMessage("Um usuário tentou ler um ônibus pelo id");
            createLogMessageDTO.setRequisitionStatus(RequisitionStatus.FAILURE.getValue());
            return new ResourceNotFoundException("Ônibus não encontrado.");
        });
        BusDTO busReturn = new BusDTO();
        BeanUtils.copyProperties(bus, busReturn);

        createLogMessageDTO.setMessage("Um usuário leu um ônibus pelo id");
        createLogMessageDTO.setRequisitionStatus(RequisitionStatus.SUCCESS.getValue());

        return ResponseEntity.ok(busReturn);
    }

    //Método que retorna os onibus criados pelo usuario
    @Override
    public ResponseEntity<List<BusDTO>> findBusForUser(Long userId) {
        UserDTO user;
        List<BusDTO> busReturn;
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO();
        createLogMessageDTO.setMethod("GET");
        createLogMessageDTO.setUrl("/api/v1/bus/user/{id}");

        user = userBO.me();
        createLogMessageDTO.setForm(user.toString() + " USER id: " + userId);

        if (!userId.equals(user.getId())) {
            createLogMessageDTO.setMessage("Um usuário tentou achar um ônibus pelo id do usuario");
            createLogMessageDTO.setRequisitionStatus(RequisitionStatus.FAILURE.getValue());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        busReturn = this.busDAO.listBusForUserId(userId).stream().map((bus) -> {

            BusDTO busDTO = new BusDTO();
            BeanUtils.copyProperties(bus, busDTO);
            return busDTO;
        }).collect(Collectors.toList());
        
        createLogMessageDTO.setMessage("Um usuário buscou um ônibus pelo id do usuário");
        createLogMessageDTO.setRequisitionStatus(RequisitionStatus.SUCCESS.getValue());
        
        return ResponseEntity.ok(busReturn);
    }

    //Método que lista os onibus pela linha
    @Override
    public Page<BusDTO> listForLine(String line, Pageable pageable) {
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO();
        createLogMessageDTO.setMethod("GET");
        createLogMessageDTO.setUrl("/api/v1/bus/line&line");
        
        Page<Bus> bus = this.busDAO.listBusForLine(line, pageable);
        Page<BusDTO> busReturn;

        createLogMessageDTO.setForm(String.valueOf(line));
        
        busReturn = bus.map((page) -> {
            BusDTO busDTO = new BusDTO();
            BeanUtils.copyProperties(page, busDTO);
            return busDTO;
        });

        createLogMessageDTO.setMessage("Um usuário buscou um ônibus pela linha dele");
        createLogMessageDTO.setRequisitionStatus(RequisitionStatus.SUCCESS.getValue());

        return busReturn;
    }

    //Método que lista os onibus paginado
    public Page<BusDTO> listAll(Pageable pageable) {
        UserDTO user = userBO.me();
        CreateLogMessageDTO createLogMessageDTO = new CreateLogMessageDTO();
        createLogMessageDTO.setMethod("GET");
        createLogMessageDTO.setUrl("/api/v1/bus");
        
        Page<BusDTO> busReturn;
        busReturn = this.busDAO.findAll(pageable).map((page) -> {
            BusDTO bus = new BusDTO();
            BeanUtils.copyProperties(page, bus);
            return bus;
        });

        createLogMessageDTO.setForm(user.toString());
        createLogMessageDTO.setMessage("Um usuário buscou todos os ônibus");
        createLogMessageDTO.setRequisitionStatus(RequisitionStatus.SUCCESS.getValue());

        return busReturn;
    }
}
