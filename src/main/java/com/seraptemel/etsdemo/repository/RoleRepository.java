package com.seraptemel.etsdemo.repository;

import com.seraptemel.etsdemo.common.ERole;
import com.seraptemel.etsdemo.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}