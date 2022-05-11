package com.api.busTime.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateUserDTO extends CreateUserDTO {

    @NotNull
    private Long id;
}
