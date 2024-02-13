package com.fabienit.aeroclubpassion.repository;

import com.fabienit.aeroclubpassion.domain.Aeroclub;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Aeroclub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AeroclubRepository extends JpaRepository<Aeroclub, Long>, JpaSpecificationExecutor<Aeroclub> {}
