package com.api.busTime.utils;

public enum ValidationType {

    ADMIN ("Admin Online"),
    SUPER_ADMIN ("Super Admin Online");

    String value;

    ValidationType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
