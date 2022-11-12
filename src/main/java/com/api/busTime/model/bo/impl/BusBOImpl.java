package com.api.busTime.model.bo.impl;

import com.api.busTime.exceptions.EntityExistsException;
import com.api.busTime.exceptions.ForbbidenException;
import com.api.busTime.exceptions.ResourceNotFoundException;
import com.api.busTime.model.bo.BusBO;
import com.api.busTime.model.bo.LineBusBO;
import com.api.busTime.model.bo.UsersBO;
import com.api.busTime.model.dao.BusDAO;
import com.api.busTime.model.dtos.*;
import com.api.busTime.model.entities.Bus;
import com.api.busTime.model.entities.LineBus;
import com.api.busTime.utils.FormatEntityToDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusBOImpl implements BusBO {

    @Autowired
    private BusDAO busDAO;

    @Autowired
    private LineBusBO lineBusBO;

    @Autowired
    private UsersBO userBO;

    private boolean findBusWithNumber(Integer busNumber) {
        Optional<Bus> bus = this.busDAO.listBusForNumber(busNumber);
        return bus.isPresent();
    }

    private LineBusDTO findLineForName(String lineName) {
        return this.lineBusBO.getLineByName(lineName).getBody();
    }

    private boolean findBusInBusList(List<Bus> busList, Long busId) {
        List<Bus> busExists = busList.stream().filter(busOne ->
                busOne.getId().equals(busId)
        ).collect(Collectors.toList());
        return busExists.size() != 0;
    }

    private void removeBusFromList(List<Bus> busList, Long busId) {
        List<Bus> newBusList = busList.stream().filter(busA ->
                busA.getId().equals(busId)
        ).collect(Collectors.toList());
        busList.remove(newBusList.get(0));
    }

    private void verifyIdUserAdminBus(Bus bus, UserDTO user) {
        //Verificando se o id do usuárioAdmin é o mesmo do usuário logado
        if (!bus.getIdUserAdmin().equals(user.getId()))
            throw new ForbbidenException("Usuário não permitido!");
    }

    //Método que cria o onibus
    @Override
    public ResponseEntity<BusDTO> create(CreateBusDTO createBusDTO) {
        Bus bus = new Bus();
        if (findBusWithNumber(createBusDTO.getBusNumber()))
            throw new EntityExistsException("Número de ônibus ja cadastrado");

        //Buscando se a linha de ônibus existe
        LineBusDTO lineBusDTO = findLineForName(createBusDTO.getLineBus().getLineName());
        LineBus lineBus = new LineBus();
        CreateLineBusDTO createLineBusDTO = createBusDTO.getLineBus();
        if (lineBusDTO == null)
            lineBusDTO = lineBusBO.create(createLineBusDTO).getBody();

        assert lineBusDTO != null;
        BeanUtils.copyProperties(lineBusDTO, lineBus);
        bus.setLineBus(lineBus);
        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(createBusDTO, bus);
        return ResponseEntity.ok(FormatEntityToDTO.formatEntityToDto(this.busDAO.save(bus), BusDTO::new));
    }

    //Método que atualiza as informações do onibus
    @Override
    public ResponseEntity<BusDTO> update(Long busId, UpdateBusDTO updateBusDTO) {
        UserDTO user = userBO.me();

        //Verificando se existe algum onibus com esse id
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        LineBus lineBus1 = new LineBus();
        verifyIdUserAdminBus(bus, user);
        LineBusDTO lineBusDTO = findLineForName(updateBusDTO.getLineBus().getLineName());

        if (!Objects.equals(updateBusDTO.getLineBus().getLineName(), bus.getLineBus().getLineName())) {

            if (lineBusDTO == null) {
                CreateLineBusDTO createLineBusDTO = new CreateLineBusDTO();
                createLineBusDTO.setLineName(updateBusDTO.getLineBus().getLineName());
                lineBusDTO = lineBusBO.create(createLineBusDTO).getBody();
            }
            updateBusDTO.setLineBus(lineBusDTO);
        }
        assert lineBusDTO != null;
        BeanUtils.copyProperties(lineBusDTO, lineBus1);
        bus.setLineBus(lineBus1);
        //Colocando os valores de userDTO em user
        BeanUtils.copyProperties(updateBusDTO, bus);
        return ResponseEntity.ok(FormatEntityToDTO.formatEntityToDto(this.busDAO.save(bus), BusDTO::new));
    }

    //Método que irá deletar o onibus
    @Override
    public ResponseEntity<String> delete(Long busId) {
        UserDTO user = userBO.me();
        //Verificando se existe algum onibus com esse id
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        verifyIdUserAdminBus(bus, user);
        this.busDAO.delete(bus);
        return ResponseEntity.ok("Ônibus deletado com sucesso");
    }

    //Método que lista o onibus por id
    @Override
    public ResponseEntity<BusDTO> getById(Long busId) {
        Bus bus = this.busDAO.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Ônibus não encontrado."));
        return ResponseEntity.ok(FormatEntityToDTO.formatEntityToDto(bus, BusDTO::new));
    }

    //Método que retorna os onibus criados pelo usuario
    @Override
    public ResponseEntity<List<BusDTO>> findBusForUser() {
        UserDTO user = userBO.me();
        return ResponseEntity.ok(FormatEntityToDTO.formatListEntityToListDTO(this.busDAO.listBusForUserId(user.getId()), BusDTO::new));
    }

    //Método que lista os onibus pela linha
    @Override
    public Page<BusDTO> listBusForLine(String line, Pageable pageable) {
        Page<Bus> bus = this.busDAO.listBusForLine(line, pageable);
        return FormatEntityToDTO.formatPageEntityToPageDto(bus, BusDTO::new);
    }


    //Método que lista os onibus paginado
    @Override
    public Page<BusDTO> listAll(Pageable pageable) {
        return FormatEntityToDTO.formatPageEntityToPageDto(this.busDAO.listForDate(pageable), BusDTO::new);
    }


    //Método que favorita um onibus
    @Override
    public ResponseEntity<List<BusDTO>> favoriteBus(Long busId) {
        UserDTO user = userBO.me();
        BusDTO busDTO = getById(busId).getBody();
        Bus bus = new Bus();
        List<Bus> busList = user.getFavoriteBus();
        assert busDTO != null;
        BeanUtils.copyProperties(busDTO, bus);
        if (findBusInBusList(busList, bus.getId()))
            throw new EntityExistsException("Ônibus já favoritado!");
        busList.add(bus);
        userBO.updateFavoriteBus(user.getId(), busList);
        return ResponseEntity.ok(FormatEntityToDTO.formatListEntityToListDTO(busList, BusDTO::new));
    }

    //Método que desfavorita um onibus
    @Override
    public ResponseEntity<List<BusDTO>> desfavoriteBus(Long busId) {
        UserDTO currenUser = userBO.me();
        Long userId = currenUser.getId();
        BusDTO busDTO = getById(busId).getBody();
        List<Bus> busList = currenUser.getFavoriteBus();

        assert busDTO != null;
        if (!findBusInBusList(busList, busDTO.getId()))
            throw new ResourceNotFoundException("Ônibus não encontrado nos favoritos!");
        removeBusFromList(busList, busId);
        userBO.updateFavoriteBus(userId, busList);
        List<BusDTO> busListDTO = FormatEntityToDTO.formatListEntityToListDTO(busList, BusDTO::new);
        return ResponseEntity.ok(busListDTO);
    }

    @Override
    public ResponseEntity<List<StatisticsDTO>> listBusStatistics() {
        List<LineBusDTO> lineBusDTOList = this.lineBusBO.listAll().getBody();
        assert lineBusDTOList != null;
        List<StatisticsDTO> statisticsDTOList = lineBusDTOList.stream().map(line -> {
            int savedQuantity = this.busDAO.listLineBusFavorited(line.getId());
            if (savedQuantity == 0) return null;
            return new StatisticsDTO(savedQuantity, line.getLineName());
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return ResponseEntity.ok(statisticsDTOList);
    }
}