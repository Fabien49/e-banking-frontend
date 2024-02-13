package com.fabienit.biblioapi.controller;

import com.fabienit.biblioapi.exceptions.LivreIntrouvableException;
import com.fabienit.biblioapi.model.Livre;
import com.fabienit.biblioapi.repository.LivreRepository;
import com.fabienit.biblioapi.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/livres")
public class LivreController {

    @Autowired
    LivreRepository livreRepository;

    @Autowired
    LivreService livreService;

    //Récupérer la liste de tous les livres
    @GetMapping
    public List<Livre> listeDesLivres () {

        List<Livre> livres = livreRepository.findAll();

        return livres;
    }

    //Récupérer un livre par son Id
    @GetMapping(value = "/{id}")
    public Livre afficherUnLivre(@PathVariable int id) {

        Livre livre = livreRepository.findById(id);

        if(livre==null) throw new LivreIntrouvableException("Le livre avec cet id est INTROUVABLE. Essayez avec un autre ID.");

        return livre;
    }


    //Ajouter un livre
    @PostMapping
    public void ajouterLivre (@RequestBody Livre livre){livreService.ajouterLivre(livre);}


    //Modifier un livre via son Id
    @PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivre(@PathVariable("id") int id, @RequestBody Livre livre) {
        Optional<Livre> modifierLivre = Optional.ofNullable(livreRepository.findById(id));

        if (modifierLivre.isPresent()) {
            Livre _livre = modifierLivre.get();
            _livre.setAuteur(livre.getAuteur());
            _livre.setDescription(livre.getDescription());
            _livre.setCategorie(livre.getCategorie());
            _livre.setQuantite(livre.getQuantite());
            _livre.setIsbn(livre.getIsbn());
            _livre.setTitre(livre.getTitre());
            return new ResponseEntity<Livre>(livreRepository.save(_livre), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Supprimer un livre via son Id
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLivre(@PathVariable("id") int id) {
        try {
            livreRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Supprimer tous les livres
    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAllLivres() {
        try {
            livreRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
