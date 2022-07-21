package com.api.busTime.model.bo.impl;

import com.api.busTime.model.dtos.CreateBusDTO;
import com.api.busTime.model.dtos.CustomUserDetails;
import com.api.busTime.model.dtos.UpdateBusDTO;
import com.api.busTime.exceptions.Forbbiden;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.entities.BusModel;
import com.api.busTime.model.dao.BusRepository;
import com.api.busTime.model.bo.BusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;
    
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private TaskDefinitionBeanImpl taskDefinitionBean;
    
    @Autowired
    private TaskSchedulingServiceImpl taskSchedulingService;

    //Método que cria o onibus
    @Override
    public BusModel create(CreateBusDTO createBusDTO) {
        UserModel customUserDetails = userService.me();
        EmailTaskDefinition emailTaskDefinition = EmailTaskDefinition.builder().emailTo(customUserDetails.getEmail()).emailFrom("busTimeEquip@gmail.com").build();
        
        BusModel bus = new BusModel();

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(createBusDTO, bus);
        
//        taskDefinitionBean.setTaskDefinition();

        return this.busRepository.save(bus);
    }

    //Método que atualiza as informações do onibus
    @Override
    public BusModel update(Long busId, UpdateBusDTO updateBusDTO) {
        UserModel customUserDetails = userService.me();

        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!updateBusDTO.getIdUserAdmin().equals(customUserDetails.getId()))
            throw new ResourceNotFoundException("O id do usuário não bate com o logado!");

        //Verificando se existe algum onibus com esse id
        BusModel bus = this.busRepository.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        
        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(customUserDetails.getUser().getId()))
            throw new ResourceNotFoundException("Você não pode alterar esse ônibus");

        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(updateBusDTO, bus);

        return this.busRepository.save(bus);
    }

    //Método que irá deletar o onibus
    @Override
    public String delete(Long busId) {
        UserModel customUserDetails = userService.me();

        //Verificando se existe algum onibus com esse id
        BusModel bus = this.busRepository.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));

        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(customUserDetails.getId()))
            throw new ResourceNotFoundException("Você não pode deletar esse ônibus");

        this.busRepository.delete(bus);

        return "Ônibus deletado com sucesso";
    }

    //Método que lista o onibus por id
    @Override
    public BusModel getById(Long busId) {
        BusModel bus = this.busRepository.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));

        return bus;
    }

    //Método que retorna os onibus criados pelo usuario
    @Override
    public List<BusModel> findBusForUser(Long userId) {
        UserModel customUserDetails = userService.me();

        if (!userId.equals(customUserDetails.getId()))
            throw new ResourceNotFoundException("O id do usuário não bate com o logado!");
        
        return this.busRepository.listBusForUserId(userId);
    }
    
    //Método que lista os onibus pela linha
    @Override
    public Page<BusModel> listForLine (String line, Pageable pageable){
        return  this.busRepository.listBusForLine(line, pageable);
    }

    //Método que lista os onibus paginado
    public Page<BusModel> listAll(Pageable pageable) {
        return this.busRepository.findAll(pageable);
    }
}
