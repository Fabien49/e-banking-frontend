package com.fabienit.aeroclubpassion.repository;

import com.fabienit.aeroclubpassion.domain.Reservation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Reservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {}
