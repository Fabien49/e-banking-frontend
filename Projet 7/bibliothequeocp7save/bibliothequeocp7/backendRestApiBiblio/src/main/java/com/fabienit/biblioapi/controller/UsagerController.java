package com.fabienit.biblioapi.controller;

import com.fabienit.biblioapi.exceptions.LivreIntrouvableException;
import com.fabienit.biblioapi.model.Usager;
import com.fabienit.biblioapi.repository.UsagerRepository;
import com.fabienit.biblioapi.service.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/usagers")
public class UsagerController {

    @Autowired
    UsagerRepository usagerRepository;

    @Autowired
    UsagerService usagerService;

    //Récupérer la liste de tous les usagers
    @GetMapping
    public List<Usager> listeDesUsagers() {

        List<Usager> usagers = usagerRepository.findAll();

        return usagers;
    }

    //Récupérer un usager par son Id
    @GetMapping(value = "/{id}")
    public Usager afficherUnUsager(@PathVariable int id) {

        Usager usager = usagerRepository.findById(id);

        if(usager==null) throw new LivreIntrouvableException("Le livre avec cet id est INTROUVABLE. Essayez avec un autre ID.");

        return usager;
    }

    @PostMapping
    public void ajouterUsager(@RequestBody Usager usager) {usagerService.ajouterUsager(usager);}

    //Modifier un usager via son Id
    @PutMapping("/{id}")
    public ResponseEntity<Usager> updateUsager(@PathVariable("id") int id, @RequestBody Usager usager) {
        Optional<Usager> modifierUsager = Optional.ofNullable(usagerRepository.findById(id));

        if (modifierUsager.isPresent()) {
            Usager _usager = modifierUsager.get();
            _usager.setName(usager.getName());
            _usager.setLastName(usager.getLastName());
            _usager.setEmail(usager.getEmail());
            _usager.setPassword(usager.getPassword());
            _usager.setVoie(usager.getVoie());
            _usager.setCodePostal(usager.getCodePostal());
            _usager.setCommune(usager.getCommune());
            _usager.setActive(usager.isActive());
            _usager.setRoles(usager.getRoles());
            return new ResponseEntity<Usager>(usagerRepository.save(_usager), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Supprimer un usager via son Id
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUsager(@PathVariable("id") int id) {
        try {
            usagerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Supprimer tous les usagers
    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAllUsagers() {
        try {
            usagerRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
