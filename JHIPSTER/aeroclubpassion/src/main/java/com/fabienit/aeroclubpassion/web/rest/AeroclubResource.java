package com.fabienit.aeroclubpassion.web.rest;

import com.fabienit.aeroclubpassion.repository.AeroclubRepository;
import com.fabienit.aeroclubpassion.service.AeroclubQueryService;
import com.fabienit.aeroclubpassion.service.AeroclubService;
import com.fabienit.aeroclubpassion.service.criteria.AeroclubCriteria;
import com.fabienit.aeroclubpassion.service.dto.AeroclubDTO;
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
 * REST controller for managing {@link com.fabienit.aeroclubpassion.domain.Aeroclub}.
 */
@RestController
@RequestMapping("/api")
public class AeroclubResource {

    private final Logger log = LoggerFactory.getLogger(AeroclubResource.class);

    private static final String ENTITY_NAME = "aeroclub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AeroclubService aeroclubService;

    private final AeroclubRepository aeroclubRepository;

    private final AeroclubQueryService aeroclubQueryService;

    public AeroclubResource(
        AeroclubService aeroclubService,
        AeroclubRepository aeroclubRepository,
        AeroclubQueryService aeroclubQueryService
    ) {
        this.aeroclubService = aeroclubService;
        this.aeroclubRepository = aeroclubRepository;
        this.aeroclubQueryService = aeroclubQueryService;
    }

    /**
     * {@code POST  /aeroclubs} : Create a new aeroclub.
     *
     * @param aeroclubDTO the aeroclubDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aeroclubDTO, or with status {@code 400 (Bad Request)} if the aeroclub has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aeroclubs")
    public ResponseEntity<AeroclubDTO> createAeroclub(@Valid @RequestBody AeroclubDTO aeroclubDTO) throws URISyntaxException {
        log.debug("REST request to save Aeroclub : {}", aeroclubDTO);
        if (aeroclubDTO.getId() != null) {
            throw new BadRequestAlertException("A new aeroclub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AeroclubDTO result = aeroclubService.save(aeroclubDTO);
        return ResponseEntity
            .created(new URI("/api/aeroclubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aeroclubs/:id} : Updates an existing aeroclub.
     *
     * @param id the id of the aeroclubDTO to save.
     * @param aeroclubDTO the aeroclubDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aeroclubDTO,
     * or with status {@code 400 (Bad Request)} if the aeroclubDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aeroclubDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aeroclubs/{id}")
    public ResponseEntity<AeroclubDTO> updateAeroclub(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AeroclubDTO aeroclubDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Aeroclub : {}, {}", id, aeroclubDTO);
        if (aeroclubDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aeroclubDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aeroclubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AeroclubDTO result = aeroclubService.save(aeroclubDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aeroclubDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aeroclubs/:id} : Partial updates given fields of an existing aeroclub, field will ignore if it is null
     *
     * @param id the id of the aeroclubDTO to save.
     * @param aeroclubDTO the aeroclubDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aeroclubDTO,
     * or with status {@code 400 (Bad Request)} if the aeroclubDTO is not valid,
     * or with status {@code 404 (Not Found)} if the aeroclubDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the aeroclubDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/aeroclubs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AeroclubDTO> partialUpdateAeroclub(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AeroclubDTO aeroclubDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aeroclub partially : {}, {}", id, aeroclubDTO);
        if (aeroclubDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aeroclubDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aeroclubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AeroclubDTO> result = aeroclubService.partialUpdate(aeroclubDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aeroclubDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /aeroclubs} : get all the aeroclubs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aeroclubs in body.
     */
    @GetMapping("/aeroclubs")
    public ResponseEntity<List<AeroclubDTO>> getAllAeroclubs(AeroclubCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Aeroclubs by criteria: {}", criteria);
        Page<AeroclubDTO> page = aeroclubQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aeroclubs/count} : count all the aeroclubs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/aeroclubs/count")
    public ResponseEntity<Long> countAeroclubs(AeroclubCriteria criteria) {
        log.debug("REST request to count Aeroclubs by criteria: {}", criteria);
        return ResponseEntity.ok().body(aeroclubQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /aeroclubs/:id} : get the "id" aeroclub.
     *
     * @param id the id of the aeroclubDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aeroclubDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aeroclubs/{id}")
    public ResponseEntity<AeroclubDTO> getAeroclub(@PathVariable Long id) {
        log.debug("REST request to get Aeroclub : {}", id);
        Optional<AeroclubDTO> aeroclubDTO = aeroclubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aeroclubDTO);
    }

    /**
     * {@code DELETE  /aeroclubs/:id} : delete the "id" aeroclub.
     *
     * @param id the id of the aeroclubDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aeroclubs/{id}")
    public ResponseEntity<Void> deleteAeroclub(@PathVariable Long id) {
        log.debug("REST request to delete Aeroclub : {}", id);
        aeroclubService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
