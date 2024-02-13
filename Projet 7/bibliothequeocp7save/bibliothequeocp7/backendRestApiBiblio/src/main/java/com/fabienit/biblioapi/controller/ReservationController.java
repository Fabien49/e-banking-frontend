package com.fabienit.biblioapi.controller;


import com.fabienit.biblioapi.exceptions.LivreIntrouvableException;
import com.fabienit.biblioapi.model.Reservation;
import com.fabienit.biblioapi.repository.ReservationRepository;
import com.fabienit.biblioapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/reservations")
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationService reservationService;

    //Récupérer la liste de toutes les réservations
    @GetMapping
    public List<Reservation> listeDesReservations () {

        List<Reservation> reservations = reservationRepository.findAll();

        return reservations;
    }

    //Récupérer une réservation par son Id
    @GetMapping(value = "/{id}")
    public Reservation afficherUneReservation(@PathVariable int id) {

        Reservation reservation = reservationRepository.findById(id);

        if(reservation==null) throw new LivreIntrouvableException("La réservation avec cet id est INTROUVABLE. Essayez avec un autre ID.");

        return reservation;
    }

    //Ajouter une réservation
    @PostMapping
    public void ajouterReservation (@RequestBody Reservation reservation){reservationService.ajouterReservation(reservation);}


    //Modifier une réservation via son Id
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable("id") int id, @RequestBody Reservation reservation) {
        Optional<Reservation> modifierReservation = Optional.ofNullable(reservationRepository.findById(id));

        if (modifierReservation.isPresent()) {
            Reservation _reservation = modifierReservation.get();
            _reservation.setDateReservation(reservation.getDateReservation());
            _reservation.setDateRetour(reservation.getDateRetour());
            _reservation.setProlongation(reservation.isProlongation());
            return new ResponseEntity<Reservation>(reservationRepository.save(_reservation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Supprimer une réservation via son Id
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable("id") int id) {
        try {
            reservationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
