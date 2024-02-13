package com.fabienit.aeroclubpassion.repository;

import com.fabienit.aeroclubpassion.domain.Atelier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Atelier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AtelierRepository extends JpaRepository<Atelier, Long>, JpaSpecificationExecutor<Atelier> {}
