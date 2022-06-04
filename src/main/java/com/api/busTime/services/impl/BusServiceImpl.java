package com.api.busTime.services.impl;

import com.api.busTime.dtos.CreateBusDTO;
import com.api.busTime.dtos.UpdateBusDTO;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.models.BusModel;
import com.api.busTime.repositories.BusRepository;
import com.api.busTime.services.BusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    //Método que cria o onibus
    @Override
    public BusModel create(CreateBusDTO createBusDTO, Long userId) {
    
        BusModel bus = new BusModel();

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(createBusDTO, bus);

        return this.busRepository.save(bus);
    }

    //Método que atualiza as informações do onibus
    @Override
    public BusModel update(Long busId, UpdateBusDTO updateBusDTO, Long userId) {
        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!updateBusDTO.getIdUserAdmin().equals(userId))
            throw new ResourceNotFoundException("O id do usuário não bate com o logado!");

        //Verificando se existe algum onibus com esse id
        BusModel bus = this.busRepository.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(updateBusDTO, bus);

        return this.busRepository.save(bus);
    }

    //Método que irá deletar o onibus
    @Override
    public String delete(Long busId, Long userId){

        //Verificando se existe algum onibus com esse id
        BusModel bus = this.busRepository.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));

        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(userId))
            throw new ResourceNotFoundException("Você não pode deletar esse ônibus");
        
         this.busRepository.delete(bus);

        return "Ônibus deletado com sucesso";
    }
    
    //Método que lista o onibus por id
    public BusModel getById(Long busId){
        BusModel bus = this.busRepository.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        
        return bus;
    }
    
    //Método que lista os onibus paginado
    public Page<BusModel> listAll(Pageable pageable){
        return this.busRepository.findAll(pageable);
    }
}
