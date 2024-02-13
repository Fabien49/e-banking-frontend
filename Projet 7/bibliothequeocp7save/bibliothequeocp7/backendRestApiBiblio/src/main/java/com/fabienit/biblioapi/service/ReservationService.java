package com.fabienit.biblioapi.service;

import com.fabienit.biblioapi.model.Reservation;
import com.fabienit.biblioapi.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public void ajouterReservation (Reservation reservation) {reservationRepository.save(reservation);}
}
