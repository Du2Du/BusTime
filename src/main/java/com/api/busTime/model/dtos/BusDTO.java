package com.api.busTime.model.dtos;

import javax.validation.constraints.NotNull;

public class BusDTO extends CreateBusDTO{
    
    @NotNull
    private Long id;
}
