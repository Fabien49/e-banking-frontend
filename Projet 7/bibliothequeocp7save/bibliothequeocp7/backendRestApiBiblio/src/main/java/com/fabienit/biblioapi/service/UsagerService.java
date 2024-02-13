package com.fabienit.biblioapi.service;

import com.fabienit.biblioapi.model.Usager;
import com.fabienit.biblioapi.repository.UsagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsagerService {

    @Autowired
    UsagerRepository usagerRepository;

    public void ajouterUsager (Usager usager){usagerRepository.save(usager);}
}
