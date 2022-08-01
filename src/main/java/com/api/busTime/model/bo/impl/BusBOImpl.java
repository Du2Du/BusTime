package com.api.busTime.model.bo.impl;

import com.api.busTime.model.dtos.CreateBusDTO;
import com.api.busTime.model.dtos.CustomUserDetails;
import com.api.busTime.model.dtos.UpdateBusDTO;
import com.api.busTime.exceptions.Forbbiden;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.dao.BusDAO;
import com.api.busTime.model.bo.BusBO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusBOImpl implements BusBO {

    @Autowired
    private BusDAO busDAO;

    //Método que cria o onibus
    @Override
    public Bus create(CreateBusDTO createBusDTO) {

        Bus bus = new Bus();

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(createBusDTO, bus);

        return this.busDAO.save(bus);
    }

    //Método que atualiza as informações do onibus
    @Override
    public Bus update(Long busId, UpdateBusDTO updateBusDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        
        //Verificando se existe algum onibus com esse id
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        
        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(customUserDetails.getUser().getId()))
            throw new ResourceNotFoundException("Você não pode alterar esse ônibus");

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(updateBusDTO, bus);

        return this.busDAO.save(bus);
    }

    //Método que irá deletar o onibus
    @Override
    public String delete(Long busId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        //Verificando se existe algum onibus com esse id
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));

        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(customUserDetails.getUser().getId()))
            throw new Forbbiden("Você não pode deletar esse ônibus");

        this.busDAO.delete(bus);

        return "Ônibus deletado com sucesso";
    }

    //Método que lista o onibus por id
    @Override
    public Bus getById(Long busId) {
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));

        return bus;
    }

    //Método que retorna os onibus criados pelo usuario
    @Override
    public List<Bus> findBusForUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if (!userId.equals(customUserDetails.getUser().getId()))
            throw new Forbbiden("O id do usuário não bate com o logado!");
        
        return this.busDAO.listBusForUserId(userId);
    }
    
    //Método que lista os onibus pela linha
    @Override
    public Page<Bus> listForLine (String line, Pageable pageable){
        return  this.busDAO.listBusForLine(line, pageable);
    }

    //Método que lista os onibus paginado
    public Page<Bus> listAll(Pageable pageable) {
        return this.busDAO.findAll(pageable);
    }
}
