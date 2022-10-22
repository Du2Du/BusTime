package com.api.busTime.model.bo.impl;

import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.bo.BusBO;
import com.api.busTime.model.bo.LineBusBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.BusDAO;
import com.api.busTime.model.dao.LineBusDAO;
import com.api.busTime.model.dtos.*;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.LineBus;
import com.api.busTime.model.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BusBOImpl implements BusBO {

    @Autowired
    private BusDAO busDAO;

    @Autowired
    private LineBusBO lineBusBO;

    @Autowired
    private UsersBO userBO;

    public boolean findBusWithNumber(Integer busNumber) {
        Optional<Bus> bus = this.busDAO.listBusForNumber(busNumber);

        return !bus.isPresent();
    }

    //Método que cria o onibus
    @Override
    public ResponseEntity<BusDTO> create(CreateBusDTO createBusDTO) {
        Bus bus = new Bus();

        if (!findBusWithNumber(createBusDTO.getBusNumber()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        //Buscando se a linha de ônibus existe
        LineBusDTO lineBusDTO = this.lineBusBO.getLineByName(createBusDTO.getLineBus().getLineName()).getBody();
        LineBus lineBus = new LineBus();
        CreateLineBusDTO createLineBusDTO = createBusDTO.getLineBus();
        if (lineBusDTO == null)
            lineBusDTO = lineBusBO.create(createLineBusDTO).getBody();

        BeanUtils.copyProperties(lineBusDTO, lineBus);

        bus.setLineBus(lineBus);

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
        LineBus lineBus1 = new LineBus();
        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(user.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        LineBusDTO lineBusDTO = this.lineBusBO.getLineByName(updateBusDTO.getLineBus().getLineName()).getBody();

        if (!Objects.equals(updateBusDTO.getLineBus().getLineName(), bus.getLineBus().getLineName())) {

            if (lineBusDTO == null) {
                CreateLineBusDTO createLineBusDTO = new CreateLineBusDTO();
                createLineBusDTO.setLineName(updateBusDTO.getLineBus().getLineName());
                lineBusDTO = lineBusBO.create(createLineBusDTO).getBody();
            }
            updateBusDTO.setLineBus(lineBusDTO);
        }
        BeanUtils.copyProperties(lineBusDTO, lineBus1);

        bus.setLineBus(lineBus1);
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

        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        BusDTO busReturn = new BusDTO();
        BeanUtils.copyProperties(bus, busReturn);

        return ResponseEntity.ok(busReturn);
    }

    //Método que retorna os onibus criados pelo usuario
    @Override
    public ResponseEntity<List<BusDTO>> findBusForUser(Long userId) {
        List<BusDTO> busReturn;
        UserDTO user = userBO.me();

        if (!userId.equals(user.getId())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        busReturn = this.busDAO.listBusForUserId(userId).stream().map((bus) -> {

            BusDTO busDTO = new BusDTO();
            BeanUtils.copyProperties(bus, busDTO);
            return busDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(busReturn);
    }

    //Método que lista os onibus pela linha
    @Override
    public Page<BusDTO> listBusForLine(String line, Pageable pageable) {

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
    @Override
    public Page<BusDTO> listAll(Pageable pageable) {
        Page<BusDTO> busReturn;
        busReturn = this.busDAO.listForDate(pageable).map((page) -> {
            BusDTO bus = new BusDTO();
            BeanUtils.copyProperties(page, bus);
            return bus;
        });

        return busReturn;
    }


    //Método que favorita um onibus
    @Override
    public ResponseEntity<List<BusDTO>> favoriteBus(Long busId) {
        UserDTO user = userBO.me();

        BusDTO busDTO = getById(busId).getBody();
        Bus bus = new Bus();

        List<Bus> busList = user.getFavoriteBus();
        BeanUtils.copyProperties(busDTO, bus);

        if (busList.contains(bus))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        busList.add(bus);

        userBO.updateFavoriteBus(user.getId(), busList);

        List<BusDTO> busListDTO = busList.stream().map(busMap -> {
            BusDTO busDTO1 = new BusDTO();

            BeanUtils.copyProperties(busMap, busDTO1);

            return busDTO1;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(busListDTO);
    }

    //Método que desfavorita um onibus
    @Override
    public ResponseEntity<List<BusDTO>> desfavoriteBus(Long busId) {
        UserDTO currenUser = userBO.me();

        Long userId = currenUser.getId();

        if (!Objects.equals(currenUser.getId(), userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        UserDTO user = userBO.findById(userId);
        BusDTO busDTO = getById(busId).getBody();

        List<Bus> busList = user.getFavoriteBus();
        List<Bus> verifyBusInList = busList.stream().filter(bus ->
                bus.getId().equals(busDTO.getId())
        ).collect(Collectors.toList());


        if (verifyBusInList.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        busList.remove(verifyBusInList.get(0));
        userBO.updateFavoriteBus(userId, busList);

        List<BusDTO> busListDTO = busList.stream().map(busMap -> {
            BusDTO busDTO1 = new BusDTO();

            BeanUtils.copyProperties(busMap, busDTO1);

            return busDTO1;
        }).collect(Collectors.toList());

        User userSave = new User();

        BeanUtils.copyProperties(user, userSave);

        return ResponseEntity.ok(busListDTO);
    }

    @Override
    public ResponseEntity<List<StatisticsDTO>> listBusStatistics() {
        List<LineBusDTO> lineBusDTOList = this.lineBusBO.listAll().getBody();

        List<StatisticsDTO> statisticsDTOList = lineBusDTOList.stream().map(line -> {
            int savedQuantity = this.busDAO.listLineBusFavorited(line.getId());
            StatisticsDTO statisticsDTO = new StatisticsDTO(savedQuantity, line.getLineName());
            
            return statisticsDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(statisticsDTOList);
    }
}
