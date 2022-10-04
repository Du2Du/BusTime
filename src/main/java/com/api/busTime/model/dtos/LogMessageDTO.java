package com.api.busTime.model.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
