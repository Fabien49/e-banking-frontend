package com.fabienit.biblioapi.controller;

import com.fabienit.biblioapi.exceptions.LivreIntrouvableException;
import com.fabienit.biblioapi.model.Bibliotheque;
import com.fabienit.biblioapi.repository.BibliothequeRepository;
import com.fabienit.biblioapi.service.BibliothequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/bibliotheques")
public class BibliothequeController {

    @Autowired
    BibliothequeRepository bibliothequeRepository;

    @Autowired
    BibliothequeService bibliothequeService;

    //Récupérer la liste de toutes les bibliothèques
    @GetMapping
    public List<Bibliotheque> listeDesBibliothques () {

        List<Bibliotheque> bibliotheques = bibliothequeRepository.findAll();

        return bibliotheques;
    }

    //Récupérer une bibliothèque par son Id
    @GetMapping(value = "/{id}")
    public Bibliotheque afficherUneBilbiotheque(@PathVariable int id) {

        Bibliotheque bibliotheque = bibliothequeRepository.findById(id);

        if(bibliotheque==null) throw new LivreIntrouvableException("La bibliothèque avec cet id est INTROUVABLE. Essayez avec un autre ID.");

        return bibliotheque;
    }

    //Ajouter une blibliothèque
    @PostMapping
    public void ajouterBibli (@RequestBody Bibliotheque bibliotheque){bibliothequeService.ajouterBibli(bibliotheque);}

    //Modifier une bibliothèque via son Id
    @PutMapping("/{id}")
    public ResponseEntity<Bibliotheque> updateBibliothèque(@PathVariable("id") int id, @RequestBody Bibliotheque bibliotheque) {
        Optional<Bibliotheque> modifierBibliotheque = Optional.ofNullable(bibliothequeRepository.findById(id));

        if (modifierBibliotheque.isPresent()) {
            Bibliotheque _bibliotheque = modifierBibliotheque.get();
            _bibliotheque.setNom(bibliotheque.getNom());
            _bibliotheque.setVoie(bibliotheque.getVoie());
            _bibliotheque.setCodePostal(bibliotheque.getCodePostal());
            _bibliotheque.setCommune(bibliotheque.getCommune());
            return new ResponseEntity<Bibliotheque>(bibliothequeRepository.save(_bibliotheque), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Supprimer une bibliothèque via son Id
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBibliotheque(@PathVariable("id") int id) {
        try {
            bibliothequeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
