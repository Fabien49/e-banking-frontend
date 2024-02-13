package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.service.dto.RevisionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.fabienit.aeroclubpassion.domain.Revision}.
 */
public interface RevisionService {
    /**
     * Save a revision.
     *
     * @param revisionDTO the entity to save.
     * @return the persisted entity.
     */
    RevisionDTO save(RevisionDTO revisionDTO);

    /**
     * Partially updates a revision.
     *
     * @param revisionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RevisionDTO> partialUpdate(RevisionDTO revisionDTO);

    /**
     * Get all the revisions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RevisionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" revision.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RevisionDTO> findOne(Long id);

    /**
     * Delete the "id" revision.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
