
package com.fabienit.biblioweb.service;

import com.fabienit.biblioweb.model.Role;
import com.fabienit.biblioweb.model.Usager;
import com.fabienit.biblioweb.repository.RoleRepository;
import com.fabienit.biblioweb.repository.UsagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("usagerService")
public abstract class UsagerServiceImpl implements UserDetailsService, UsagerService{


	@Autowired
	private UsagerRepository usagerRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;


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
	public UserDetails loadUserByUsername(String userName, Usager usager, String email) throws UsernameNotFoundException {
		//Usager usager = usagerRepository.findUsagerByEmail(userName);// TODO appeler l'api
        RestTemplate restTemplate = new RestTemplate();

        restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));
        final ResponseEntity<Usager> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/usagers/usageremail/ " , Usager.class);
        Usager body = responseEntity.getBody();
		userName = usager.getName();
        System.out.println("body = " + body);
        usager = body;
        System.out.println("L'email de l'usager est : " + usager.getEmail());
        System.out.println("5555555555555555555" + usager);
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


