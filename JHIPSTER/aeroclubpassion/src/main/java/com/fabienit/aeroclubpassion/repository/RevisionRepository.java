package com.fabienit.aeroclubpassion.repository;

import com.fabienit.aeroclubpassion.domain.Revision;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Revision entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RevisionRepository extends JpaRepository<Revision, Long>, JpaSpecificationExecutor<Revision> {}
