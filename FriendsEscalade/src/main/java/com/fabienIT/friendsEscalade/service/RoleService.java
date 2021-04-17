package com.fabienIT.friendsEscalade.service;

import com.fabienIT.friendsEscalade.model.Role;
import com.fabienIT.friendsEscalade.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	 @Autowired
	 RoleRepository roleRepository;

	 public Role findRoleById (Long id){return roleRepository.findAllById(id);}

}
