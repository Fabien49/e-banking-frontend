package com.fabienIT.friendsEscalade.service;

import com.fabienIT.friendsEscalade.model.Role;
import com.fabienIT.friendsEscalade.model.User;
import com.fabienIT.friendsEscalade.repository.RoleRepository;
import com.fabienIT.friendsEscalade.repository.UserRepository;
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
/*import sun.jvm.hotspot.debugger.Page;*/

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findUser(Long id){
		return userRepository.findUserById(id);
	}


	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("UTILISATEURCONNECTE");
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		System.out.println("L'utilisateur enregistré est : " + user);
	}

	public void saveMembre(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("MEMBRE");
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		System.out.println("Le membre enregistré est : " + user);
	}






/*	public void saveAdmin(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("ADMIN");
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		System.out.println("L'admin enregistré est : " + user);
	}*/
	

/*	 * INSERT INTO `role` VALUES (1,'ADMIN');
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}*/





	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(userName);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, authorities);
	}
}

