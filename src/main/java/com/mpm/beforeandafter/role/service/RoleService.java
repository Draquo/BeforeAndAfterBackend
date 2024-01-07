package com.mpm.beforeandafter.role.service;

import com.mpm.beforeandafter.role.model.Role;
import com.mpm.beforeandafter.role.type.RoleType;

public interface RoleService {

    void createRole(Role role);

    Role findByName(RoleType roleTypeName);

    void createRoles();
}
