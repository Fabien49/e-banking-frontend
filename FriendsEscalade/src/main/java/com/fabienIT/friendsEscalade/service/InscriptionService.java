package com.fabienIT.friendsEscalade.service;


import com.fabienIT.friendsEscalade.model.Utilisateur;
import com.fabienIT.friendsEscalade.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InscriptionService {

    @Autowired
    InscriptionRepository inscriptionRepository;

    public void ajouter (Utilisateur utilisateur){

        inscriptionRepository.save(utilisateur);
    }

   /* public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        HashSet<Role> roles = new HashSet<Role>();
        Role role = new Role();
        role.setRole("ADMIN");
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }*/
}
