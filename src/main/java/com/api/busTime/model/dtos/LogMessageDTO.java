package com.api.busTime.model.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class LogMessageDTO  {
    @NotBlank
    private Long id;
    @NotBlank
    private LocalDateTime time;
    @NotNull
    private String urlStatus;
    @NotBlank
    private String method;
    @NotBlank
    private String url;
    private String userForm;

}
