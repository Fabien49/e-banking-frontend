package com.fabienit.biblioapi.service;


import com.fabienit.biblioapi.model.Role;
import com.fabienit.biblioapi.model.Usager;
import com.fabienit.biblioapi.repository.RoleRepository;
import com.fabienit.biblioapi.repository.UsagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("usagerService")
public class UsagerServiceImpl implements UsagerService, UserDetailsService {

	@Autowired
	private UsagerRepository usagerRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	public Usager findUsagerByEmail(String email) {
		return usagerRepository.findUsagerByEmail(email);
	}


	public void saveUsager(Usager usager) {
		usager.setPassword(bCryptPasswordEncoder.encode(usager.getPassword()));
		usager.setActive(true);
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("UTILISATEURCONNECTE");
		roles.add(role);
		usager.setRoles(roles);
		usagerRepository.save(usager);
		System.out.println("L'usager enregistré est : " + usager);
	}

	public void saveMembre(Usager usager) {
		usager.setPassword(bCryptPasswordEncoder.encode(usager.getPassword()));
		usager.setActive(true);
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("MEMBRE");
		roles.add(role);
		usager.setRoles(roles);
		usagerRepository.save(usager);
		System.out.println("Le membre enregistré est : " + usager);
	}

	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Usager usager = usagerRepository.findUsagerByEmail(userName);
		List<GrantedAuthority> authorities = getUserAuthority(usager.getRoles());
		return buildUserForAuthentication(usager, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(Usager user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, authorities);
	}
}

