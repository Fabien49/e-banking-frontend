package com.fabienit.aeroclubpassion.web.rest;

import com.fabienit.aeroclubpassion.repository.AtelierRepository;
import com.fabienit.aeroclubpassion.service.AtelierQueryService;
import com.fabienit.aeroclubpassion.service.AtelierService;
import com.fabienit.aeroclubpassion.service.criteria.AtelierCriteria;
import com.fabienit.aeroclubpassion.service.dto.AtelierDTO;
import com.fabienit.aeroclubpassion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.fabienit.aeroclubpassion.domain.Atelier}.
 */
@RestController
@RequestMapping("/api")
public class AtelierResource {

    private final Logger log = LoggerFactory.getLogger(AtelierResource.class);

    private static final String ENTITY_NAME = "atelier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtelierService atelierService;

    private final AtelierRepository atelierRepository;

    private final AtelierQueryService atelierQueryService;

    public AtelierResource(AtelierService atelierService, AtelierRepository atelierRepository, AtelierQueryService atelierQueryService) {
        this.atelierService = atelierService;
        this.atelierRepository = atelierRepository;
        this.atelierQueryService = atelierQueryService;
    }

    /**
     * {@code POST  /ateliers} : Create a new atelier.
     *
     * @param atelierDTO the atelierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atelierDTO, or with status {@code 400 (Bad Request)} if the atelier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ateliers")
    public ResponseEntity<AtelierDTO> createAtelier(@RequestBody AtelierDTO atelierDTO) throws URISyntaxException {
        log.debug("REST request to save Atelier : {}", atelierDTO);
        if (atelierDTO.getId() != null) {
            throw new BadRequestAlertException("A new atelier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AtelierDTO result = atelierService.save(atelierDTO);
        return ResponseEntity
            .created(new URI("/api/ateliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ateliers/:id} : Updates an existing atelier.
     *
     * @param id the id of the atelierDTO to save.
     * @param atelierDTO the atelierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atelierDTO,
     * or with status {@code 400 (Bad Request)} if the atelierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atelierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ateliers/{id}")
    public ResponseEntity<AtelierDTO> updateAtelier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AtelierDTO atelierDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Atelier : {}, {}", id, atelierDTO);
        if (atelierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atelierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atelierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AtelierDTO result = atelierService.save(atelierDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atelierDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ateliers/:id} : Partial updates given fields of an existing atelier, field will ignore if it is null
     *
     * @param id the id of the atelierDTO to save.
     * @param atelierDTO the atelierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atelierDTO,
     * or with status {@code 400 (Bad Request)} if the atelierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the atelierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the atelierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ateliers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AtelierDTO> partialUpdateAtelier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AtelierDTO atelierDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Atelier partially : {}, {}", id, atelierDTO);
        if (atelierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atelierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atelierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AtelierDTO> result = atelierService.partialUpdate(atelierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atelierDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ateliers} : get all the ateliers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ateliers in body.
     */
    @GetMapping("/ateliers")
    public ResponseEntity<List<AtelierDTO>> getAllAteliers(AtelierCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ateliers by criteria: {}", criteria);
        Page<AtelierDTO> page = atelierQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ateliers/count} : count all the ateliers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ateliers/count")
    public ResponseEntity<Long> countAteliers(AtelierCriteria criteria) {
        log.debug("REST request to count Ateliers by criteria: {}", criteria);
        return ResponseEntity.ok().body(atelierQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ateliers/:id} : get the "id" atelier.
     *
     * @param id the id of the atelierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atelierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ateliers/{id}")
    public ResponseEntity<AtelierDTO> getAtelier(@PathVariable Long id) {
        log.debug("REST request to get Atelier : {}", id);
        Optional<AtelierDTO> atelierDTO = atelierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(atelierDTO);
    }

    /**
     * {@code DELETE  /ateliers/:id} : delete the "id" atelier.
     *
     * @param id the id of the atelierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ateliers/{id}")
    public ResponseEntity<Void> deleteAtelier(@PathVariable Long id) {
        log.debug("REST request to delete Atelier : {}", id);
        atelierService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
