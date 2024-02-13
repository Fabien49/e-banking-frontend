package com.fabienit.aeroclubpassion.repository;

import com.fabienit.aeroclubpassion.domain.Tarif;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tarif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifRepository extends JpaRepository<Tarif, Long>, JpaSpecificationExecutor<Tarif> {}
