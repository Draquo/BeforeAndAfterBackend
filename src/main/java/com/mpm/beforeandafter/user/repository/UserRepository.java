package com.mpm.beforeandafter.user.repository;

import com.mpm.beforeandafter.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByRolesIn(Set<Long> roles);

    Optional<User> findByName(String username);

    Optional<User> findByEmail(String email);
}