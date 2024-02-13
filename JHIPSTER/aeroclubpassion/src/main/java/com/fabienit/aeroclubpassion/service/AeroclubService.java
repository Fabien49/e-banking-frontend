package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.service.dto.AeroclubDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.fabienit.aeroclubpassion.domain.Aeroclub}.
 */
public interface AeroclubService {
    /**
     * Save a aeroclub.
     *
     * @param aeroclubDTO the entity to save.
     * @return the persisted entity.
     */
    AeroclubDTO save(AeroclubDTO aeroclubDTO);

    /**
     * Partially updates a aeroclub.
     *
     * @param aeroclubDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AeroclubDTO> partialUpdate(AeroclubDTO aeroclubDTO);

    /**
     * Get all the aeroclubs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AeroclubDTO> findAll(Pageable pageable);

    /**
     * Get the "id" aeroclub.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AeroclubDTO> findOne(Long id);

    /**
     * Delete the "id" aeroclub.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
