package com.api.busTime.model.bo;

import com.api.busTime.model.entities.PermissionsGroup;

import java.util.List;

public interface PermissionsBO {

    List<PermissionsGroup> findAll();

    PermissionsGroup findById(int id);
}
