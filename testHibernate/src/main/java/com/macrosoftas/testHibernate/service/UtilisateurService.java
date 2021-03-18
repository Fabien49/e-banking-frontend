package com.macrosoftas.testHibernate.service;


import com.macrosoftas.testHibernate.model.Eleve;
import com.macrosoftas.testHibernate.repository.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurService {


    @Autowired
    EleveRepository eleveRepository;

    public List<Eleve> getUserList() {

        List<Eleve> maListe = new ArrayList<Eleve>();
        maListe = eleveRepository.findAll();

        return maListe;

    }

}