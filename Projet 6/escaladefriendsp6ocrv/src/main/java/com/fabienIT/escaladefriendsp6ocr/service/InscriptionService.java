package com.fabienIT.escaladefriendsp6ocr.service;



import com.fabienIT.escaladefriendsp6ocr.repository.InscriptionRepository;
import com.fabienIT.escaladefriendsp6ocr.model.Utilisateur;
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
