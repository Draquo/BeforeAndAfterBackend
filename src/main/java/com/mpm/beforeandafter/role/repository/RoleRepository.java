package com.mpm.beforeandafter.role.repository;

import com.mpm.beforeandafter.role.model.Role;
import com.mpm.beforeandafter.role.type.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for accessing role data.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleType roleName);
}
