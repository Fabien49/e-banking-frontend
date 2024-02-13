package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.service.dto.AtelierDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.fabienit.aeroclubpassion.domain.Atelier}.
 */
public interface AtelierService {
    /**
     * Save a atelier.
     *
     * @param atelierDTO the entity to save.
     * @return the persisted entity.
     */
    AtelierDTO save(AtelierDTO atelierDTO);

    /**
     * Partially updates a atelier.
     *
     * @param atelierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AtelierDTO> partialUpdate(AtelierDTO atelierDTO);

    /**
     * Get all the ateliers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AtelierDTO> findAll(Pageable pageable);

    /**
     * Get the "id" atelier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AtelierDTO> findOne(Long id);

    /**
     * Delete the "id" atelier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
