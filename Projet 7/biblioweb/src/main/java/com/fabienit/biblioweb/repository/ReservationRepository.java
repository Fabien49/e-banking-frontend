package com.fabienit.biblioapi.repository;

import com.fabienit.biblioapi.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    Reservation findById(int id);

    @Query("SELECT r FROM Reservation r WHERE r.dateRetard < CURRENT_DATE")
    List<Reservation> findReservationByDateRetard(LocalDateTime localDateTime);
}
