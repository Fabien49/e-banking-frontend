package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.service.dto.AvionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.fabienit.aeroclubpassion.domain.Avion}.
 */
public interface AvionService {
    /**
     * Save a avion.
     *
     * @param avionDTO the entity to save.
     * @return the persisted entity.
     */
    AvionDTO save(AvionDTO avionDTO);

    /**
     * Partially updates a avion.
     *
     * @param avionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AvionDTO> partialUpdate(AvionDTO avionDTO);

    /**
     * Get all the avions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" avion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvionDTO> findOne(Long id);

    /**
     * Delete the "id" avion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
