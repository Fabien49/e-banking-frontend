package com.fabienit.escaladefriends.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/*import com.macrosoftas.archijee.model.Role;
import com.macrosoftas.archijee.model.User;
import com.macrosoftas.archijee.repository.RoleRepository;
import com.macrosoftas.archijee.repository.UserRepository;*/

@Service
public class UserServiceImpl implements UserDetailsService {

   /* @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        HashSet<Role> roles = new HashSet<Role>();
        Role role = new Role();
        role.setRole("ADMIN");
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

	/*
	 * INSERT INTO `role` VALUES (1,'ADMIN');
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}
	*/


   @Transactional
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

       //TODO
        /*User user = userRepository.findByEmail(mail);
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);*/
       return null;
    }

    /*private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, authorities);
    }*/
}