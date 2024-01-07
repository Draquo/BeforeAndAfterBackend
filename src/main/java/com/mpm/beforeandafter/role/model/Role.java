package com.mpm.beforeandafter.role.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpm.beforeandafter.role.type.RoleType;
import com.mpm.beforeandafter.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Class representing a role in the system.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles", indexes = @Index(name = "role_name_index", columnList = "name"))
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name="name")
    @Enumerated(EnumType.STRING)
    private RoleType name;

    public Role(RoleType name) {
        this.name = name;
    }
}