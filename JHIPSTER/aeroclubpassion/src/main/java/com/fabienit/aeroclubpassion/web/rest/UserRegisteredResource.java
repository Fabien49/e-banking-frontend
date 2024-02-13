package com.fabienit.aeroclubpassion.web.rest;

import com.fabienit.aeroclubpassion.repository.UserRegisteredRepository;
import com.fabienit.aeroclubpassion.service.UserRegisteredQueryService;
import com.fabienit.aeroclubpassion.service.UserRegisteredService;
import com.fabienit.aeroclubpassion.service.criteria.UserRegisteredCriteria;
import com.fabienit.aeroclubpassion.service.dto.UserRegisteredDTO;
import com.fabienit.aeroclubpassion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fabienit.aeroclubpassion.domain.UserRegistered}.
 */
@RestController
@RequestMapping("/api")
public class UserRegisteredResource {

    private final Logger log = LoggerFactory.getLogger(UserRegisteredResource.class);

    private static final String ENTITY_NAME = "userRegistered";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRegisteredService userRegisteredService;

    private final UserRegisteredRepository userRegisteredRepository;

    private final UserRegisteredQueryService userRegisteredQueryService;

    public UserRegisteredResource(
        UserRegisteredService userRegisteredService,
        UserRegisteredRepository userRegisteredRepository,
        UserRegisteredQueryService userRegisteredQueryService
    ) {
        this.userRegisteredService = userRegisteredService;
        this.userRegisteredRepository = userRegisteredRepository;
        this.userRegisteredQueryService = userRegisteredQueryService;
    }

    /**
     * {@code POST  /user-registereds} : Create a new userRegistered.
     *
     * @param userRegisteredDTO the userRegisteredDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRegisteredDTO, or with status {@code 400 (Bad Request)} if the userRegistered has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-registereds")
    public ResponseEntity<UserRegisteredDTO> createUserRegistered(@Valid @RequestBody UserRegisteredDTO userRegisteredDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserRegistered : {}", userRegisteredDTO);
        if (userRegisteredDTO.getId() != null) {
            throw new BadRequestAlertException("A new userRegistered cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRegisteredDTO result = userRegisteredService.save(userRegisteredDTO);
        return ResponseEntity
            .created(new URI("/api/user-registereds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-registereds/:id} : Updates an existing userRegistered.
     *
     * @param id the id of the userRegisteredDTO to save.
     * @param userRegisteredDTO the userRegisteredDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRegisteredDTO,
     * or with status {@code 400 (Bad Request)} if the userRegisteredDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRegisteredDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-registereds/{id}")
    public ResponseEntity<UserRegisteredDTO> updateUserRegistered(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserRegisteredDTO userRegisteredDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserRegistered : {}, {}", id, userRegisteredDTO);
        if (userRegisteredDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRegisteredDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRegisteredRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserRegisteredDTO result = userRegisteredService.save(userRegisteredDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userRegisteredDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-registereds/:id} : Partial updates given fields of an existing userRegistered, field will ignore if it is null
     *
     * @param id the id of the userRegisteredDTO to save.
     * @param userRegisteredDTO the userRegisteredDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRegisteredDTO,
     * or with status {@code 400 (Bad Request)} if the userRegisteredDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userRegisteredDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userRegisteredDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-registereds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserRegisteredDTO> partialUpdateUserRegistered(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserRegisteredDTO userRegisteredDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserRegistered partially : {}, {}", id, userRegisteredDTO);
        if (userRegisteredDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRegisteredDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRegisteredRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserRegisteredDTO> result = userRegisteredService.partialUpdate(userRegisteredDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userRegisteredDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-registereds} : get all the userRegistereds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRegistereds in body.
     */
    @GetMapping("/user-registereds")
    public ResponseEntity<List<UserRegisteredDTO>> getAllUserRegistereds(UserRegisteredCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserRegistereds by criteria: {}", criteria);
        Page<UserRegisteredDTO> page = userRegisteredQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-registereds/count} : count all the userRegistereds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-registereds/count")
    public ResponseEntity<Long> countUserRegistereds(UserRegisteredCriteria criteria) {
        log.debug("REST request to count UserRegistereds by criteria: {}", criteria);
        return ResponseEntity.ok().body(userRegisteredQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-registereds/:id} : get the "id" userRegistered.
     *
     * @param id the id of the userRegisteredDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRegisteredDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-registereds/{id}")
    public ResponseEntity<UserRegisteredDTO> getUserRegistered(@PathVariable Long id) {
        log.debug("REST request to get UserRegistered : {}", id);
        Optional<UserRegisteredDTO> userRegisteredDTO = userRegisteredService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userRegisteredDTO);
    }

    /**
     * {@code DELETE  /user-registereds/:id} : delete the "id" userRegistered.
     *
     * @param id the id of the userRegisteredDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-registereds/{id}")
    public ResponseEntity<Void> deleteUserRegistered(@PathVariable Long id) {
        log.debug("REST request to delete UserRegistered : {}", id);
        userRegisteredService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
