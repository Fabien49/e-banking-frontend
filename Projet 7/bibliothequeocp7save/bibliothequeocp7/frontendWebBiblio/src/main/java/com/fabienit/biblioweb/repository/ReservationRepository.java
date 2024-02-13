package com.fabienit.biblioweb.repository;

import com.fabienit.biblioapi.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    Reservation findById(int id);
}
