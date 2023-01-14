package com.svilen.onlinebookstore.service;

import com.svilen.onlinebookstore.domain.models.service.RoleServiceModel;

import java.util.Set;

public interface RoleService  {

    void seedRolesInDB();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
