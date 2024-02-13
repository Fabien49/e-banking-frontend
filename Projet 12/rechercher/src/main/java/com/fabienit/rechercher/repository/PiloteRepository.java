package com.fabienit.rechercher.repository;

import com.fabienit.rechercher.domain.Pilote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pilote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PiloteRepository extends JpaRepository<Pilote, Long> {}
