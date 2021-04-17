/*
package com.fabienIT.escaladefriendsp6ocr.service;


import com.fabienIT.escaladefriendsp6ocr.model.Topo;
import com.fabienIT.escaladefriendsp6ocr.model.UtilisateurOld;
import com.fabienIT.escaladefriendsp6ocr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    @Autowired
    UserRepository utilisateurRepository;

    public UtilisateurOld findUtilisateurById(Long utilisateur) {
        return (UtilisateurOld) utilisateurRepository.findAllById(utilisateur);
    }

    public void effacer(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public List<UtilisateurOld> getUserList() {

        List<UtilisateurOld> maListe = new ArrayList<UtilisateurOld>();
        maListe = utilisateurRepository.findAll();

        return maListe;

    }

    public Page<UtilisateurOld> findByNomContains(String mc, Pageable pageable) {
        if (mc != null) {
            return utilisateurRepository.search(mc, pageable);
        }
        return utilisateurRepository.findAll(pageable);
    }



    public void ajouter (UtilisateurOld utilisateur){
        utilisateurRepository.save(utilisateur);
    }

    public void deleteById(Long id) {
    }

}


// public Page<UtilisateurOld> findByNomContains(String mc, Pageable pageable);

*/
