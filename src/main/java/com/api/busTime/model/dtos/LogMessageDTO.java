package com.api.busTime.model.dtos;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class LogMessageDTO extends CreateLogMessageDTO{
    @NotBlank
    private Long id;
    @NotBlank
    private LocalDateTime time;
}
