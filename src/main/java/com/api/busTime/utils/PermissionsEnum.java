package com.api.busTime.utils;

public enum PermissionsEnum {
    RETURN_USERS ("return_users"),
    UPDATE_PERMISSION_USER ("update_permission_user"),
    DELETE_USER ("delete_user"),
    CREATE_BUS("create_bus"),
    UPDATE_BUS("update_bus"),
    RETURN_BUS_FROM_USER("return_bus_from_user"),
    DELETE_BUS("delete_bus");

    String value;

    PermissionsEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
