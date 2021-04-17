package com.fabienIT.escaladefriendsp6ocr.service;

import com.fabienIT.escaladefriendsp6ocr.model.Role;
import com.fabienIT.escaladefriendsp6ocr.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	 @Autowired
	 RoleRepository roleRepository;

	 public Role findRoleById (Long id){return roleRepository.findRoleById(id);}

}
