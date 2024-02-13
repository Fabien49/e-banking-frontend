package com.fabienit.biblioapi.service;

import com.fabienit.biblioapi.model.Bibliotheque;
import com.fabienit.biblioapi.repository.BibliothequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BibliothequeService {

    @Autowired
    BibliothequeRepository bibliothequeRepository;

    public void ajouterBibli (Bibliotheque bibliotheque){bibliothequeRepository.save(bibliotheque);}
}
