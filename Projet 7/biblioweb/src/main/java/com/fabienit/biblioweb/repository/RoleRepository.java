package com.fabienit.biblioapi.repository;

import com.fabienit.biblioapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{

    Role findRoleById(Long id);
}