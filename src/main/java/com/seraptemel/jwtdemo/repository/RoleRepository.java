package com.seraptemel.jwtdemo.repository;

import com.seraptemel.jwtdemo.common.ERole;
import com.seraptemel.jwtdemo.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
