package com.fabienit.escaladefriends.service;

import com.fabienit.escaladefriends.dao.InscriptionRepository;
import com.fabienit.escaladefriends.entities.Utilisateur;
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
