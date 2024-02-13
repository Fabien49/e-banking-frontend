/*
package com.fabienIT.escaladefriendsp6ocr.service;


import com.fabienIT.escaladefriendsp6ocr.model.Topo;
import com.fabienIT.escaladefriendsp6ocr.model.Utilisateur;
import com.fabienIT.escaladefriendsp6ocr.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurService {


    @Autowired
    UtilisateurRepository utilisateurRepository;

    public Utilisateur findUtilisateurById(Long utilisateur) {
        return (Utilisateur) utilisateurRepository.findAllById(utilisateur);
    }

    public void effacer(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public List<Utilisateur> getUserList() {

        List<Utilisateur> maListe = new ArrayList<Utilisateur>();
        maListe = utilisateurRepository.findAll();

        return maListe;

    }

    public Page<Utilisateur> findByNomContains(String mc, Pageable pageable) {
        if (mc != null) {
            return utilisateurRepository.search(mc, pageable);
        }
        return utilisateurRepository.findAll(pageable);
    }



    public void ajouter (Utilisateur utilisateur){
        utilisateurRepository.save(utilisateur);
    }

    public void deleteById(Long id) {
    }

}


// public Page<Utilisateur> findByNomContains(String mc, Pageable pageable);

*/
