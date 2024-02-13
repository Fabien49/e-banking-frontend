package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.service.dto.UserRegisteredDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.fabienit.aeroclubpassion.domain.UserRegistered}.
 */
public interface UserRegisteredService {
    /**
     * Save a userRegistered.
     *
     * @param userRegisteredDTO the entity to save.
     * @return the persisted entity.
     */
    UserRegisteredDTO save(UserRegisteredDTO userRegisteredDTO);

    /**
     * Partially updates a userRegistered.
     *
     * @param userRegisteredDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserRegisteredDTO> partialUpdate(UserRegisteredDTO userRegisteredDTO);

    /**
     * Get all the userRegistereds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserRegisteredDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userRegistered.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserRegisteredDTO> findOne(Long id);

    /**
     * Delete the "id" userRegistered.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
