package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.service.dto.TarifDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.fabienit.aeroclubpassion.domain.Tarif}.
 */
public interface TarifService {
    /**
     * Save a tarif.
     *
     * @param tarifDTO the entity to save.
     * @return the persisted entity.
     */
    TarifDTO save(TarifDTO tarifDTO);

    /**
     * Partially updates a tarif.
     *
     * @param tarifDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TarifDTO> partialUpdate(TarifDTO tarifDTO);

    /**
     * Get all the tarifs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TarifDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tarif.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TarifDTO> findOne(Long id);

    /**
     * Delete the "id" tarif.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
