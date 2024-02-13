package com.fabienit.biblioapi.service;

import com.fabienit.biblioapi.model.Livre;
import com.fabienit.biblioapi.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LivreService {

    @Autowired
    LivreRepository livreRepository;

    public void ajouterLivre (Livre livre) {livreRepository.save(livre);}
}
