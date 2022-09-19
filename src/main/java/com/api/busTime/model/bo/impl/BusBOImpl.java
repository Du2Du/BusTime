package com.api.busTime.model.bo.impl;

import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.UserDAO;
import com.api.busTime.model.dtos.CreateBusDTO;
import com.api.busTime.model.dtos.CustomUserDetails;
import com.api.busTime.model.dtos.UpdateBusDTO;
import com.api.busTime.exceptions.Forbbiden;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.dao.BusDAO;
import com.api.busTime.model.bo.BusBO;
import com.api.busTime.model.entities.User;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusBOImpl implements BusBO {

    @Autowired
    private BusDAO busDAO;

    @Autowired
    private UsersBO userBO;


    //Método que cria o onibus
    @Override
    public ResponseEntity<Bus> create(CreateBusDTO createBusDTO) {
        Bus bus = new Bus();

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(createBusDTO, bus);

        return ResponseEntity.ok(this.busDAO.save(bus));
    }

    //Método que atualiza as informações do onibus
    @Override
    public ResponseEntity<Bus> update(Long busId, UpdateBusDTO updateBusDTO) {
        User user = userBO.me();
        
        //Verificando se existe algum onibus com esse id
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));

        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(user.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(updateBusDTO, bus);

        return ResponseEntity.ok(this.busDAO.save(bus));
    }

    //Método que irá deletar o onibus
    @Override
    public ResponseEntity<String> delete(Long busId) {
        User user = userBO.me();
        
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
    public ResponseEntity<Bus> getById(Long busId) {
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));

        return ResponseEntity.ok(bus);
    }

    //Método que retorna os onibus criados pelo usuario
    @Override
    public ResponseEntity<List<Bus>> findBusForUser(Long userId) {
        User user = userBO.me();
        
        if (!userId.equals(user.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(this.busDAO.listBusForUserId(userId));
    }

    //Método que lista os onibus pela linha
    @Override
    public Page<Bus> listForLine(String line, Pageable pageable) {
        return this.busDAO.listBusForLine(line, pageable);
    }

    //Método que lista os onibus paginado
    public Page<Bus> listAll(Pageable pageable) {
        return this.busDAO.findAll(pageable);
    }
}
