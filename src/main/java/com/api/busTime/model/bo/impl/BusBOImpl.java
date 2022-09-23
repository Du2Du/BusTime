package com.api.busTime.model.bo.impl;

import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.bo.BusBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.BusDAO;
import com.api.busTime.model.dtos.BusDTO;
import com.api.busTime.model.dtos.CreateBusDTO;
import com.api.busTime.model.dtos.UpdateBusDTO;
import com.api.busTime.model.dtos.UserDTO;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.utils.LoggerUtil;
import com.api.busTime.utils.RequisitionStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private LoggerUtil loggerUtil;

    //Método que cria o onibus
    @Override
    public ResponseEntity<BusDTO> create(CreateBusDTO createBusDTO) {
        Bus bus = new Bus();
        BusDTO busReturn = new BusDTO();

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(createBusDTO, bus);
        BeanUtils.copyProperties(this.busDAO.save(bus), busReturn);

        return ResponseEntity.ok(busReturn);
    }

    //Método que atualiza as informações do onibus
    @Override
    public ResponseEntity<BusDTO> update(Long busId, UpdateBusDTO updateBusDTO) {
        UserDTO user = userBO.me();

        //Verificando se existe algum onibus com esse id
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        BusDTO busReturn = new BusDTO();

        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(user.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(updateBusDTO, bus);

        BeanUtils.copyProperties(this.busDAO.save(bus), busReturn);

        return ResponseEntity.ok(busReturn);
    }

    //Método que irá deletar o onibus
    @Override
    public ResponseEntity<String> delete(Long busId) {
        UserDTO user = userBO.me();

        //Verificando se existe algum onibus com esse id
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));

        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(user.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        this.busDAO.delete(bus);

        return ResponseEntity.ok("Ônibus deletado com sucesso");
    }

    //Método que lista o onibus por id
    @Override
    public ResponseEntity<BusDTO> getById(Long busId) {
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> {
            loggerUtil.registerLogger("GET", "/api/v1/bus", RequisitionStatus.FAILURE, "tentou buscar ônibus pelo ID", "com o ID:" + busId);
            return new ResourceNotFoundException("Ônibus não encontrado.");
        });
        BusDTO busReturn = new BusDTO();
        BeanUtils.copyProperties(bus, busReturn);

        loggerUtil.registerLogger("GET", "/api/v1/bus", RequisitionStatus.SUCCESS, "buscou ônibus pelo ID", "com o ID:" + busId);
        return ResponseEntity.ok(busReturn);
    }

    //Método que retorna os onibus criados pelo usuario
    @Override
    public ResponseEntity<List<BusDTO>> findBusForUser(Long userId) {
        UserDTO user;
        List<BusDTO> busReturn;

        try {
            user = userBO.me();

            if (!userId.equals(user.getId())) {
                loggerUtil.registerLogger("GET", "/api/v1/bus/user", RequisitionStatus.FAILURE, "buscou ônibus pelo ID do usuario logado",
                        "Usuário \nEmail:" + user.getEmail() + "\n ID:" + user.getId() + " com o ID:" + userId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            loggerUtil.registerLogger("GET", "/api/v1/bus/user", RequisitionStatus.SUCCESS, "buscou ônibus pelo ID do usuario logado",
                    "Usuário \nEmail:" + user.getEmail() + "\n ID:" + user.getId() + " com o ID:" + userId);

            busReturn = this.busDAO.listBusForUserId(userId).stream().map((bus) -> {

                BusDTO busDTO = new BusDTO();
                BeanUtils.copyProperties(bus, busDTO);
                return busDTO;
            }).collect(Collectors.toList());
            
            return ResponseEntity.ok(busReturn);

        } catch (NoSuchElementException elementException) {
            loggerUtil.registerLogger("GET", "/api/v1/bus/user", RequisitionStatus.FAILURE, "buscou ônibus pelo ID do usuario logado", "com o ID:" + userId);

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    //Método que lista os onibus pela linha
    @Override
    public Page<BusDTO> listForLine(String line, Pageable pageable) {
        loggerUtil.registerLogger("GET", "/api/v1/bus/line", RequisitionStatus.SUCCESS, "buscou ônibus pela linha", "com a linha:" + line);
        Page<Bus> bus = this.busDAO.listBusForLine(line, pageable);
        Page<BusDTO> busReturn;
        
        busReturn = bus.map((page) -> {
            BusDTO busDTO = new BusDTO();
            BeanUtils.copyProperties(page, busDTO);
            return busDTO;
        });
        
        return busReturn;
    }

    //Método que lista os onibus paginado
    public Page<BusDTO> listAll(Pageable pageable) {

        Page<BusDTO> busReturn;
        busReturn = this.busDAO.findAll(pageable).map((page) -> {
            BusDTO bus = new BusDTO();
            BeanUtils.copyProperties(page, bus);
            return bus;
        });
        
        return busReturn;
    }
}
