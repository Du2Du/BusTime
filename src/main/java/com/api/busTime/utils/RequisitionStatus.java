package com.api.busTime.utils;

public enum RequisitionStatus {
    SUCCESS("Sucesso"),
    FAILURE("Falha");
    
    String value;

    RequisitionStatus(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
