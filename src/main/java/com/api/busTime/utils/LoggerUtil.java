package com.api.busTime.utils;

import com.api.busTime.BusTimeApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LoggerUtil {
    @Autowired
    private static Logger logger = LoggerFactory.getLogger(BusTimeApplication.class);

    public void registerLogger(String method, String url, RequisitionStatus status, String action, String complement) {

        LocalDateTime time = LocalDateTime.now();
        logger.info("Método: "+method+"URL: "+url+" - Status: "+status+" - Descricao: Um usuário " + action + " " + complement);
    }
}
